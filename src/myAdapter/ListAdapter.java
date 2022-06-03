package myAdapter;

import javax.imageio.plugins.tiff.ExifInteroperabilityTagSet;
import java.util.ListResourceBundle;
import java.util.NoSuchElementException;

public class ListAdapter implements HList, HCollection{
    private int from, to;
    private Vector list;
    ListAdapter father;

    public ListAdapter() {
        from = 0;
        to = 0;
        list = new Vector();
        father = null;
    }

    private ListAdapter(int from, int to, ListAdapter listAdapter){
        this.from = from;
        this.to = to;
        this.list = listAdapter.list;
        this.father = listAdapter;
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
        while (iter.hasNext()){
            if(iter.next().equals(obj)) return true;
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

    /**
     *
     * la lista puo' contenere valori null.
     * se arrayTarget ha dimensioni maggiori della lista, le posizioni non riempite vengono settate a null.
     * questo puo' creare confusione
     */
    @Override
    public Object[] toArray(Object[] arrayTarget) {
        if(arrayTarget == null) throw new NullPointerException();
        if(arrayTarget.length < size()){
            Object[] toReturn = toArray();
            return toReturn;
        }else{
            HListIterator iter = listIterator();
            int i = 0;
            while (iter.hasNext()){
                arrayTarget[i] = iter.next();
            }
            for(; i < arrayTarget.length; i++){
                arrayTarget[i] = null;
            }
            return arrayTarget;
        }
    }

    private void refreshIndexes(int n){
        to += n;
        if(father != null) father.refreshIndexes(n);
    }

    /**
     *
     * ritorna true se obj viene aggiunto in coda alla lista.
     * la lista puo' contenere duplicati e non ci sono limitazioni sui tipi (anche il tipo null e' concesso).
     * questo metodo, quindi, non fallirà mai.
     */
    @Override
    public boolean add(Object obj) {
        list.addElement(to, obj);
        return true;
    }

    @Override
    public boolean remove(Object obj) {
        HListIterator iter = listIterator();
        while (iter.hasNext()){
            if(iter.next().equals(obj)){
                remove(iter.previousIndex());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(HCollection coll) {
        if(coll == null) throw new NullPointerException();
        Object[] array = coll.toArray();
        for(int i = 0; i < array.length; i++){
            if(!contains(array[i])) return false;
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
            if(remove(iterator.next())){
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
                remove(iterator.previousIndex());
            }
        }
        return isChanged;
    }

    @Override
    public void clear() {
        while(size() > 0){
            remove(0);
        }
    }

    @Override
    public boolean equals(Object obj){
        ListAdapter sub = new ListAdapter();
        HListIterator iter = listIterator();
        while(iter.hasNext()) sub.add(iter.next());
        return sub.list.equals(obj);
    }

    @Override
    public int hashCode(){
        ListAdapter sub = new ListAdapter();
        HListIterator iter = listIterator();
        while(iter.hasNext()) sub.add(iter.next());
        return sub.list.hashCode();
    }

    @Override
    public Object get(int index) {
        if(index < 0 || index >= size()) throw new IndexOutOfBoundsException();
        Object toReturn = list.elementAt(index + from);
        return toReturn;
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
        if(index < 0 || index > this.size()) throw new IndexOutOfBoundsException();
        this.list.insertElementAt(element, index);
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
        ListAdapter sub = new ListAdapter();
        HListIterator iter = listIterator();
        while(iter.hasNext()) sub.add(iter.next());
        return sub.list.indexOf(obj);
    }

    @Override
    public int lastIndexOf(Object obj) {
        ListAdapter sub = new ListAdapter();
        HListIterator iter = listIterator(size());
        while(iter.hasPrevious()) sub.add(iter.previous());
        return sub.list.indexOf(obj);
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
        return new ListAdapter(fromIndex, toIndex, this);
    }

    private class ListIterator implements HIterator, HListIterator{
        private int previous, next;
        private int lastCall; // 0: invalid, 1: next, -1: previous

        public ListIterator(){
            this.next = ListAdapter.this.from;
            this.previous = next - 1;
            this.lastCall = 0;
        }

        public ListIterator(int index){
            if(index < 0 || index > size()) throw new IndexOutOfBoundsException();
            this.next = index + from;
            this.previous = next -1;
            this.lastCall = 0;
        }
        @Override
        public boolean hasNext() {
            return next < ListAdapter.this.to;
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
            return previous >= ListAdapter.this.from;
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

    public static void main(String[] args) {
        ListAdapter list = new ListAdapter();
        list.add(12);
        list.add(0);
        list.add(0);
        HListIterator iter = list.listIterator();
        System.out.println(iter.previousIndex());
        System.out.println(list.to);
        System.out.println(iter.next());
        while(iter.hasNext()) System.out.println(iter.next());
    }
}