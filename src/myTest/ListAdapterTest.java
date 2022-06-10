package myTest;

import myAdapter.*;
import org.junit.Before;
import org.junit.Test;

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
     * Method for initializing the execution variables:
     * an empty list, another one which contains "teams" elements, and an iterator, attached to the latter, are created.
     * In this way every method invoked always has a valid state.
     * <br><br>
     * <strong>Preconditions</strong>: the constructors have to correctly initialize the execution variables.
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
     * Test of {@link ListAdapter#ListAdapter()} and {@link ListAdapter#ListAdapter(HCollection)}
     * <br><br>
     * Method for checking the correct behaviour of the public constructors of ListAdapter.
     * <br><br>
     * <strong>Design</strong>: a new list is instantiated by the void constructor. Another list is instantiated
     * by the parameterized constructor.
     * <br><br>
     * <strong>Preconditions</strong>: size() must work in the right way
     * <br><br>
     * <strong>Postconditions</strong> and <strong>Expected result</strong>: in the first case the void constructor
     * must return an empty list. In the last case the parameterized constructor must return a list which contains all the
     * elements of teamList.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testConstructors(){
        HList list = new ListAdapter();
        assertEquals(0, list.size());
        HList list2 = new ListAdapter(teamList);
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}, list2.toArray());
    }

    /**
     * Test of {@link ListAdapter#size()}
     * <br><br>
     * Method for checking the correct size of the list.
     * <br><br>
     * <strong>Design</strong>: checking the size of teamList before and after adding or removing an element.
     * emptyList doesn't have changes, so size() must return 0.
     * <br><br>
     * <strong>Preconditions</strong>: the add() and remove() method must work in the right way
     * <br><br>
     * <strong>Postconditions</strong> and <strong>Expected result</strong>: the returned value of size()
     * method must correspond to the number of elements inside the list.
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
     * Method for checking if the list is empty.
     * <br><br>
     * <strong>Design</strong>: teamList is emptied and isEmpty() is invoked. An element is added and isEmpty is invoked again.
     * emptyList doesn't have changes, so isEmpty() must return true.
     * <br><br>
     * <strong>Preconditions</strong>: clear() and add() method must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: isEmpty must return true if the collection is empty, otherwise it must return false.
     * <br><br>
     * <strong>Expected result</strong>: true after the removal of all the elements, false after the insert.
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
     * Method for checking if the list contains a specific element.
     * <br><br>
     * <strong>Design</strong>: an element is added to teamList. Check if that element is present inside the list. Check if an element
     * that wasn't added isn't inside the list.
     * <br><br>
     * <strong>Preconditions</strong>: add() method must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: contains() must return true if the parameter is inside the list, otherwise it must return false.
     * <br><br>
     * <strong>Expected result</strong>: it must return true when the added element is searched, false when the element that
     * isn't in the list is searched.
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
     * Method for checking the conversion from ListAdapter to Object[].
     * <br><br>
     * <strong>Design</strong>: teamList is converted to array. Then two elements are added and the list is converted again.
     * <br><br>
     * <strong>Preconditions</strong>: add() method must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: the returned array must contain the same elements of teamList and emptyList.
     * <br><br>
     * <strong>Expected result</strong>: toArray() called on teamList must return an array with the same elements of the list even after the addition of an element.
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
     * Method for checking the correct copying process of the elements of the list into a specified array.
     * toArray(target) must return an array with the same size of the list if target size is smaller or equal than list size.
     * If target size is greater than list's size, toArray(target) must return an array with null elements at the end.
     * <br><br>
     * <strong>Design</strong>: at the beginning, toArray(target) is executed with a target with the same size of teamList.
     * Then it is executed with a target with
     * a different size. After that, this method is executed with a null parameter.
     * <br><br>
     * <strong>Preconditions</strong>: size() must work correctly
     * <br><br>
     * <strong>Postconditions</strong>: the returned array must contain teamList's elements in addition to null elements if target's size
     * is greater
     * than teamList's size.
     * <br><br>
     * <strong>Expected result</strong>: in the first and in the third case, the returned array contains only the elements of the list.
     * In the second case the returned
     * array contains the elements of the list plus null ones filling the remaining space.
     * toArray() must throw an exception in the last case.
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
     * Method for checking the correct addition of the elements to the list.
     * <br><br>
     * <strong>Design</strong>: two elements are added at the end of teamList. Check if the last element is correct,
     * then the last element is removed
     * and is checked again if the last element is correct.
     * <br><br>
     * <strong>Preconditions</strong>: remove() and size() method must work correctly.
     * <br><br>
     * <strong>Postconditions</strong> and <strong>Expected result</strong>: the last element of the list must be the last one to be added.
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
     * <strong>Design</strong>: A null element is added then removed. An element is removed, then is checked the correct
     * position of the previous and next element.
     * Another element is removed, then is checked if the entire list matches the expected result
     * <br><br>
     * <strong>Preconditions</strong>: get() and toArray() method must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: remove the correct element from the listAdapter
     * <br><br>
     * <strong>Expected result</strong>: the list must contain the remaining elements in the correct order
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
     * Method for checking if the list contains an entire collection, which is passed by parameter
     * <br><br>
     * <strong>Design</strong>: check if teamList contains an empty collection. add to the empty collection some elements
     * which are contained in teamList
     * and check again if teamList contains this new collection. An element in emptyList is changed, then containsAll() is called again.
     * Then containsAll() is called with an invalid parameter.
     * <br><br>
     * <strong>Preconditions</strong>: add() and set() method must work correctly
     * <br><br>
     * <strong>Postconditions</strong>: containsAll() must return true if and only if the list contains all the element
     * of the parameter collection
     * <br><br>
     * <strong>Expected result</strong>: teamList must contain the emptyList. When elements are added to emptyList containsAll()
     * must return true again.
     * When another element is added to emptyList and then this element is changed, teamList mustn't contain the emptyList.
     * When the parameter is null, this method must throw a NullPointerException.
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
     * Method for checking if the list adds at the end all the elements of a specified collection
     * <br><br>
     * <strong>Design</strong>: some elements are added to emptyList. teamList must add all the emptyList's elements. addAll() must
     * throw an exception
     * if the parameter is null.
     * <br><br>
     * <strong>Preconditions</strong>: add() and toArray() must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: emptyList's elements have to been added to teamList.
     * <br><br>
     * <strong>Expected result</strong>: after the addition of the collection, teamList must have its previous elements plus emptyList's elements.
     * A NullPointerException must be thrown when trying to add a null collection.
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
     * Method for checking if the list inserts correctly all the elements of a specified collection
     * <br><br>
     * <strong>Design</strong>: some elements are added to emptyList, then emptyList is inserted at position 1 in teamList.
     * emptyList is changed, and it is inserted again at position size() -1. After that, the collection is added in a position which is
     * out of bounds. A null collection is added in teamList.
     * <br><br>
     * <strong>Preconditions</strong>: add(), remove() and toArray() must work correctly.
     * <br><br>
     * <strong>Postconditions</strong> and <strong>Expected result</strong>: all the elements of emptyList are added in the correct
     * position of teamList. An exception is
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
     * <strong>Design</strong>: removeAll() is tried to call with an invalid parameter. Some elements which aren't inside teamList are added to emptyList. Then removeAll() is called.
     * emptyList is modified with elements which are inside teamList and removeAll() is called again.
     * <br><br>
     * <strong>Preconditions</strong>: add() and toArray must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: the emptyList's elements have to be removed from teamList
     * <br><br>
     * <strong>Expected result</strong>: in the first case, an exception must be thrown. teamList must be the same after the first call of removeAll(). In the second case the elements inside emptyList have to
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
     * <strong>Design</strong>: retainAll() is called with an invalid parameter. Some elements are added to emptyList then retainAll() is called on teamList.
     * <br><br>
     * <strong>Preconditions</strong>: add() and toArray() must work correctly
     * <br><br>
     * <strong>Postconditions</strong> and <strong>Expected result</strong>: In the first case, the method must throw an exception. In the second case,
     * after the call of retainAll(), teamList must contain only the elements which are inside emptyList.
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
     * Method for checking the emptying of the list.
     * <br><br>
     * <strong>Design</strong>: teamList is emptied, then the size of the list must be 0.
     * <br><br>
     * <strong>Preconditions</strong>: toArray() must work correctly
     * <br><br>
     * <strong>Postconditions</strong>: teamList must be empty
     * <br><br>
     * <strong>Expected result</strong>: toArray() must return an empty array
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
     * Method for checking if two instances of ListAdapter contain the same elements
     * <br><br>
     * <strong>Design</strong>: a new list is created by copying all the elements of teamList. Then an element is removed from that list.
     * <br><br>
     * <strong>Preconditions</strong>: constructor with parameter must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: equals() must return true if the two lists contains the same elements and have the same size.
     * <br><br>
     * <strong>Expected result</strong>: the first call of equals() must return true. the second call must return false.
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
     * Method for testing if different instances of ListAdapter have the same hashCode.
     * <br><br>
     * <strong>Design</strong>: newList is created with the elements of teamList. Every element of teamList is added to emptyList
     * (two elements are swapped). newList is emptied. emptyList is emptied then is filled with all the elements of teamList.
     * <br><br>
     * <strong>Preconditions</strong>: constructor(parameter), add(), clear() and addAll() must work correctly.
     * <br><br>
     * <strong>Postconditions</strong> and <strong>Expected result</strong>: hashCode must be the same in first and last case, different otherwise.
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
     * Method for checking if get() method returns the correct element inside the list.
     * <br><br>
     * <strong>Design</strong>: two elements inside teamList are inspected. Then an index which isn't inside teamList is inspected.
     * An element is added to teamList, then another two elements are inspected.
     * <br><br>
     * <strong>Preconditions</strong>: add() and size() method must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: the element returned by get() must be the expected element.
     * <br><br>
     * <strong>Expected result</strong>: the first and last elements are inspected. An index out of bounds is inspected but an exception is thrown.
     * Then an element is inserted and all subsequent elements are shifted.
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
     * Method for checking if set() modifies the correct element of the list
     * <br><br>
     * <strong>Design</strong>: is changed the value of an element inside the list, then is checked if the modification of that element was
     * successful. Then set() is called on an element outside the list.
     * <br><br>
     * <strong>Preconditions</strong>: get() and size() must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: method set() must modify the correct element if it is allowed.
     * <br><br>
     * <strong>Expected result</strong>: the first call of set() must be successful. the last call must throw an IndexOutOfBoundsException
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
     * Method for testing if the addition of an element in every position of the list works correctly
     * <br><br>
     * <strong>Design</strong>: an element is inserted inside teamList.
     * Then you try to add an element in a position out from teamList. After that an element is added at the end of teamList.
     * <br><br>
     * <strong>Preconditions</strong>: get() and toArray() must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: the attempt to add elements out of bounds must throw an exception. The addition of elements inside
     * the list
     * must be successful.
     * <br><br>
     * <strong>Expected result</strong>: after the insertion of the first element, a call to get() method must return the element just added.
     * toArray() method must return the expected array. When trying to add an element out of bounds, an exception must be thrown.
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
     * Method for checking if an element is removed from the list (specified by his index)
     * <br><br>
     * <strong>Design</strong>: an element is removed from teamList, then the element in that position must be the element following
     * the one removed.
     * remove() method is called in a position which isn't inside teamList.
     * <br><br>
     * <strong>Preconditions</strong>: get(), size() and toArray must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: teamList must be like the expected array.
     * <br><br>
     * <strong>Expected result</strong>: the first remove() call must be successful. The second call must throw an IndexOutOfBoundException.
     * toArray() must return the expected array.
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
     * Method for checking if is returned the index of the first occurrence of a specified element
     * <br><br>
     * <strong>Design</strong>: first is requested the index of an element inside teamList. Then an element is modified and is requested the index
     * of the first occurrence of that element. After that is requested the index of an element which isn't inside teamList.
     * <br><br>
     * <strong>Preconditions</strong>: set() method must work correctly
     * <br><br>
     * <strong>Postconditions</strong>: indexOf() must return the first occurrence of the specified element
     * <br><br>
     * <strong>Expected result</strong>: equality must be verified in all cases
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
     * Test of {@link ListAdapter#lastIndexOf(Object)}
     * <br><br>
     * Method for checking if is returned the index of the last occurrence of a specified element
     * <br><br>
     * <strong>Design</strong>: two elements already present inside teamList are added to the list. In the first and second cases
     * is required the index of the last occurrence of a specified element. In the last case is requested the index of
     * an element which isn't inside the list.
     * <br><br>
     * <strong>Preconditions</strong>: add() method must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: lastIndexOf() must return the last occurrence of the specified element
     * <br><br>
     * <strong>Expected result</strong>: equality must be verified in all cases
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

    /**
     * Test of {@link ListAdapter#subList(int, int)}
     * <br><br>
     * Method for checking the correct update of the father list if there is a structural change in the subList
     * <br><br>
     * <strong>Design</strong>: is created a subList from teamList (exclusive first and last element). Then is created a subList from the
     * previous subList (exclusive first and last element). Then is inserted an element in the second subList.
     * <br><br>
     * <strong>Preconditions</strong>: size(), add(), toArray() methods must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: if a structural change is made on a subList, the father list must be updated.
     * <br><br>
     * <strong>Expected result</strong>: after the creation of two subLists, an element is added in the last subList.
     * toArray() method must return the expected array for every list. It must contain the new element inserted in the correct
     * position.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testSublist(){
        HList sub1 = teamList.subList(1, teamList.size() - 1);
        assertArrayEquals(new Object[]{"Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco"}, sub1.toArray());
        HList sub2 = sub1.subList(1, sub1.size() - 1);
        assertArrayEquals(new Object[]{"Real Madrid", "Manchester United"}, sub2.toArray());
        sub2.add(1, "Chelsea");
        assertArrayEquals(new Object[]{"Real Madrid", "Chelsea", "Manchester United"}, sub2.toArray());
        assertArrayEquals(new Object[]{"Liverpool", "Real Madrid", "Chelsea", "Manchester United", "Bayern Monaco"}, sub1.toArray());
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Chelsea", "Manchester United", "Bayern Monaco", "Ajax"},
                teamList.toArray());
    }

    /**
     * Test of {@link ListAdapter.ListAdapterIterator#ListAdapterIterator()} and {@link ListAdapter.ListAdapterIterator#ListAdapterIterator(int)}
     * <br><br>
     * Method for checking the correct behaviour of the public constructors of ListAdapterIterator.
     * <br><br>
     * <strong>Design</strong>: a new iterator is instantiated by the void constructor. Another iterator
     * is instantiated by the parameterized constructor
     * <br><br>
     * <strong>Preconditions</strong>: next() must work in the right way
     * <br><br>
     * <strong>Postconditions</strong> and <strong>Expected result</strong>: in the first case the void constructor
     * must return an iterator pointing the first element of teamList. In the last case the parameterized
     * constructor must return an iterator pointing to the fourth element of the list
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testListIteratorConstructors(){
        HListIterator iterator = teamList.listIterator();
        assertEquals("Milan", iterator.next());
        HListIterator iterator2 = teamList.listIterator(4);
        assertEquals("Bayern Monaco", iterator2.next());
    }

    /**
     * Test of {@link ListAdapter.ListAdapterIterator#hasNext()}
     * <br><br>
     * Method for checking if hasNext() returns true if there is an element after the current element.
     * <br><br>
     * <strong>Design</strong>: an iterator is created in front of the list and hasNext() in called. Then the iterator is
     * moved forward and hasNext() is called again. When the iterator came to the end of teamList, hasNext() is called again.
     * <br><br>
     * <strong>Preconditions</strong>: no preconditions are necessary
     * <br><br>
     * <strong>Postconditions</strong>: hasNext() must return true if iter isn't came to the end of the list, false otherwise.
     * <br><br>
     * <strong>Expected result</strong>: in the first three cases the method must return true (because it isn't came to the end).
     * In the last case it must return false.
     */
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

    /**
     * Test of {@link ListAdapter.ListAdapterIterator#next()}
     * <br><br>
     * Method for checking if next() method returns the correct element of the list
     * <br><br>
     * <strong>Design</strong>: the iterator is moved forward and backward in teamList and next() method is called
     * many times.
     * <br><br>
     * <strong>Preconditions</strong>: previous() and nextIndex() methods must work correctly.
     * <br><br>
     * <strong>Postconditions</strong> and <strong>Expected result</strong>: next() must always return the correct element
     */
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

    /**
     * Test of {@link ListAdapter.ListAdapterIterator#hasPrevious()}
     * <br><br>
     * Method for checking if hasPrevious() returns true if there is an element before the current element.
     * <br><br>
     * <strong>Design</strong>: a listIterator is created in front of the list and hasPrevious() in called. Then the iterator is
     * moved to the end of teamList. Then the iterator is moved backward and hasPrevious() is called several times.
     * <br><br>
     * <strong>Preconditions</strong>: next() and previous() methods must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: hasPrevious() must return true if iter isn't came to the front of the list, false otherwise.
     * <br><br>
     * <strong>Expected result</strong>: in the first case the method must return false (iter is in front of the list). In the second and third
     * case the method must return true. In the last case it must return false (because iter is again in the front of the list).
     */
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

    /**
     * Test of {@link ListAdapter.ListAdapterIterator#previous()}
     * <br><br>
     * Method for checking if previous() method returns the correct element of the list.
     * <br><br>
     * <strong>Design</strong>: iter is moved forward and backward, the method is called three times.
     * <br><br>
     * <strong>Preconditions</strong>: next() method must work correctly.
     * <br><br>
     * <strong>Postconditions</strong> and <strong>Expected result</strong>: previous() must return always the correct value. In the first and the last
     * case an exception must be launched. In the second case previous() must return the correct element.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testPreviousListIterator(){
        assertThrows(NoSuchElementException.class, () -> iter.previous());
        iter.next();
        assertEquals("Milan", iter.previous());
        assertThrows(NoSuchElementException.class, () -> iter.previous());
    }

    /**
     * Test of {@link ListAdapter.ListAdapterIterator#nextIndex()}
     * <br><br>
     * Method for checking if nextIndex() method returns the index of the element
     * that will be returned with a next() call.
     * <br><br>
     * <strong>Design</strong>: iter is in front of teamList, nextIndex() is called. Then iter is moved forward to the end of the list.
     * nextIndex() is called again.
     * <br><br>
     * <strong>Preconditions</strong>: next() and size() method must work correctly.
     * <br><br>
     * <strong>Postconditions</strong> and <strong>Expected result</strong>: nextIndex() must return the index of the next element in every case.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testNextIndexListIterator(){
        assertEquals(0, iter.nextIndex());
        while(iter.hasNext()) iter.next();
        assertEquals(teamList.size(), iter.nextIndex());
    }

    /**
     * Test of {@link ListAdapter.ListAdapterIterator#previousIndex()}
     * <br><br>
     * Method for checking if previousIndex() method returns the index of the element
     * that will be returned with a previous() call.
     * <br><br>
     * <strong>Design</strong>: iter is in front of teamList, previousIndex() is called. Then iter is moved forward
     * to the end of the list. The method is called again.
     * <br><br>
     * <strong>Preconditions</strong>: next() method must work correctly.
     * <br><br>
     * <strong>Postconditions</strong> and <strong>Expected result</strong>: the method must return the index of the previous element in
     * every case.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testPreviousIndexListIterator(){
        assertEquals(-1, iter.previousIndex());
        while(iter.hasNext()) iter.next();
        assertEquals(5, iter.previousIndex());
    }

    /**
     * Test of {@link ListAdapter.ListAdapterIterator#remove()}
     * <br><br>
     * Method for checking the correct behaviour of remove() method.
     * <br><br>
     * <strong>Design</strong>: remove() can be called only after a previous() or next() call. The method
     * is immediately called. Then iter is moved forward and
     * remove() is called again(). iter is moved to the end of teamList, previous() is called and then remove() is called again.
     * At the end of the test toArray must return the expected array.
     * <br><br>
     * <strong>Preconditions</strong>: next(), hasNext(), previous() and toArray() must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: remove() is successful only if there was a call to next() or previous() before.
     * <br><br>
     * <strong>Expected result</strong>:In first case the call of remove() must throw an IllegalStateException
     * because neither next() nor previous() were called before. In the second and last case the call of remove()
     * must succeed. At the end toArray() must return the expected array.
     */
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

    /**
     * Test of {@link ListAdapter.ListAdapterIterator#set(Object)}
     * <br><br>
     * Method for checking the correct behaviour of set() method.
     * <br><br>
     * <strong>Design</strong>: like remove(), set() can be called only after a previous() or next() call.
     * The method is called immediately. Then iter is moved forward of 1 position and set() is called again.
     * <br><br>
     * <strong>Preconditions</strong>: next() and get() methods must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: set() is successful only if there was a call to next() or previous() before.
     * <br><br>
     * <strong>Expected result</strong>: in the first case must be thrown an IllegalStateException because
     * neither next() nor previous() were called before. After the call of next(), set() must be successful and the element
     * must be modified.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testSetListIterator(){
        assertThrows(IllegalStateException.class, () -> iter.set("Chelsea"));
        iter.next();
        iter.set("Chelsea");
        assertEquals("Chelsea", teamList.get(0));
    }

    /**
     * Test of {@link ListAdapter.ListAdapterIterator#add(Object)}
     * <br><br>
     * Method for checking if add() inserts the specified element in the position returned by nextIndex().
     * <br><br>
     * <strong>Design</strong>: two elements are added to the list. Then iter is moved back and another element
     * is added to teamList.
     * <br><br>
     * <strong>Preconditions</strong>: previous() and next() methods must work correctly
     * <br><br>
     * <strong>Postconditions</strong>: the elements must be added in the correct positions.
     * <br><br>
     * <strong>Expected result</strong>: the first two elements must be added in the first two position of teamList.
     * The last element must be added between the two elements previously added.
     */
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

    /**
     * Test of the general behaviour of iterators.
     * <br><br>
     * <strong>Design</strong>: the iterator is moved forward and backward in teamList. Some elements are added, removed and
     * modified.
     * The call of all the methods of listIterator must be successful.
     * <br><br>
     * <strong>Preconditions</strong>: all the methods must work correctly
     * <br><br>
     * <strong>Postconditions</strong>: the test must work
     */
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
}