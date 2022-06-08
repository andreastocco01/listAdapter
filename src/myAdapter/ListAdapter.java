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
public class ListAdapter implements HList, HCollection{

    private final int from;
    private int to;
    private final Vector list;
    private final ListAdapter father;

    /**
     * Void Constructor (no arguments).
     * Creates an empty ListAdapter.
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

    @Override
    public int size() {
        return to - from;
    }

    @Override
    public boolean isEmpty() {
        return to == from;
    }

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

    @Override
    public HIterator iterator() {
        return new ListIterator();
    }

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

    @Override
    public boolean add(Object obj) {
        add(to, obj);
        return true;
    }

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

    @Override
    public boolean containsAll(HCollection coll) {
        if(coll == null) throw new NullPointerException();
        Object[] array = coll.toArray();
        for (Object o : array) {
            if (!contains(o)) return false;
        }
        return true;
    }

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

    @Override
    public void clear() {
        retainAll(new ListAdapter());
    }

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

    @Override
    public Object get(int index) {
        if(index < 0 || index >= size()) throw new IndexOutOfBoundsException();
        return list.elementAt(index + from);
    }

    @Override
    public Object set(int index, Object element) {
        if(index < 0 || index >= this.size()) throw new IndexOutOfBoundsException();
        Object toReturn = get(index + from);
        list.setElementAt(element, index + from);
        return toReturn;
    }

    @Override
    public void add(int index, Object element) {
        if(index < 0 || index > size()) throw new IndexOutOfBoundsException();
        list.insertElementAt(element, index + from);
        refreshIndexes(1);
    }

    @Override
    public Object remove(int index) {
        if(index < 0 || index >= size()) throw new IndexOutOfBoundsException();
        Object toRemove = list.elementAt(index + from);
        list.removeElementAt(index + from);
        refreshIndexes(-1);
        return toRemove;
    }

    @Override
    public int indexOf(Object obj) {
        HListIterator iter = listIterator();
        while(iter.hasNext()){
            Object current = iter.next();
            if(current == null ? obj == null : current.equals(obj)) return iter.previousIndex();
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object obj) {
        HListIterator iter = listIterator(size());
        while(iter.hasPrevious()){
            Object current = iter.previous();
            if(current == null ? obj == null : current.equals(obj)) return iter.nextIndex();
        }
        return -1;
    }

    @Override
    public HListIterator listIterator() {
        return new ListIterator();
    }

    @Override
    public HListIterator listIterator(int index) {
        return new ListIterator(index);
    }

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
    private class ListIterator implements HIterator, HListIterator{
        private int previous, next;
        private int lastCall; // 0: invalid, 1: next, -1: previous

        /**
         * Void constructor (no arguments). It creates a new listIterator on the start position of a listAdapter
         */
        public ListIterator(){
            this(0);
        }

        /**
         * It creates a new listIterator on the position specified by index.
         * <br><br>
         * A call to next() will return the element at index position
         * @param index where to place the new listIterator
         */
        public ListIterator(int index){
            if(index < 0 || index > size()) throw new IndexOutOfBoundsException();
            this.next = index;
            this.previous = next - 1;
            this.lastCall = 0;
        }
        @Override
        public boolean hasNext() {
            return next < size();
        }

        @Override
        public Object next() {
            if(!hasNext()) throw new NoSuchElementException();
            Object toReturn = get(next);
            next++;
            previous = next - 1;
            lastCall = 1;
            return toReturn;
        }

        @Override
        public boolean hasPrevious() {
            return previous >= 0;
        }

        @Override
        public Object previous() {
            if(!hasPrevious()) throw new NoSuchElementException();
            Object toReturn = get(previous);
            previous--;
            next = previous + 1;
            lastCall = -1;
            return toReturn;
        }

        @Override
        public int nextIndex() {
            return next;
        }

        @Override
        public int previousIndex() {
            return previous;
        }

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

        @Override
        public void add(Object obj) {
            ListAdapter.this.add(next, obj);
            next++;
            previous = next - 1;
            lastCall = 0;
        }
    }
}