package myAdapter;

import javax.imageio.plugins.tiff.ExifInteroperabilityTagSet;
import java.util.ListResourceBundle;
import java.util.NoSuchElementException;

public class ListAdapter implements HList, HCollection{
    private int from, to;
    private Vector list;
    boolean isFather;

    public ListAdapter() {
        from = 0;
        to = 0;
        list = new Vector();
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public boolean contains(Object obj) {
        return this.list.contains(obj);
    }

    @Override
    public HIterator iterator() {
        return new ListIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] toReturn = new Object[this.size()];
        this.list.copyInto(toReturn);
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
        if(arrayTarget.length < this.size()){
            Object[] toReturn = this.toArray();
            return toReturn;
        }else{
            int i = 0;
            for(; i < this.size(); i++){
                arrayTarget[i] = this.get(i);
            }
            for(; i < arrayTarget.length; i++){
                arrayTarget[i] = null;
            }
            return arrayTarget;
        }
    }

    /**
     *
     * ritorna true se obj viene aggiunto in coda alla lista.
     * la lista puo' contenere duplicati e non ci sono limitazioni sui tipi (anche il tipo null e' concesso).
     * questo metodo, quindi, non fallirà mai.
     */
    @Override
    public boolean add(Object obj) {
        this.list.addElement(obj);
        this.to++;
        return true;
    }

    @Override
    public boolean remove(Object obj) {
        if(this.list.removeElement(obj)){
            this.to--;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(HCollection coll) {
        if(coll == null) throw new NullPointerException();
        Object[] array = coll.toArray();
        for(int i = 0; i < array.length; i++){
            if(!this.contains(array[i])) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(HCollection coll) {
        return false;
    }

    @Override
    public boolean addAll(int index, HCollection coll) {
        return false;
    }

    @Override
    public boolean removeAll(HCollection coll) {
        return false;
    }

    @Override
    public boolean retainAll(HCollection coll) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Object get(int index) {
        if(index < 0 || index > this.size()) throw new IndexOutOfBoundsException();
        Object toReturn = this.list.elementAt(index);
        return toReturn;
    }

    @Override
    public Object set(int index, Object element) {
        if(index < 0 || index > this.size()) throw new IndexOutOfBoundsException();
        Object toReturn = this.get(index);
        this.list.setElementAt(element, index);
        return toReturn;
    }

    @Override
    public void add(int index, Object element) {
        if(index < 0 || index > this.size()) throw new IndexOutOfBoundsException();
        this.list.insertElementAt(element, index);
        this.to++;
    }

    @Override
    public Object remove(int index) {
        if(index < 0 || index > this.size()) throw new IndexOutOfBoundsException();
        Object toRemove = this.list.elementAt(index);
        this.list.removeElementAt(index);
        this.to--;
        return toRemove;
    }

    @Override
    public int indexOf(Object obj) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object obj) {
        return 0;
    }

    @Override
    public HListIterator listIterator() {
        return null;
    }

    @Override
    public HListIterator listIterator(int index) {
        return null;
    }

    @Override
    public HList subList(int fromIndex, int toIndex) {
        return null;
    }

    private class ListIterator implements HIterator, HListIterator{
        private int previous, next;
        private int lastCall; // 0: inizio, 1: next, -1: previous
        private final int INVALID_STATE = -1;

        public ListIterator(){
            this.previous = INVALID_STATE;
            this.next = 0;
            lastCall = 0;
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
            previous++;
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
            next--;
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
            if(previous == INVALID_STATE || next == INVALID_STATE) throw new IllegalStateException();
            if(lastCall == 1){
                ListAdapter.this.remove(previous);
                next--;
                previous = INVALID_STATE;
            }
            if(lastCall == -1){
                ListAdapter.this.remove(next);
                next = INVALID_STATE;
            }
        }

        @Override
        public void set(Object obj) {
            if(previous == INVALID_STATE || next == INVALID_STATE) throw new IllegalStateException();
            if(lastCall == 1){
                ListAdapter.this.set(previous, obj);
                next--;
                previous = INVALID_STATE;
            }
            if(lastCall == -1){
                ListAdapter.this.set(next, obj);
                next = INVALID_STATE;
            }
        }

        @Override
        public void add(Object obj) {
            ListAdapter.this.add(next, obj);
            next++;
            previous++;
        }
    }
}