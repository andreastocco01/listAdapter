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
     * Test of {@link ListAdapter#size()}
     * <br><br>
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
     * Test of {@link ListAdapter#isEmpty()}
     * <br><br>
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
     * Test of {@link ListAdapter#contains(Object)}
     * <br><br>
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
        teamList.add(null);
        assertTrue(teamList.contains(null));
        assertFalse(teamList.contains("Juventus"));
    }

    /**
     * Test of {@link ListAdapter#toArray()}
     * <br><br>
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
     * Test of {@link ListAdapter#toArray(Object[])}
     * <br><br>
     * Method for checking the correct copying of the elements of listAdapter into a specified array.
     * toArray(target) must return an array fitted to the list if target size is smaller or equal than listAdapter size.
     * If target size is greater than listAdapter's size, toArray(target) must return an array with null elements at the end.
     * <br><br>
     * Design test: first toArray(target) is executed with a target with the same size of teamList. Then it is executed with a target with
     * a different size. After that, this method is executed with a null parameter.
     * <br><br>
     * Preconditions: size() must work correctly
     * <br><br>
     * Postconditions: the returned array has to contain teamList's elements in addition to null element if target's size is greater
     * than teamList's size.
     * <br><br>
     * Execution result: in the first and the third case the returned array contains only the elements of the list. In the second case the returned
     * array contains the elements of the list plus nulls filling the remaining space.
     * toArray() has to throw an exception in the last case.
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
        assertThrows(NullPointerException.class, () -> teamList.toArray(null));
    }

    /**
     * Test of {@link ListAdapter#add(Object)}
     * <br><br>
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
     * Test of {@link ListAdapter#remove(Object)}
     * <br><br>
     * Method for checking the correct removal of an element (specified by his value)
     * <br><br>
     * Design test: A null element is added then removed. An element is removed, then is checked the correct position of the previous and next element.
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
        teamList.add(null);
        teamList.remove(null);
        teamList.remove("Manchester United");
        assertEquals("Bayern Monaco", teamList.get(3));
        assertEquals("Real Madrid", teamList.get(2));
        teamList.remove("Bayern Monaco");
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Ajax"}, teamList.toArray());

    }

    /**
     * Test of {@link ListAdapter#containsAll(HCollection)}
     * <br><br>
     * Method for checking if listAdapter contains an entire collection, which is passed by parameter
     * <br><br>
     * Design case: check if teamList contains an empty collection. add to the empty collection some elements which are contained in teamList
     * and check again if teamList contains this new collection. An element in emptyList is changed, then containsAll() is called again.
     * Then containsAll() is called with an invalid parameter.
     * <br><br>
     * Preconditions: add() and set() method must work correctly
     * <br><br>
     * Postconditions: containsAll() has to return true if and only if the listAdapter contains all the element of the parameter collection
     * <br><br>
     * Execution result: teamList must contain the emptyList. When elements are added to emptyList containsAll() has to return true again.
     * When another element is added to emptyList and then this element is changed, teamList mustn't contain the emptyList.
     * When the parameter is null, this method has to throw a NullPointerException.
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
        assertThrows(NullPointerException.class, () -> teamList.containsAll(null));
    }

    /**
     * Test of {@link ListAdapter#addAll(HCollection)}
     * <br><br>
     * Method for checking if listAdapter add at the end all element of a specified collection
     * <br><br>
     * Design test: some elements are added to emptyList. teamList has to add all of emptyList's elements. addAll() has to throw an exception
     * if the parameter is null.
     * <br><br>
     * Preconditions: add() and toArray() must work correctly.
     * <br><br>
     * Postconditions: emptyList's elements have to been added to teamList.
     * <br><br>
     * Execution result: after the addition of the collection, teamList has to have its previous elements plus emptyList's elements.
     * A NullPointerException has to be thrown when trying to add a null collection.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testAddAll(){
        emptyList.add("Chelsea");
        emptyList.add("Benfica");
        teamList.addAll(emptyList);
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax", "Chelsea", "Benfica"},
                teamList.toArray());
        assertThrows(NullPointerException.class, () -> teamList.addAll(null));
    }

    /**
     * Test of {@link ListAdapter#addAll(int, HCollection)}
     * <br><br>
     * Method for checking if listAdapter inserts correctly all the elements of a specified collection
     * <br><br>
     * Design test: some elements are added to emptyList, then emptyList is inserted at position 1 in teamList.
     * emptyList is changed, and it is inserted again at position size() -1. After that the collection is added in a position which is
     * out of bounds. A null collection is added in teamList.
     * <br><br>
     * Preconditions: add(), remove() and toArray() must work correctly.
     * <br><br>
     * Postconditions and Expected result: all the elements of emptyList are added in the correct position of teamList. An exception is
     * thrown in the last two cases.
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
        assertThrows(IndexOutOfBoundsException.class, () -> teamList.addAll(teamList.size() + 1, emptyList));
        assertThrows(NullPointerException.class, () -> teamList.addAll(0, null));
    }

    /**
     * Test of {@link ListAdapter#removeAll(HCollection)}
     * <br><br>
     * Method for checking the removal of all the element inside a specified collection
     * <br><br>
     * Design case: removeAll() is tried to call with an invalid parameter. Some elements which aren't inside teamList are added to emptyList. Then removeAll() is called.
     * emptyList is modified with elements which are inside teamList and removeAll() is called again.
     * <br><br>
     * Preconditions: add() and toArray must work correctly.
     * <br><br>
     * Postconditions: the emptyList's elements have to be removed from teamList
     * <br><br>
     * Execution result: in the first case, an exception must be thrown. teamList has to be the same after the first call of removeAll(). In the second case the elements inside emptyList have to
     * been removed.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testRemoveAll(){
        assertThrows(NullPointerException.class, () -> teamList.removeAll(null));
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
     * Test of {@link ListAdapter#retainAll(HCollection)}
     * <br><br>
     * Method for testing the removal of every element that isn't contained in the specified collection
     * <br><br>
     * Design test: retainAll() is called with an invalid parameter. Some elements are added to emptyList then retainAll() is called on teamList.
     * <br><br>
     * Preconditions: add() and toArray() must work correctly
     * <br><br>
     * Postconditions and Execution result: In the first case, the method must throw an exception. In the second case
     * after the call of retainAll(), teamList must contain only the elements which are inside emptyList
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testRetainAll(){
        assertThrows(NullPointerException.class, () -> teamList.retainAll(null));
        emptyList.add("Milan");
        emptyList.add("Real Madrid");
        emptyList.add("Chelsea");
        teamList.retainAll(emptyList);
        assertArrayEquals(new Object[]{"Milan", "Real Madrid"}, teamList.toArray());
    }

    /**
     * Test of {@link ListAdapter#clear()}
     * <br><br>
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
     * Test of {@link ListAdapter#equals(Object)}
     * <br><br>
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
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testEquals(){
        HList list = new ListAdapter(teamList);
        assertEquals(teamList, list);
        list.remove(0);
        assertNotEquals(teamList, list);
    }

    /**
     * Test of {@link ListAdapter#hashCode()}
     * <br><br>
     * Method for testing if different instances of listAdapter have the same hashCode.
     * <br><br>
     * Design test: newList is created with the elements of teamList. Are added to emptyList every element of teamList
     * (two elements are swapped). newList is emptied. emptyList is emptied then is filled with all the elements of teamList.
     * <br><br>
     * Preconditions: constructor(parameter), add(), clear() and addAll() must work correctly.
     * <br><br>
     * Postconditions and Execution result: hashCode must be the same in first and last case, different otherwise.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testHashCode(){
        HList newList = new ListAdapter(teamList);
        assertEquals(newList.hashCode(), teamList.hashCode());
        emptyList.add("Milan");
        emptyList.add("Liverpool");
        emptyList.add("Bayern Monaco");
        emptyList.add("Manchester United");
        emptyList.add("Real Madrid");
        emptyList.add("Ajax");
        assertNotEquals(emptyList.hashCode(), teamList.hashCode());
        newList.clear();
        assertNotEquals(newList.hashCode(), teamList.hashCode());
        emptyList.clear();
        emptyList.addAll(teamList);
        assertEquals(emptyList.hashCode(), teamList.hashCode());
    }

    /**
     * Test of {@link ListAdapter#get(int)}
     * <br><br>
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

    /**
     * Test of {@link ListAdapter#set(int, Object)}
     * <br><br>
     * Method for checking if set() modify the correct element of listAdapter
     * <br><br>
     * Design case: change the value of an element inside the list, then check if the modification of that element was
     * successful. Then set() is called on an element outside the listAdapter.
     * <br><br>
     * Preconditions: get() and size() must work correctly.
     * <br><br>
     * Postconditions: method set() has to modify the correct element if it is allowed.
     * <br><br>
     * Execution result: the first call of set() must be successful. the last call must throw an IndexOutOfBoundsException
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testSet(){
        teamList.set(3, "Inter");
        assertEquals("Inter", teamList.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> teamList.set(teamList.size(), "Juventus"));
    }

    /**
     * Test of {@link ListAdapter#add(int, Object)}
     * <br><br>
     * Method for testing if the addition of an element in every position of the listAdapter works correctly
     * <br><br>
     * Design test: an element is inserted inside teamList.
     * Then try to add an element in a position out from teamList. After that an element is added at the end of teamList.
     * <br><br>
     * Preconditions: get() and toArray() must work correctly.
     * <br><br>
     * Postconditions: the attempt to add elements out of bounds must throw an exception. The addition of elements inside the listAdapter
     * must be successful.
     * <br><br>
     * Execution result: after the insertion of the first element, a call to get() method must return the element just added.
     * toArray() method has to return the expected array. When trying to add an element out of bounds, an exception has to be thrown.
     * When an element is added at the end of teamList, this method mustn't throw an exception.
     */
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

    /**
     * Test of {@link ListAdapter#remove(int)}
     * <br><br>
     * Method for checking if an element is removed from the listAdapter (specified by his index)
     * <br><br>
     * Design case: an element is removed from teamList, then the element in that position has to be the element following the one removed.
     * remove() method is called in a position which isn't inside teamList.
     * <br><br>
     * Preconditions: get(), size() and toArray must work correctly.
     * <br><br>
     * Postconditions: teamList has to be like the expected array.
     * <br><br>
     * Execution result: the first remove() call must be successful. The second call has to throw an IndexOutOfBoundException.
     * toArray() has to return the expected array.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testRemoveWithIndex(){
        assertEquals("Milan", teamList.remove(0));
        assertEquals("Liverpool", teamList.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> teamList.remove(teamList.size()));
        assertArrayEquals(new Object[]{"Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"},
                teamList.toArray());
    }

    /**
     * Test of {@link ListAdapter#indexOf(Object)}
     * <br><br>
     * Method for checking if is returned the correct index of a specified element
     * <br><br>
     * Design case: first is requested the index of an element inside teamList. Then an element is modified and is requested the index
     * of the first occurrence of that element. After that is requested the index of an element which isn't inside teamList.
     * <br><br>
     * Precondition: set() method must work correctly
     * Postconditions: indexOf() must return the first occurrence of the specified element
     * Executing result: equality must be verified in all cases
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testIndexOf(){
        assertEquals(2, teamList.indexOf("Real Madrid"));
        teamList.set(0, "Ajax");
        assertEquals(0, teamList.indexOf("Ajax"));
        assertEquals(-1, teamList.indexOf(null));
    }

    /**
     * Test of {@link ListAdapter#indexOf(Object)}
     * <br><br>
     * Design case: two elements already present inside teamList are added to the list. in first and second cases
     * is required the index of the last occurrence of a specified element. In the last case is requested the index of
     * an element which isn't inside the list.
     * <br><br>
     * Preconditions: add() method must work correctly.
     * Postconditions: lastIndexOf() must return the last occurrence of the specified element
     * Execution result: equality must be verified in all cases
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testLastIndexOf(){
        teamList.add(4, "Milan");
        teamList.add(5, "Real Madrid");
        assertEquals(4, teamList.lastIndexOf("Milan"));
        assertEquals(5, teamList.lastIndexOf("Real Madrid"));
        assertEquals(-1, teamList.lastIndexOf(null));
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

        int after;
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