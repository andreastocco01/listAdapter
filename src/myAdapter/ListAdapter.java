package myAdapter;

import javax.imageio.plugins.tiff.ExifInteroperabilityTagSet;
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
        return null;
    }

    @Override
    public Object[] toArray() {
        Object[] toReturn = new Object[this.size()];
        this.list.copyInto(toReturn);
        return toReturn;
    }

    @Override
    public Object[] toArray(Object[] arrayTarget) {
        return new Object[0];
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
        return null;
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
}