package myAdapter;


import java.util.NoSuchElementException;

/**
 * This class allows to use classes which implement List interface (J2SE 1.4.2) in Java Micro Edition environment (CLDC 1.1).
 * This adapter uses class Vector from CLDC 1.1 as adaptee for List from J2SE 1.4.2.
 * <br><br>
 * ListAdapter implements HList and HCollection interfaces which have all the methods of List and Collection interfaces from J2SE 1.4.2
 * environment. The interfaces have been renamed to avoid collision with current version of List and Collection.
 * <br><br>
 * This class provides add, insert, remove and inspect operations.
 * ListAdapter allows to duplicate elements, and it doesn't give any restriction on the type of the element that will be inserted (null is a valid value).
 * <br><br>
 * ListAdapter also manages sublist. In this case when a sublist has a structural change the father list has the same change.
 * <br><br>
 * ListAdapter provides ListIterator which will be described before the declaration of ListIterator subclass.
 *
 * @see myAdapter.HList
 * @see myAdapter.HCollection
 * @author Andrea Stocco
 */
public class ListAdapter implements HList{

    /**
     * Variable identifying the first valid index for a sublist. If the list isn't a sublist
     * this variable is set to 0.
     */
    private final int from;
    /**
     * Variable identifying the last index (exclusive) of a sublist. If the list isn't a sublist
     * this variable is set to size().
     */
    private int to;
    /**
     * Vector which contains the element of the list
     */
    private final Vector list;
    /**
     * Reference to the father list of a sublist. If the list isn't a sublist
     * this variable is set to null.
     */
    private final ListAdapter father;

    /**
     * Void Constructor (no arguments).
     * It creates an empty ListAdapter.
     */
    public ListAdapter() {
        from = 0;
        to = 0;
        list = new Vector();
        father = null;
    }

    /**
     * Private constructor. It creates a new listAdapter based on his ancestor.
     * It is invoked only by subList method.
     * <br><br>
     * Example: {@code
     * ListAdapter subList = list.subList(0, list.size() - 1);
     * }
     * @param from starting index of the sublist
     * @param to final index of the sublist
     * @param listAdapter reference of father list
     */
    private ListAdapter(int from, int to, ListAdapter listAdapter){
        this.from = from;
        this.to = to;
        this.list = listAdapter.list;
        this.father = listAdapter;
    }

    /**
     * Constructor which creates a new ListAdapter with the same elements as its argument
     * @param coll collection from which to take elements
     */
    public ListAdapter(HCollection coll){
        this();
        HIterator iter = coll.iterator();
        while(iter.hasNext()) add(iter.next());
    }

    /**
     * Method that calculates the size of the list
     * @return the size of the list
     */
    @Override
    public int size() {
        return to - from;
    }

    /**
     * Method that checks if the list contains elements
     * @return true if the list is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return to == from;
    }

    /**
     * Method that searches in the list the specified element
     * @param obj element whose presence in this list is to be tested.
     * @return true if obj is present at least one time, false otherwise
     */
    @Override
    public boolean contains(Object obj) {
        HListIterator iter = listIterator();
        if(obj == null){
            while(iter.hasNext()){
                if(iter.next() == null) return true;
            }
        }else{
            while(iter.hasNext()){
                Object current = iter.next();
                if(current != null && current.equals(obj)) return true;
            }
        }
        return false;
    }

    /**
     * Method that returns a new instance of HIterator
     * @return a new instance of HIterator
     */
    @Override
    public HIterator iterator() {
        return new ListAdapterIterator();
    }

    /**
     * Method that returns an Object[] which contains all the elements of the list.
     * The size of the returned array is the same as the size of the list
     * @return a new Object[] that contains a copy of all the elements of the list
     */
    @Override
    public Object[] toArray() {
        Object[] toReturn = new Object[size()];
        HListIterator iter = listIterator();
        int i = 0;
        while (iter.hasNext()){
            toReturn[i] = iter.next();
            i++;
        }
        return toReturn;
    }

    /**
     * Method that copies all the elements of the list inside a specified array
     * @param arrayTarget the array into which the elements of this list are to be
     *                    stored, if it is big enough (nulls are added for filling the remaining space);
     *                    otherwise, a new array of the
     *                    same size is allocated for this purpose.
     * @throws NullPointerException if arrayTarget == null;
     * @return a new array if arrayTarget.length < size(). arrayTarget if all list's elements can be contained
     * in this array
     */
    @Override
    public Object[] toArray(Object[] arrayTarget) {
        if(arrayTarget == null) throw new NullPointerException();
        if(arrayTarget.length < size()){
            return toArray();
        }else{
            HListIterator iter = listIterator();
            int i = 0;
            while (iter.hasNext()){
                arrayTarget[i] = iter.next();
                i++;
            }
            for(; i < arrayTarget.length; i++){
                arrayTarget[i] = null;
            }
            return arrayTarget;
        }
    }

    /**
     * Recursive method for updating the value of "to" as a consequence of any structural change in the sublist.
     * <br><br>
     * All ancestors of the current sublist must update their size following a structural change.
     * @param n can be +1 (there was an insertion) or -1 (there was a removal)
     */
    private void refreshIndexes(int n){
        to += n;
        if(father != null) father.refreshIndexes(n);
    }

    /**
     * Method for adding an element at the end of the list
     * @param obj element to be appended to this list.
     * @return always true (cannot fail)
     */
    @Override
    public boolean add(Object obj) {
        add(size(), obj);
        return true;
    }

    /**
     * Method for removing the first occurrence of the specified element
     * @param obj element to be removed from this list, if present.
     * @return true if an element is removed, false otherwise
     */
    @Override
    public boolean remove(Object obj) {
        HListIterator iter = listIterator();
        if(obj == null){
            while(iter.hasNext()){
                if(iter.next() == null){
                    remove(iter.previousIndex());
                    return true;
                }
            }
        }else{
            while(iter.hasNext()){
                Object current = iter.next();
                if(current != null && current.equals(obj)){
                    remove(iter.previousIndex());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method for checking if the elements of an entire collection are contained in the list
     * @param coll collection to be checked for containment in this list.
     * @throws NullPointerException if coll == null
     * @return true if the alle the elements of coll are contained in the list, false otherwise
     */
    @Override
    public boolean containsAll(HCollection coll) {
        if(coll == null) throw new NullPointerException();
        Object[] array = coll.toArray();
        for (Object o : array) {
            if (!contains(o)) return false;
        }
        return true;
    }

    /**
     * Method for appending an entire collection to the list
     * @param coll collection whose elements are to be added to this list.
     * @throws NullPointerException if coll == null
     * @return always true (cannot fail)
     */
    @Override
    public boolean addAll(HCollection coll) {
        if(coll == null) throw new NullPointerException();
        HIterator iterator = coll.iterator();
        HListIterator iter = listIterator(size());
        while(iterator.hasNext()){
            iter.add(iterator.next());
        }
        return true;
    }

    /**
     * Method for inserting all the elements of a specified collection in a specific position
     * @param index index at which to insert first element from the specified
     *              collection.
     * @param coll elements to be inserted into this list.
     * @throws IndexOutOfBoundsException if index < 0 || index > size()
     * @throws NullPointerException if coll == null
     * @return always true (cannot fail)
     */
    @Override
    public boolean addAll(int index, HCollection coll) {
        if(index < 0 || index > size()) throw new IndexOutOfBoundsException();
        if(coll == null) throw new NullPointerException();
        HIterator iterator = coll.iterator();
        HListIterator iter = listIterator(index);
        while(iterator.hasNext()){
            iter.add(iterator.next());
        }
        return true;
    }

    /**
     * Method for the removal of all the elements contained in the specified collection.
     * If an element has more occurrence inside the list, all the occurrence are removed.
     * @param coll collection that defines which elements will be removed from this
     *          list.
     * @throws NullPointerException if coll == null
     * @return true if there was a deletion, false otherwise
     */
    @Override
    public boolean removeAll(HCollection coll) {
        if(coll == null) throw new NullPointerException();
        boolean isChanged = false;
        HIterator iterator = coll.iterator();
        while(iterator.hasNext()){
            Object current = iterator.next();
            while(remove(current)){
                isChanged = true;
            }
        }
        return isChanged;
    }

    /**
     * Method for the removal of all the elements which aren't contained in the specified
     * collection.
     * @param coll collection that defines which elements this set will retain.
     * @throws NullPointerException if coll == null
     * @return true if at least one element is removed, false otherwise
     */
    @Override
    public boolean retainAll(HCollection coll) {
        if(coll == null) throw new NullPointerException();
        boolean isChanged = false;
        HListIterator iterator = listIterator();
        while(iterator.hasNext()){
            if(!coll.contains(iterator.next())){
                isChanged = true;
                iterator.remove();
            }
        }
        return isChanged;
    }

    /**
     * Method that removes all the elements inside a list. After that, the size
     * becomes 0
     */
    @Override
    public void clear() {
        retainAll(new ListAdapter());
    }

    /**
     * Method for comparing the list with a generic Object
     * @param obj the object to be compared for equality with this list.
     * @return true if obj and the list contain the same elements in the same order, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(! (obj instanceof ListAdapter)) return false;
        ListAdapter other = (ListAdapter) obj;
        if(this.size() != other.size()) return false;
        HListIterator iter = listIterator();
        int i = 0;
        while (iter.hasNext()){
            if(iter.next() != other.get(i)) return false;
            i++;
        }
        return true;
    }

    /**
     * Method that calculates the hashCode for the list.
     * If two lists are equals, then they must have the same hashCode
     * @return the hashCode for the list
     */
    @Override
    public int hashCode(){
        int hash = 1;
        HListIterator iter = listIterator();
        while(iter.hasNext()){
            Object current = iter.next();
            hash = 31 * hash + (current == null ? 0 : current.hashCode());
        }
        return hash;
    }

    /**
     * Method for inspecting the element in a specified position in the list
     * @param index index of element to return.
     * @throws IndexOutOfBoundsException if index < 0 || index >= size
     * @return the element in that position
     */
    @Override
    public Object get(int index) {
        if(index < 0 || index >= size()) throw new IndexOutOfBoundsException();
        return list.elementAt(index + from);
    }

    /**
     * Method for replacing an element in a specified position with a new one
     * @param index   index of element to replace.
     * @param element element to be stored at the specified position.
     * @throws IndexOutOfBoundsException if index < 0 || index >= size
     * @return the element that has been replaced
     */
    @Override
    public Object set(int index, Object element) {
        if(index < 0 || index >= this.size()) throw new IndexOutOfBoundsException();
        Object toReturn = get(index);
        list.setElementAt(element, index + from);
        return toReturn;
    }

    /**
     * Method for inserting an element in a specified position
     * @param index   index at which the specified element is to be inserted.
     * @param element element to be inserted.
     * @throws IndexOutOfBoundsException if index < 0 || index > size
     */
    @Override
    public void add(int index, Object element) {
        if(index < 0 || index > size()) throw new IndexOutOfBoundsException();
        list.insertElementAt(element, index + from);
        refreshIndexes(1);
    }

    /**
     * Method for remove an element specified by his index
     * @param index the index of the element to removed.
     * @throws IndexOutOfBoundsException if index < 0 || index >= size
     * @return the object that has been removed
     */
    @Override
    public Object remove(int index) {
        if(index < 0 || index >= size()) throw new IndexOutOfBoundsException();
        Object toRemove = get(index);
        list.removeElementAt(index + from);
        refreshIndexes(-1);
        return toRemove;
    }

    /**
     * Method that find the first occurrence of a specified element and return his position in the list
     * @param obj element to search for.
     * @return the index of obj in the list or -1 if the element isn't inside the list
     */
    @Override
    public int indexOf(Object obj) {
        HListIterator iter = listIterator();
        while(iter.hasNext()){
            Object current = iter.next();
            if(current == null ? obj == null : current.equals(obj)) return iter.previousIndex();
        }
        return -1;
    }

    /**
     * Method that find the last occurrence of a specified element and return his position in the list
     * @param obj element to search for.
     * @return the last index of obj in the list or -1 if the element isn't inside the list
     */
    @Override
    public int lastIndexOf(Object obj) {
        HListIterator iter = listIterator(size());
        while(iter.hasPrevious()){
            Object current = iter.previous();
            if(current == null ? obj == null : current.equals(obj)) return iter.nextIndex();
        }
        return -1;
    }

    /**
     * Method which returns a new instance of ListAdapterIterator
     * @return a new instance of ListAdapterIterator
     */
    @Override
    public HListIterator listIterator() {
        return new ListAdapterIterator();
    }

    /**
     * Method that returns a new instance of ListAdapterIterator pointing to the specified position
     * @param index index of first element to be returned from the list iterator (by
     *              a call to the next method).
     * @return a new instance of ListAdapterIterator pointing to the specified position
     */
    @Override
    public HListIterator listIterator(int index) {
        return new ListAdapterIterator(index);
    }

    /**
     * Method for creating a new sublist based on this list
     * @param fromIndex low endpoint (inclusive) of the subList.
     * @param toIndex   high endpoint (exclusive) of the subList.
     * @throws IndexOutOfBoundsException if fromIndex < 0 || toIndex > size() || fromIndex > toIndex
     * @return a new ListAdapter instance
     */
    @Override
    public HList subList(int fromIndex, int toIndex) {
        if(fromIndex < 0 || toIndex > size() || fromIndex > toIndex) throw new IndexOutOfBoundsException();
        return new ListAdapter(fromIndex + from, toIndex + from, this);
    }

    /**
     * ListIterator allows the programmer to traverse the ListAdapter in either direction.
     * <br><br>
     * It allows to modify the ListAdapter (insert, remove, replace).
     * <br><br>
     * It doesn't have a current element. Like ListIterator its position lies between an element and its predecessor.
     * @see myAdapter.HIterator
     * @see myAdapter.HListIterator
     * @author Andrea Stocco
     */
    private class ListAdapterIterator implements HListIterator{
        private int previous, next;
        private int lastCall; // 0: invalid, 1: next, -1: previous

        /**
         * Void constructor (no arguments). It creates a new listIterator on the start position of a listAdapter
         */
        public ListAdapterIterator(){
            this(0);
        }

        /**
         * It creates a new listIterator on the position specified by index.
         * <br><br>
         * A call to next() will return the element at index position
         * @param index where to place the new listIterator
         * @throws IndexOutOfBoundsException if index < 0 || index > size()
         */
        public ListAdapterIterator(int index){
            if(index < 0 || index > size()) throw new IndexOutOfBoundsException();
            this.next = index;
            this.previous = next - 1;
            this.lastCall = 0;
        }

        /**
         * Method that checks if there are other elements after the current position of the iterator
         * @return true if next < size, false otherwise
         */
        @Override
        public boolean hasNext() {
            return next < size();
        }

        /**
         * Method for moving forward the iterator inside the list
         * @return the element pointed to the iterator before its moving
         * @throws NoSuchElementException if the iterator is at the end of the list
         */
        @Override
        public Object next() {
            if(!hasNext()) throw new NoSuchElementException();
            Object toReturn = get(next);
            next++;
            previous = next - 1;
            lastCall = 1;
            return toReturn;
        }

        /**
         * Method that checks if there are other elements previous the current position of the iterator
         * @return true if previous >= 0, false otherwise
         */
        @Override
        public boolean hasPrevious() {
            return previous >= 0;
        }

        /**
         * Method for moving backward the iterator inside the list
         * @return the element pointed to the iterator before its moving
         * @throws NoSuchElementException if the iterator is at the beginning of the list
         */
        @Override
        public Object previous() {
            if(!hasPrevious()) throw new NoSuchElementException();
            Object toReturn = get(previous);
            previous--;
            next = previous + 1;
            lastCall = -1;
            return toReturn;
        }

        /**
         * Method that returns the position of the next element which would be returned by a next() call
         * @return the position of the next element which would be returned by a next() call
         */
        @Override
        public int nextIndex() {
            return next;
        }

        /**
         * Method that returns the position of the next element which would be returned by a previous() call
         * @return the position of the next element which would be returned by a previous() call
         */
        @Override
        public int previousIndex() {
            return previous;
        }

        /**
         * Method for removing the element previously visited
         * @throws IllegalStateException if there wasn't a call to previous() or next() before calling
         * this method
         */
        @Override
        public void remove() {
            if(lastCall == 0) throw new IllegalStateException();
            if(lastCall == 1){
                ListAdapter.this.remove(previous);
                next--;
                previous = next - 1;
            }
            if(lastCall == -1){
                ListAdapter.this.remove(next);
            }
            lastCall = 0;
        }

        /**
         * Method for replacing the element previously visited
         * @param obj element to be stored at the previously visited position.
         * @throws IllegalStateException if there wasn't a call to previous() or next() before calling
         * this method
         */
        @Override
        public void set(Object obj) {
            if(lastCall == 0) throw new IllegalStateException();
            if(lastCall == 1){
                ListAdapter.this.set(previous, obj);
            }
            if(lastCall == -1){
                ListAdapter.this.set(next, obj);
            }
        }

        /**
         * Method that inserts an element in the position returned by nextIndex()
         * @param obj the element to insert.
         */
        @Override
        public void add(Object obj) {
            ListAdapter.this.add(next, obj);
            next++;
            previous = next - 1;
            lastCall = 0;
        }
    }
}