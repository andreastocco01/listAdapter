package myTest;

import myAdapter.*;
import org.junit.Before;
import org.junit.Test;

//import java.util.NoSuchElementException;
import java.sql.BatchUpdateException;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * This class tests the functionality of all ListAdapter methods.
 * It checks the correct behaviour of all ListIterator methods.
 * <p>
 * The first test cases in this class simply test all methods of ListAdapter and ListIterator. The subsequent test cases
 * are more complex because they test more functionality at once. They are real simulations of the use of the ListAdapter class.
 * <p>
 * All test cases are based on the string array "teams" which contains some important european football club.
 * Before each test case there is a reminder comment to the state of the listAdapter before the execution of that test.
 * <p>
 * All execution variables are never in an uninitialized state (i.e. teamList = null)
 * Before each test case a new empty listAdapter must be instantiated. A new listAdapter which contains the elements of the
 * array "teams" must be instantiated and a listIterator pointing the first element of that list.
 * <p>
 * A test case is correct if all the tests that verify the correct functioning give positive result.
 * <p>
 * Execution variable:
 * HList teamList - main listAdapter on which al methods are tested.
 * HList emptyList - empty listAdapter which is used to test teamList. In most tests this is filled with test values.
 * HListIterator iter - listIterator attached to teamList.
 * String [] teams - array which contains the elements for initializing teamList.
 *
 * @see myAdapter.ListAdapter
 * @author Andrea Stocco
 */
public class ListAdapterTest
{
    HList teamList, emptyList;
    HListIterator iter;
    static String[] teams = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"};

    @Before
    public void setup()
    {
        teamList = new ListAdapter();
        emptyList = new ListAdapter();
        iter = teamList.listIterator();
        for(String team : teams){
            teamList.add(team);
        }
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testSize(){
        assertEquals(6, teamList.size());
        teamList.add("Barcelona");
        assertEquals(7, teamList.size());
        teamList.remove(0);
        assertEquals(6, teamList.size());
        assertEquals(0, emptyList.size());
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testIsEmpty(){
        teamList.clear();
        assertTrue(teamList.isEmpty());
        teamList.add("Milan");
        assertFalse(teamList.isEmpty());
        assertTrue(emptyList.isEmpty());
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testContains(){
        teamList.add("Barcelona");
        assertTrue(teamList.contains("Barcelona"));
        assertFalse(teamList.contains("Juventus"));
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testToArray(){
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}, teamList.toArray());
        teamList.add("Chelsea");
        teamList.add("Barcelona");
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax", "Chelsea", "Barcelona"},
                teamList.toArray());
        assertArrayEquals(new Object[]{}, emptyList.toArray());
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testToArrayWithTarget(){
        Object[] target = new Object[teamList.size()];
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}, teamList.toArray(target));
        target = new Object[teamList.size() + 2];
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax", null, null}, teamList.toArray(target));
        target = new Object[0];
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}, teamList.toArray(target));
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testAdd(){
        teamList.add("Barcelona");
        teamList.add("Chelsea");
        assertEquals("Chelsea", teamList.get(teamList.size() - 1));
        teamList.remove(teamList.size() - 1);
        assertEquals("Barcelona", teamList.get(teamList.size() - 1));
        assertEquals("Milan", teamList.get(0));
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testRemoveObject(){
        teamList.remove("Manchester United");
        assertEquals("Bayern Monaco", teamList.get(3));
        assertEquals("Real Madrid", teamList.get(2));
        teamList.remove("Bayern Monaco");
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Ajax"}, teamList.toArray());
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testContainsAll(){
        assertTrue(teamList.containsAll(emptyList));
        emptyList.add("Bayern Monaco");
        emptyList.add("Milan");
        emptyList.add("Liverpool");
        assertTrue(teamList.containsAll(emptyList));
        emptyList.add("Ajax");
        assertTrue(teamList.containsAll(emptyList));
        emptyList.set(0, "Inter");
        assertFalse(teamList.containsAll(emptyList));
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testAddAll(){
        emptyList.add("Chelsea");
        emptyList.add("Benfica");
        teamList.addAll(emptyList);
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax", "Chelsea", "Benfica"},
                teamList.toArray());
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testAddAllWithIndex(){
        emptyList.add("Chelsea");
        emptyList.add("Benfica");
        teamList.addAll(1, emptyList);
        assertArrayEquals(new Object[]{"Milan", "Chelsea", "Benfica", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"},
                teamList.toArray());
        emptyList.remove("Chelsea");
        emptyList.remove("Benfica");
        emptyList.add("Porto");
        teamList.addAll(teamList.size() - 1, emptyList);
        assertArrayEquals(new Object[]{"Milan", "Chelsea", "Benfica", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Porto", "Ajax"},
                teamList.toArray());
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testRemoveAll(){
        emptyList.add("Chelsea");
        emptyList.add("Benfica");
        teamList.removeAll(emptyList);
        assertEquals(6, teamList.size());
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}, teamList.toArray());
        emptyList.add("Liverpool");
        emptyList.add("Manchester United");
        teamList.removeAll(emptyList);
        assertEquals(4, teamList.size());
        assertArrayEquals(new Object[]{"Milan", "Real Madrid", "Bayern Monaco", "Ajax"}, teamList.toArray());
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testRetainAll(){
        emptyList.add("Milan");
        emptyList.add("Real Madrid");
        emptyList.add("Chelsea");
        teamList.retainAll(emptyList);
        assertArrayEquals(new Object[]{"Milan", "Real Madrid"}, teamList.toArray());
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testClear(){
        teamList.clear();
        assertArrayEquals(new Object[]{}, teamList.toArray());
        assertEquals(0, teamList.size());
    }

    @Test
    public void testEquals(){
        HList list = new ListAdapter(teamList);
        assertTrue(teamList.equals(list));
        list.remove(0);
        assertFalse(teamList.equals(list));
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testGet(){
        assertEquals("Milan", teamList.get(0));
        assertEquals("Ajax", teamList.get(teamList.size() - 1));
        assertThrows(IndexOutOfBoundsException.class, () -> teamList.get(teamList.size()));
        teamList.add(1, "Chelsea");
        assertEquals("Chelsea", teamList.get(1));
        assertEquals("Liverpool", teamList.get(2));
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testSet(){
        teamList.set(3, "Inter");
        assertEquals("Inter", teamList.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> teamList.set(teamList.size(), "Juventus"));
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testAddWithIndex(){
        teamList.add(2, "Porto");
        assertEquals("Porto", teamList.get(2));
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Porto", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"},
                teamList.toArray());
        assertThrows(IndexOutOfBoundsException.class, () -> teamList.add(8, "Inter"));
        teamList.add(teamList.size(), "Chelsea");
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Porto", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax", "Chelsea"},
                teamList.toArray());
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testRemoveSpecifiedIndex(){
        assertEquals("Milan", teamList.remove(0));
        assertEquals("Liverpool", teamList.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> teamList.remove(teamList.size()));
        assertArrayEquals(new Object[]{"Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"},
                teamList.toArray());
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testIndexOf(){
        assertEquals(2, teamList.indexOf("Real Madrid"));
        teamList.set(0, "Ajax");
        assertEquals(0, teamList.indexOf("Ajax"));
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testLastIndexOf(){
        teamList.add(4, "Milan");
        teamList.add(5, "Real Madrid");
        assertEquals(4, teamList.lastIndexOf("Milan"));
        assertEquals(5, teamList.lastIndexOf("Real Madrid"));
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testHasNextListIterator(){
        assertTrue(iter.hasNext());
        iter.next();
        assertTrue(iter.hasNext());
        iter.next();
        iter.next();
        iter.next();
        iter.next();
        assertTrue(iter.hasNext());
        iter.next();
        assertFalse(iter.hasNext());
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testNextListIterator(){
        assertEquals("Milan", iter.next());
        iter.next();
        iter.previous();
        iter.next();
        assertEquals("Real Madrid", iter.next());
        assertEquals("Manchester United", teamList.get(iter.nextIndex()));
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testHasPreviousListIterator(){
        assertFalse(iter.hasPrevious());
        while (iter.hasNext()) iter.next();
        assertTrue(iter.hasPrevious());
        iter.previous();
        iter.previous();
        assertTrue(iter.hasPrevious());
        iter.previous();
        while (iter.hasPrevious()) iter.previous();
        assertFalse(iter.hasPrevious());
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testPreviousListIterator(){
        assertThrows(NoSuchElementException.class, () -> iter.previous());
        iter.next();
        assertEquals("Milan", iter.previous());
        assertThrows(NoSuchElementException.class, () -> iter.previous());
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testNextIndexListIterator(){
        assertEquals(0, iter.nextIndex());
        while(iter.hasNext()) iter.next();
        assertEquals(teamList.size(), iter.nextIndex());
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testPreviousIndexListIterator(){
        assertEquals(-1, iter.previousIndex());
        while(iter.hasNext()) iter.next();
        assertEquals(5, iter.previousIndex());
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testRemoveListIterator(){
        assertThrows(IllegalStateException.class, () -> iter.remove());
        iter.next();
        iter.remove();
        assertEquals("Liverpool", iter.next());
        while(iter.hasNext())iter.next();
        iter.previous();
        iter.remove();
        assertEquals("Bayern Monaco", iter.previous());
        assertArrayEquals(new Object[]{"Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco"}, teamList.toArray());
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testSetListIterator(){
        assertThrows(IllegalStateException.class, () -> iter.set("Chelsea"));
        iter.next();
        iter.set("Chelsea");
        assertEquals("Chelsea", teamList.get(0));
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testAddListIterator(){
        iter.add("Chelsea");
        iter.add("Porto");
        iter.previous();
        assertEquals("Porto", iter.next());
        iter.previous();
        iter.add("Barcelona");
        assertEquals("Barcelona", iter.previous());
        iter.next();
        assertEquals("Porto", iter.next());
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testListIterator(){
        iter.add("Barcelona");
        iter.add("Benfica");
        assertThrows(IllegalStateException.class, () -> iter.set("Chelsea"));
        iter.previous();
        iter.set("Chelsea");
        assertEquals("Chelsea", teamList.get(iter.nextIndex()));
        iter.remove();
        assertEquals("Barcelona", teamList.get(iter.previousIndex()));
        iter.add("Porto");
        iter.previous();
        iter.previous();
        iter.next();
        iter.remove();
        assertThrows(IndexOutOfBoundsException.class, () -> teamList.get(iter.previousIndex()));
        assertEquals("Porto", teamList.get(iter.nextIndex()));
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testBacking() {
        System.out.println("TestBacking");
        System.out.println("List.toString() ? " + teamList);

        int dl0, dl1, dli, dsl0, dsl1, dsli;

        iterate(teamList.iterator());
        System.out.println(teamList + " " + teamList.size());
        dl0 = teamList.size();

        emptyList = teamList.subList(0, teams.length/2);
        dsl0 = emptyList.size();

        emptyList.add("Chelsea");
        dli = teamList.size();
        dsli = emptyList.size();

        assertEquals("\n*** sublist add is NOT backed correctly ***\n", dli, dl0+1);
        assertEquals("\n*** sublist add is NOT backed correctly ***\n", dsli, dsl0+1);

        emptyList.remove("Chelsea");
        assertEquals("\n*** list remove is NOT backed correctly ***\n", teamList.size(), dl0);
        assertEquals("\n*** list remove is NOT backed correctly ***\n", emptyList.size(), dsl0);


        iterate(emptyList.iterator());
        System.out.println(emptyList + " " + emptyList.size());

        emptyList.clear();
        dl1 = teamList.size();
        dsl1 = emptyList.size();
        System.out.println(teamList + " " + teamList.size());
        iterate(teamList.iterator());
        System.out.println(emptyList + " " + emptyList.size());
        iterate(emptyList.iterator());

        System.out.println(dl0 + " " + dl1 + " " + dsl0 + " " + dsl1);
        assertEquals("\n*** sublist is NOT backed correctly ***\n", dsl0, (dl0/2));
        assertEquals("\n*** sublist is NOT backed correctly ***\n", dsl1, 0);
        assertEquals("\n*** sublist is NOT backed correctly ***\n", dl1, (dl0 - dsl0));

    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testRecursiveSublist() {
        System.out.println("TestRecursive SubListing");
        teamList.clear();
        System.out.println(teamList.size());

        assertEquals("List Starts not empty", teamList.size(), 0);
        int prev = teamList.size();
        for (String s : teams) {
            teamList.add(s);
        }
        assertEquals("List add not working correctly", teamList.size(), (prev + teams.length));
        System.out.println(teamList.size());
        prev = teamList.size();
        for (String s : teams) {
            teamList.add(s);
        }
        assertEquals("List add not working correctly", teamList.size(), (prev + teams.length));
        System.out.println(teamList.size());
        prev = teamList.size();
        for (String s : teams) {
            teamList.add(s);
        }
        assertEquals("List add not working correctly", teamList.size(), (prev + teams.length));
        System.out.println(teamList.size());
        iterate(teamList.iterator());

        int after = 0;
        int count = 0;
        while(teamList.size()>=2)
        {
            count++;
            prev = teamList.size();
            teamList = teamList.subList(1, prev-1);
            after = teamList.size();
            System.out.println(after);
            assertEquals("Iterative Sublisting not working at " + count + " iteration", after, (prev-2));
            iterate(teamList.iterator());
        }
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testIterator3() {
        System.out.println("TestListIterator #3");
        int dl0, dl1, dl2;
        teamList.clear();

        dl0 = teamList.size();
        for (String s : teams) {
            teamList.add(s);
        }
        dl1 = teamList.size();
        iterate(teamList.iterator());
        iter = teamList.listIterator();
        while(iter.hasNext())
            iter.next();
        while(iter.hasPrevious())
        {
            System.out.print(iter.previous() + " ");
            iterate(teamList.iterator());
            iter.remove();
        }
        dl2 = teamList.size();
        iterate(teamList.iterator());

        assertEquals("\n*** insertion and forward to end and backward removal not working ***\n", dl1, (dl0+teams.length));
        assertEquals("\n*** insertion and forward to end and backward removal not working ***\n", dl2, 0);
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testIterator2() {
        System.out.println("TestListIterator #2");
        int dl0, dl1, dl2;
        teamList.clear();
        dl0 = teamList.size();
        for (String s : teams) {
            teamList.add(s);
        }
        dl1 = teamList.size();
        iterate(teamList.iterator());
        iter = teamList.listIterator();
        while(iter.hasNext())
        {
            System.out.print(iter.next() + " ");
            iterate(teamList.iterator());
            iter.remove();
        }
        dl2 = teamList.size();
        iterate(teamList.iterator());

        assertEquals("\n*** insertion and forward removal not working ***\n", dl1, (dl0+teams.length));
        assertEquals("\n*** insertion and forward removal not working ***\n", dl2, 0);
    }

    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testIterator1() {
        System.out.println("TestListIterator #1");
        teamList.clear();
        iterate(teamList.iterator());
        iter = teamList.listIterator(teamList.size());
        while(iter.hasPrevious())
        {
            System.out.print(iter.previous() + " ");
            iterate(teamList.iterator());
            iter.remove();
        }
        iterate(teamList.iterator());

        assertEquals("\n*** listiterator from end not working ***\n", teamList.size(), 0);
    }

    public static void iterate(HIterator iter) {
        System.out.print("{");
        while(iter.hasNext())
        {
            System.out.print(iter.next() + "; ");
        }
        System.out.println("}");
    }
}