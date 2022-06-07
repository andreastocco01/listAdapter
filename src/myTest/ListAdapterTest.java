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
 * This class tests the functionality of all the ListAdapter methods.
 * It checks the correct behaviour of all the ListIterator methods.
 * <br><br>
 * The first test cases in this class simply test all the ListAdapter and ListIterator's methods. The subsequent test cases
 * are more complex because they test more functionalities at once. They are real simulations of the use of the ListAdapter class.
 * <br><br>
 * Every test case is based on the string array "teams" which contains some important European football clubs.
 * Before each test case, there is a reminder comment to the state of the listAdapter before the execution of that test.
 * <br><br>
 * Every execution variable is never in an uninitialized state (i.e. teamList = null).
 * Before each test case, a new empty listAdapter must be instantiated. A new listAdapter, which contains the elements of the
 * array "teams", must be instantiated, together with a listIterator pointing the first element of that list.
 * <br><br>
 * A test case is correct if all the tests that verify the correct functioning give a positive result.
 * <br><br>
 * To execute each test case of this class, simply run TestRunner class.
 * <br><br>
 * Execution variable:
 * HList teamList - main listAdapter on which all the methods are tested.
 * HList emptyList - empty listAdapter which is used to test teamList. In the majority of the tests, the emptyList is filled with test values.
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

    /**
     * Method for initializing the execution variables.
     * An empty listAdapter, another one which contains "teams" elements and a listIterator attached to this one are created.
     * In this way every method that is invoked always has a valid state.
     * <br><br>
     * Preconditions: the constructors have to correctly initialize the execution variables
     */
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

    /**
     * Method for checking the correct size of listAdapter.
     * <br><br>
     * Design test: test of the size of the teamList before and after adding and removing an element.
     * emptyList doesn't have changes, so size() must return 0.
     * <br><br>
     * Preconditions: the add() and remove() method must work in the right way
     * <br><br>
     * Postconditions and Execution result: the returned value of size() method must correspond to the number of elements inside the listAdapter
     */
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

    /**
     * Method for checking if a listAdapter is empty.
     * <br><br>
     * Design test: teamList is emptied and isEmpty() is invoked. An element is added and isEmpty is invoked again.
     * emptyList doesn't have changes, so isEmpty() must return true.
     * <br><br>
     * Preconditions: clear() and add() methods must work correctly
     * <br><br>
     * Postconditions: isEmpty must return true if the collection is empty, false otherwise
     * <br><br>
     * Expected result: true after the removal of all elements, false after the insert.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testIsEmpty(){
        teamList.clear();
        assertTrue(teamList.isEmpty());
        teamList.add("Milan");
        assertFalse(teamList.isEmpty());
        assertTrue(emptyList.isEmpty());
    }

    /**
     * Method for checking if listAdapter contains a specific element.
     * <br><br>
     * Design test: an element is added to teamList. Check if that element is present inside the list. Check if an element
     * that wasn't added isn't inside the list.
     * <br><br>
     * Preconditions: add() method must work correctly.
     * <br><br>
     * Postconditions: contains() must return true if the parameter is inside the list, false otherwise.
     * <br><br>
     * Expected result: true when is searched the element that was added, false when is searched the element that isn't present in the list.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testContains(){
        teamList.add("Barcelona");
        assertTrue(teamList.contains("Barcelona"));
        assertFalse(teamList.contains("Juventus"));
    }

    /**
     * Method for checking the conversion from listAdapter to array of Object.
     * <br><br>
     * Design test: teamList is converted to array. Then two elements are added and the list is converted again.
     * <br><br>
     * Preconditions: add() method must work correctly.
     * <br><br>
     * Postconditions: the returned array has to contain the same elements of teamList and emptyList
     * <br><br>
     * Execution result: toArray() called on teamList must return an array with the same elements of the list even after the addition of an element.
     * toArray() called on emptyList must return an empty array.
     */
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

    /**
     * Method for checking the correct copy of the elements of listAdapter into a specified array.
     * toArray(target) have to return a fitted array to the list if target's size is smaller or equal than listAdapter's size.
     * If target's size in greater than listAdapter's size, toArray(target) must return an array with null elements at the end.
     * <br><br>
     * Design test: toArray(target) is executed before with a target with the same size of teamList. Then is executed with a target with
     * a different size.
     * <br><br>
     * Preconditions: size() must work correctly
     * <br><br>
     * Postconditions: the returned array has to contain teamList's elements in addition of null element if target's size is greater
     * than teamList's size.
     * <br><br>
     * Execution result: returned array has to be fitted in the first and last case and has to contain null elements in the second case.
     */
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

    /**
     * Method for checking the correct addition of the elements to the listAdapter
     * <br><br>
     * Design test: two elements are added at the end of teamList. Check if the last element is correct, then the last element is removed
     * and is checked again if the last element is correct
     * <br><br>
     * Preconditions: remove() and size() method must work correctly.
     * <br><br>
     * Postconditions and Execution result: the last element of the list has to be the last element added
     */
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

    /**
     * Method for checking the correct removal of an element (specified by his value)
     * <br><br>
     * Design test: an element is removed, then is checked the correct position of the previous and next element.
     * Another element is removed, then is checked if the entire list matches the expected result
     * <br><br>
     * Preconditions: get() and toArray() method must work correctly.
     * <br><br>
     * Postconditions: remove the correct element from the listAdapter
     * <br><br>
     * Execution result: the list has to contain the remaining elements in the correct order
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testRemoveObject(){
        teamList.remove("Manchester United");
        assertEquals("Bayern Monaco", teamList.get(3));
        assertEquals("Real Madrid", teamList.get(2));
        teamList.remove("Bayern Monaco");
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Ajax"}, teamList.toArray());
    }

    /**
     * Method for checking if listAdapter contains an entire collection, which is passed by parameter
     * <br><br>
     * Design case: check if teamList contains an empty collection. add to the empty collection some elements which are contained in teamList
     * and check again if teamList contains this new collection. An element in emptyList is changed, then containsAll() is called again.
     * <br><br>
     * Preconditions: add() and set() method must work correctly
     * <br><br>
     * Postconditions: containsAll() has to return true if and only if the listAdapter contains all the element of the parameter collection
     * <br><br>
     * Execution result: teamList must contain the emptyList. When elements are added to emptyList containsAll() has to return true again.
     * When another element is added to emptyList and then this element is changed, teamList mustn't contain the emptyList.
     */
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

    /**
     * Method for checking if listAdapter add at the end all element of a specified collection
     * <br><br>
     * Design test: some elements are added to emptyList. teamList has to add all of emptyList's elements
     * <br><br>
     * Preconditions: add() and toArray() must work correctly.
     * <br><br>
     * Postconditions: emptyList's elements have to been added to teamList.
     * <br><br>
     * Execution result: after the addition of the collection, teamList has to have its previous elements plus emptyList's elements.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testAddAll(){
        emptyList.add("Chelsea");
        emptyList.add("Benfica");
        teamList.addAll(emptyList);
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax", "Chelsea", "Benfica"},
                teamList.toArray());
    }

    /**
     * Method for checking if listAdapter inserts correctly all the elements of a specified collection
     * <br><br>
     * Design test: some elements are added to emptyList, then emptyList is inserted at position 1 in teamList.
     * emptyList is changed and is inserted again in the position size() -1.
     * <br><br>
     * Preconditions: add(), remove() and toArray() must work correctly.
     * <br><br>
     * Postconditions and Expected resul: all the elements of emptyList has to be added in the correct position of teamList
     */
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

    /**
     * Method for checking the removal of all the element inside a specified collection
     * <br><br>
     * Design case: some elements which aren't inside teamList are added to emptyList. Then removeAll() is called.
     * emptyList is modified with elements which are inside teamList and removeAll() is called again.
     * <br><br>
     * Preconditions: add() and toArray must work correctly.
     * <br><br>
     * Postconditions: the emptyList's elements have to be removed from teamList
     * <br><br>
     * Execution result: teamList has to be the same after the first call of removeAll(). In the second case the elements inside emptyList have to
     * been removed.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testRemoveAll(){
        emptyList.add("Chelsea");
        emptyList.add("Benfica");
        teamList.removeAll(emptyList);
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}, teamList.toArray());
        emptyList.add("Liverpool");
        emptyList.add("Manchester United");
        teamList.removeAll(emptyList);
        assertArrayEquals(new Object[]{"Milan", "Real Madrid", "Bayern Monaco", "Ajax"}, teamList.toArray());
    }

    /**
     * Method for testing the removal of each element which aren't contained in the specified collection
     * <br><br>
     * Design test: some elements are added to emptyList, then retainAll() is called on teamList.
     * <br><br>
     * Preconditions: add() and toArray() must work correctly
     * <br><br>
     * Postconditions and Execution result: after the call of retainAll(), teamList must contain only the elements which are inside emptyList
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testRetainAll(){
        emptyList.add("Milan");
        emptyList.add("Real Madrid");
        emptyList.add("Chelsea");
        teamList.retainAll(emptyList);
        assertArrayEquals(new Object[]{"Milan", "Real Madrid"}, teamList.toArray());
    }

    /**
     * Method for checking the emptying of a listAdapter.
     * <br><br>
     * Design method: teamList is emptied, then the size of the list has to be 0.
     * <br><br>
     * Preconditions: toArray() must work correctly
     * <br><br>
     * Postconditions: teamList has to be empty
     * <br><br>
     * Execution result: toArray() has to return an empty array
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testClear(){
        teamList.clear();
        assertArrayEquals(new Object[]{}, teamList.toArray());
    }

    /**
     * Method for checking if two instances of listAdapter contain the same elements
     * <br><br>
     * Design case: a new listAdapter is created by copying all the elements of teamList. Then an element is remove from that list.
     * <br><br>
     * Preconditions: constructor with parameter must work correctly.
     * <br><br>
     * Postconditions: equals() has to return true if the two listAdapter contains the same elements and has the same size.
     * <br><br>
     * Execution result: the first call of equals() has to return true. the second call has to return false.
     */
    @Test
    public void testEquals(){
        HList list = new ListAdapter(teamList);
        assertTrue(teamList.equals(list));
        list.remove(0);
        assertFalse(teamList.equals(list));
    }

    /**
     * Method for checking if get() method returns the correct element inside the listAdapter.
     * <br><br>
     * Design test: two elements inside teamList are inspected. Then an index which isn't inside teamList is inspected.
     * An element is added to teamList, then another two elements are inspected.
     * <br><br>
     * Preconditions: add() and size() method must work correctly.
     * <br><br>
     * Postconditions: the element returned by get() has to be the expected element.
     * <br><br>
     * Execution result: the first and last elements are inspected. An index out of bounds is inspected but an exception is thrown.
     * Then an element is insert and all subsequent elements are shifted.
     */
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
    public void testRemoveWithIndex(){
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

        assertEquals("\n*** listIterator from end not working ***\n", teamList.size(), 0);
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