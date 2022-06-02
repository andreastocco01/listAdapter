package myAdapter;

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
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object obj) {
        return false;
    }

    @Override
    public HIterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] arrayTarget) {
        return new Object[0];
    }

    @Override
    public boolean add(Object obj) {
        return false;
    }

    @Override
    public boolean remove(Object obj) {
        return false;
    }

    @Override
    public boolean containsAll(HCollection coll) {
        return false;
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
        return null;
    }

    @Override
    public Object set(int index, Object element) {
        return null;
    }

    @Override
    public void add(int index, Object element) {

    }

    @Override
    public Object remove(int index) {
        return null;
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
