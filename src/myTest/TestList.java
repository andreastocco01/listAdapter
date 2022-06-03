package myTest;

import myAdapter.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;

//import java.util.NoSuchElementException;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class TestList
{
    HList l1 = null, l2 = null;
    HListIterator li = null;
    static String[] argv = {"pippo", "qui", "pluto", "paperino", "qui", "ciccio"};

    @Before
    public void setup()
    {
        //System.out.println("Instantiate an empty List");
        l1 = new ListAdapter();
    }

    @After
    public void cleanup()
    {
        //System.out.println("Purge all remaining elements");
        l1.clear();
    }

    @Test
    public void testSize(){
        assertEquals(0, l1.size());
        l1.add(0);
        assertEquals(1, l1.size());
        l1.remove(0);
        assertEquals(0, l1.size());
    }

    @Test
    public void testIsEmpty(){
        assertEquals(true, l1.isEmpty());
        l1.add(1);
        assertEquals(false, l1.isEmpty());
    }

    @Test
    public void testContains(){
        l1.add(0);
        assertEquals(true, l1.contains(0));
        assertEquals(false, l1.contains(10));
    }

    @Test
    public void testToArray(){
        l1.add(0);
        l1.add(1);
        l1.add(2);
        assertArrayEquals(new Object[]{0, 1, 2}, l1.toArray());
    }

    @Test
    public void toArrayWithTarget(){
        l1.add(0);
        l1.add(1);
        l1.add(2);
        Object[] target = new Object[l1.size()];
        assertArrayEquals(new Object[]{0, 1, 2}, l1.toArray(target));
        target = new Object[l1.size() + 2];
        assertArrayEquals(new Object[]{0, 1, 2, null, null}, l1.toArray(target));
        target = new Object[0];
        assertArrayEquals(new Object[]{0, 1, 2}, l1.toArray(target));
    }

    @Test
    public void testAdd(){
        l1.add(1);
        l1.add(2);
        assertEquals(2, l1.get(1));
        l1.remove(0);
        assertEquals(2, l1.get(0));
    }

    @Test
    public void testRemoveObject(){
        l1.add(1);
        l1.add(3);
        l1.add(1, 2);
        l1.add(3);
        l1.add(4);
        l1.remove(3);
        assertEquals(3, l1.get(2));
        assertEquals(4, l1.get(3));
    }

    @Test
    public void testContainsAll(){
        l2 = new ListAdapter();
        l1.add(0);
        l1.add(1);
        l1.add(2);
        l2.add(0);
        l2.add(1);
        l2.add(2);
        assertEquals(true, l1.containsAll(l2));
        l1.remove(2);
        assertEquals(true, l2.containsAll(l1));
    }

    @Test
    public void testGet(){
        l1.add(0);
        l1.add(3);
        assertEquals(0, l1.get(0));
        assertEquals(3, l1.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> {
            l1.get(2);
        });
    }

    @Test
    public void testSet(){
        l1.add(0);
        l1.add(1);
        l1.set(0, 1);
        assertEquals(1, l1.get(0));
        assertEquals(1, l1.get(1));
    }

    @Test
    public void testAddSpecifiedIndex(){
        l1.add(1);
        l1.add(2);
        l1.add(2, 3);
        assertEquals(3, l1.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> {
            l1.add(4, 5);
        });
    }

    @Test
    public void removeSpecifiedIndex(){
        l1.add(0);
        l1.add(3);
        assertEquals(0, l1.remove(0));
        assertEquals(3, l1.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> {
            l1.remove(1);
        });
    }

    /*@Test
    public void testBacking()
    {
        System.out.println("TestBacking");
        for(int i=0;i<argv.length;i++)
        {
            l1.add(argv[i]);
        }
        System.out.println("List.toString() ? " + l1);

        int dl0, dl1, dli, dsl0, dsl1, dsli;

        iterate(l1.iterator());
        System.out.println(l1 + " " + l1.size());
        dl0 = l1.size();

        l2 = l1.subList(0, argv.length/2);
        dsl0 = l2.size();

        l2.add("pipperissimo");
        dli = l1.size();
        dsli = l2.size();

        assertEquals("\n*** sublist add is NOT backed correctly ***\n", dli, dl0+1);
        assertEquals("\n*** sublist add is NOT backed correctly ***\n", dsli, dsl0+1);

        l2.remove("pipperissimo");
        assertEquals("\n*** list remove is NOT backed correctly ***\n", l1.size(), dl0);
        assertEquals("\n*** list remove is NOT backed correctly ***\n", l2.size(), dsl0);


        iterate(l2.iterator());
        System.out.println(l2 + " " + l2.size());

        l2.clear();
        dl1 = l1.size();
        dsl1 = l2.size();
        System.out.println(l1 + " " + l1.size());
        iterate(l1.iterator());
        System.out.println(l2 + " " + l2.size());
        iterate(l2.iterator());

        System.out.println(dl0 + " " + dl1 + " " + dsl0 + " " + dsl1);
        assertEquals("\n*** sublist is NOT backed correctly ***\n", dsl0, (dl0/2));
        assertEquals("\n*** sublist is NOT backed correctly ***\n", dsl1, 0);
        assertEquals("\n*** sublist is NOT backed correctly ***\n", dl1, (dl0 - dsl0));

    }

    @Test
    public void testRecursiveSublist()
    {
        System.out.println("TestRecursive SubListing");
        System.out.println(l1.size());

        assertEquals("List Starts not empty", l1.size(), 0);
        int prev = l1.size();
        for(int i=0;i<argv.length;i++)
        {
            l1.add(argv[i]);
        }
        assertEquals("List add not working correctly", l1.size(), (prev + argv.length));
        System.out.println(l1.size());
        prev = l1.size();
        for(int i=0;i<argv.length;i++)
        {
            l1.add(argv[i]);
        }
        assertEquals("List add not working correctly", l1.size(), (prev + argv.length));
        System.out.println(l1.size());
        prev = l1.size();
        for(int i=0;i<argv.length;i++)
        {
            l1.add(argv[i]);
        }
        assertEquals("List add not working correctly", l1.size(), (prev + argv.length));
        System.out.println(l1.size());
        iterate(l1.iterator());

        int after = 0;
        int count = 0;
        while(l1.size()>=2)
        {
            count++;
            prev = l1.size();
            l1 = l1.subList(1, prev-1);
            after = l1.size();
            System.out.println(after);
            assertEquals("Iterative Sublisting not working at " + count + " iteration", after, (prev-2));
            iterate(l1.iterator());
        }
    }
     */

    @Test
    public void testIterator3()
    {
        System.out.println("TestListIterator #3");
        int dl0, dl1, dl2;

        dl0 = l1.size();
        for(int i=0;i<argv.length;i++)
        {
            l1.add(argv[i]);
        }
        dl1 = l1.size();
        iterate(l1.iterator());
        li = l1.listIterator();
        while(li.hasNext())
            li.next();
        while(li.hasPrevious())
        {
            System.out.print(li.previous() + " ");
            iterate(l1.iterator());
            li.remove();
        }
        dl2 = l1.size();
        iterate(l1.iterator());

        assertEquals("\n*** insertion and forward to end and backward removal not working ***\n", dl1, (dl0+argv.length));
        assertEquals("\n*** insertion and forward to end and backward removal not working ***\n", dl2, 0);
    }

    @Test
    public void testIterator2()
    {
        System.out.println("TestListIterator #2");
        int dl0, dl1, dl2;
        dl0 = l1.size();
        for(int i=0;i<argv.length;i++)
        {
            l1.add(argv[i]);
        }
        dl1 = l1.size();
        iterate(l1.iterator());
        li = l1.listIterator();
        while(li.hasNext())
        {
            System.out.print(li.next() + " ");
            iterate(l1.iterator());
            li.remove();
        }
        dl2 = l1.size();
        iterate(l1.iterator());

        assertEquals("\n*** insertion and forward removal not working ***\n", dl1, (dl0+argv.length));
        assertEquals("\n*** insertion and forward removal not working ***\n", dl2, 0);
    }

    @Test
    public void testIterator1()
    {
        System.out.println("TestListIterator #1");
        iterate(l1.iterator());
        li = l1.listIterator(l1.size());
        while(li.hasPrevious())
        {
            System.out.print(li.previous() + " ");
            iterate(l1.iterator());
            li.remove();
        }
        iterate(l1.iterator());

        assertEquals("\n*** listiterator from end not working ***\n", l1.size(), 0);
    }

    public static void iterate(HIterator iter)
    {
        System.out.print("{");
        while(iter.hasNext())
        {
            System.out.print(iter.next() + "; ");
        }
        System.out.println("}");
    }

    @Test
    public void testHasNext(){
        l1.add(0);
        l1.add(1);
        l1.add(2);
        HIterator iterator = l1.iterator();
        assertEquals(true, iterator.hasNext());
        iterator.next();
        assertEquals(true, iterator.hasNext());
        iterator.next();
        iterator.next();
        assertEquals(false, iterator.hasNext());
    }

    @Test
    public void testNext(){
        l1.add(0);
        l1.add(1);
        l1.add(2);
        HListIterator iter = (HListIterator) l1.iterator();
        assertEquals(0, iter.next());
        iter.next();
        iter.previous();
        iter.next();
        assertEquals(2, iter.next());
        assertThrows(NoSuchElementException.class, () -> {
            iter.next();
        });
    }

    @Test
    public void testHasPrevious(){
        l1.add(0);
        l1.add(1);
        l1.add(2);
        HListIterator iter = (HListIterator) l1.iterator();
        assertEquals(false, iter.hasPrevious());
        while (iter.hasNext()) iter.next();
        assertEquals(true, iter.hasPrevious());
        iter.previous();
        iter.previous();
        assertEquals(true, iter.hasPrevious());
        iter.previous();
        assertEquals(false, iter.hasPrevious());
    }

    @Test
    public void testPrevious(){
        l1.add(0);
        l1.add(1);
        l1.add(2);
        HListIterator iter = (HListIterator) l1.iterator();
        assertThrows(NoSuchElementException.class, () -> {
            iter.previous();
        });
        iter.next();
        assertEquals(0, iter.previous());
        assertThrows(NoSuchElementException.class, () -> {
            iter.previous();
        });
    }

    @Test
    public void testNextIndex(){
        l1.add(0);
        l1.add(0);
        l1.add(0);
        HListIterator iter = (HListIterator) l1.iterator();
        assertEquals(0, iter.nextIndex());
        while(iter.hasNext()) iter.next();
        assertEquals(l1.size(), iter.nextIndex());
    }

    @Test
    public void testPreviousIndex(){
        l1.add(0);
        l1.add(0);
        l1.add(0);
        HListIterator iter = (HListIterator) l1.iterator();
        assertEquals(-1, iter.previousIndex());
        while(iter.hasNext()) iter.next();
        assertEquals(2, iter.previousIndex());
    }

    @Test
    public void testRemove(){
        l1.add(0);
        l1.add(1);
        l1.add(2);
        l1.add(4);
        HListIterator iter = (HListIterator) l1.iterator();
        assertThrows(IllegalStateException.class, () -> {
            iter.remove();
        });
        iter.next();
        iter.remove();
        assertEquals(1, iter.next());
        while(iter.hasNext())iter.next();
        iter.previous();
        iter.remove();
        assertEquals(2, iter.previous());
        assertEquals(2, l1.size());
        assertEquals(1, l1.get(0));
        assertEquals(2, l1.get(1));
    }

    @Test
    public void testSetIterator(){
        l1.add(0);
        l1.add(1);
        l1.add(2);
        l1.add(4);
        HListIterator iter = (HListIterator) l1.iterator();
        assertThrows(IllegalStateException.class, () -> {
            iter.set(3);
        });
        iter.next();
        iter.set(3);
        assertEquals(3, l1.get(0));
    }

    @Test
    public void testAddIterator(){
        HListIterator iter = (HListIterator) l1.iterator();
        iter.add(0);
        iter.add(2);
        iter.previous();
        iter.add(1);
        assertEquals(1, iter.previous());
        assertEquals(0, iter.previous());
    }
}