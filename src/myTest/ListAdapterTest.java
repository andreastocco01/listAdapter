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
 * The parameters of the methods called inside the test cases are valid unless it is specified in the test case description.
 * <br><br>
 * A test case is correct if all the tests that verify the correct functioning give a positive result.
 * <br><br>
 * To execute each test case of this class, simply run TestRunner class.
 * <br><br>
 * Execution variable:
 * <br>
 * HList teamList - main listAdapter on which all the methods are tested.
 * <br>
 * HList emptyList - empty listAdapter which is used to test teamList. In the majority of the tests, the emptyList is filled with test values.
 * <br>
 * HListIterator iter - listIterator attached to teamList.
 * <br>
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
     * <strong>Summary</strong>: method for initializing the execution variables:
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
     * <strong>Summary</strong>: method for checking the correct behaviour of the public constructors of ListAdapter.
     * <br><br>
     * <strong>Design</strong>: check if the two constructors create an empty list and a non-empty list.
     * <br><br>
     * <strong>Description</strong>: a new list is instantiated by the void constructor. Another list is instantiated
     * by the parameterized constructor.
     * <br><br>
     * <strong>Preconditions</strong>: size() must work in the right way.
     * <br><br>
     * <strong>Postconditions</strong>: void constructor must create an empty list.
     * Parameterized constructor must create a list with the same elements of the specified collection.
     * <br><br>
     * <strong>Expected result</strong>: in the first case the void constructor
     * must create an empty list. In the last case the parameterized constructor must create a list which contains all the
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
     * <strong>Summary</strong>: method for checking the correct size of the list.
     * <br><br>
     * <strong>Design</strong>: use of add() and remove() methods for checking the correct behaviour of
     * size() method.
     * <br><br>
     * <strong>Description</strong>: checking the size of teamList before and after adding or removing an element.
     * emptyList doesn't have changes.
     * <br><br>
     * <strong>Preconditions</strong>: add() and remove() methods must work in the right way.
     * <br><br>
     * <strong>Postconditions</strong>: size() must return the correct number of elements inside the list.
     * <br><br>
     * <strong>Expected result</strong>: the returned value of size()
     * method must correspond to the number of elements inside the list. emptyList is empty, so its size must be 0.
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
     * <strong>Summary</strong>: method for checking if the list is empty.
     * <br><br>
     * <strong>Design</strong>: check if the method works correctly by emptying the list and then adding
     * an element to the list.
     * <br><br>
     * <strong>Description</strong>: teamList is emptied and isEmpty() is invoked. An element is added and isEmpty is invoked again.
     * emptyList doesn't have changes.
     * <br><br>
     * <strong>Preconditions</strong>: clear() and add() methods must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: isEmpty() must return true if the collection is empty, false otherwise.
     * <br><br>
     * <strong>Expected result</strong>: true after the removal of all the elements, false after the insertion of an element.
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
     * <strong>Summary</strong>: method for checking if the list contains a specific element.
     * <br><br>
     * <strong>Design</strong>: check the method by adding an element to the list and verifying if the list
     * contains that element and doesn't contain an element which wasn't added.
     * <br><br>
     * <strong>Description</strong>: an element is added to teamList. Check if that element is present inside the list. Check if an element
     * that wasn't added isn't inside the list.
     * <br><br>
     * <strong>Preconditions</strong>: add() method must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: contains() must return true if the parameter is inside the list, false otherwise.
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
     * <strong>Summary</strong>: method for checking the conversion from ListAdapter to Object[].
     * <br><br>
     * <strong>Design</strong>: conversion of the list to Object[] before and after adding elements.
     * <br><br>
     * <strong>Description</strong>: teamList is converted to array. Then two elements are added and the list is converted again.
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
     * <strong>Summary</strong>: method for checking the correct copying process of the elements of the list into a specified array.
     * toArray(target) must return an array with the same size of the list if target size is smaller or equal than list size.
     * If target size is greater than list's size, toArray(target) must return an array with null elements at the end.
     * <br><br>
     * <strong>Design</strong>: initially target has the same size of the list then has a different one.
     * <br><br>
     * <strong>Description</strong>: at the beginning, toArray(target) is executed with a target with the same size of teamList.
     * Then it is executed with a target with
     * a different size. After that, this method is executed with a null parameter.
     * <br><br>
     * <strong>Preconditions</strong>: size() must work correctly.
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
        // the list must be copied inside the array
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"},
                teamList.toArray(target));
        target = new Object[teamList.size() + 2];
        // the list must be copied inside the array in addiction of NULLs for filling the remaining space
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax", null, null},
                teamList.toArray(target));
        target = new Object[0];
        // the list must be copied in another array with the same size
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"},
                teamList.toArray(target));
        assertThrows(NullPointerException.class, () -> teamList.toArray(null));
    }

    /**
     * Test of {@link ListAdapter#add(Object)}
     * <br><br>
     * <strong>Summary</strong>: method for checking the correct addition of the elements to the list.
     * <br><br>
     * <strong>Design</strong>: check the method by removing the last two elements added
     * <br><br>
     * <strong>Description</strong>: two elements are added at the end of teamList. Check if the last element is correct,
     * then the last element is removed
     * and is checked again if the last element is correct.
     * <br><br>
     * <strong>Preconditions</strong>: remove() and size() methods must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: the specified element must be appended at the end of the list.
     * <br><br>
     * <strong>Expected result</strong>: before removal, the last element of teamList must be the second element added.
     * After removal, the last element of teamList must be the first element added.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testAdd(){
        teamList.add("Barcelona");
        teamList.add("Chelsea");
        assertEquals("Chelsea", teamList.get(teamList.size() - 1));
        teamList.remove(teamList.size() - 1);
        assertEquals("Barcelona", teamList.get(teamList.size() - 1));
    }

    /**
     * Test of {@link ListAdapter#remove(Object)}
     * <br><br>
     * <strong>Summary</strong>: method for checking the correct removal of an element (specified by his value).
     * <br><br>
     * <strong>Design</strong>: when an element is removed, subsequent elements must be moved back.
     * <br><br>
     * <strong>Description</strong>: A null element is added then removed. An element is removed, then is checked the correct
     * position of the previous and next element.
     * Another element is removed, then is checked if the entire list matches the expected result.
     * <br><br>
     * <strong>Preconditions</strong>: get() and toArray() methods must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: remove() must remove the first occurrence of the specified element from the list.
     * <br><br>
     * <strong>Expected result</strong>: the removal of an element inside the list must not change the order of the remaining ones.
     * At the end, toArray() must return the expected array.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testRemoveObject(){
        teamList.add(null);
        teamList.remove(null);
        teamList.remove("Manchester United");
        // the following elements must be shifted back
        assertEquals("Bayern Monaco", teamList.get(3));
        assertEquals("Real Madrid", teamList.get(2));
        teamList.remove("Bayern Monaco");
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Ajax"}, teamList.toArray());

    }

    /**
     * Test of {@link ListAdapter#containsAll(HCollection)}
     * <br><br>
     * <strong>Summary</strong>: method for checking if the list contains an entire collection, which is passed by parameter.
     * <br><br>
     * <strong>Design</strong>: check the method with a collection that contains some elements of the list, then an element is changed.
     * <br><br>
     * <strong>Description</strong>: check if teamList contains an empty collection. add to the empty collection some elements
     * which are contained in teamList
     * and check again if teamList contains this new collection. An element in emptyList is changed, then containsAll() is called again.
     * Then containsAll() is called with an invalid parameter.
     * <br><br>
     * <strong>Preconditions</strong>: add() and set() methods must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: containsAll() must return true if and only if the list contains all the element
     * of the parameter collection.
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
        // an element is changed with an element which isn't inside teamList
        emptyList.set(0, "Inter");
        assertFalse(teamList.containsAll(emptyList));
        assertThrows(NullPointerException.class, () -> teamList.containsAll(null));
    }

    /**
     * Test of {@link ListAdapter#addAll(HCollection)}
     * <br><br>
     * <strong>Summary</strong>: method for checking if all the elements of a specified collection are appended to the list.
     * <br><br>
     * <strong>Design</strong>: check the method by comparing the list with the expected array after the appending of a collection.
     * <br><br>
     * <strong>Description</strong>: some elements are added to emptyList. teamList must append all the emptyList's elements.
     * addAll() must
     * throw an exception
     * if the parameter is null.
     * <br><br>
     * <strong>Preconditions</strong>: add() and toArray() must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: emptyList's elements must be added to teamList.
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
     * <strong>Summary</strong>: method for checking if the list inserts all the elements of a specified collection in a specific position.
     * <br><br>
     * <strong>Design</strong>: check the method by comparing the list with the expected array. A collection is inserted in the middle
     * of the list and at the end of the list.
     * <br><br>
     * <strong>Description</strong>: some elements are added to emptyList, then emptyList is inserted at position 1 in teamList.
     * emptyList is changed, and it is inserted again at position size() -1. After that, the collection is added in a position which is
     * out of bounds. A null collection is added in teamList.
     * <br><br>
     * <strong>Preconditions</strong>: add(), remove() and toArray() must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: collection's elements must be inserted in the list without modify the order or the elements
     * that were already there.
     * <br><br>
     * <strong>Expected result</strong>: all the elements of emptyList are added in the correct
     * position of teamList. An exception is
     * thrown in the last two cases.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testAddAllWithIndex(){
        emptyList.add("Chelsea");
        emptyList.add("Benfica");
        // the list is inserted at the second index
        teamList.addAll(1, emptyList);
        assertArrayEquals(new Object[]{"Milan", "Chelsea", "Benfica", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"},
                teamList.toArray());
        emptyList.remove("Chelsea");
        emptyList.remove("Benfica");
        emptyList.add("Porto");
        // the list is inserted at the last index
        teamList.addAll(teamList.size() - 1, emptyList);
        assertArrayEquals(new Object[]{"Milan", "Chelsea", "Benfica", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Porto", "Ajax"},
                teamList.toArray());
        assertThrows(IndexOutOfBoundsException.class, () -> teamList.addAll(teamList.size() + 1, emptyList));
        assertThrows(NullPointerException.class, () -> teamList.addAll(0, null));
    }

    /**
     * Test of {@link ListAdapter#removeAll(HCollection)}
     * <br><br>
     * <strong>Summary</strong>: method for checking the removal of all the element inside a specified collection.
     * <br><br>
     * <strong>Design</strong>: check the method by removing a collection that isn't inside the list and a collection
     * contained in the list.
     * <br><br>
     * <strong>Description</strong>: removeAll() is called with an invalid parameter. Some elements which aren't inside teamList are added to emptyList. Then removeAll() is called.
     * emptyList is modified with elements which are inside teamList and removeAll() is called again.
     * <br><br>
     * <strong>Preconditions</strong>: add() and toArray() must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: the emptyList's elements must be removed from the list.
     * <br><br>
     * <strong>Expected result</strong>: in the first case, an exception must be thrown. teamList must be the same after the
     * first call of removeAll(). In the second case the elements inside emptyList must
     * be removed.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testRemoveAll(){
        assertThrows(NullPointerException.class, () -> teamList.removeAll(null));
        emptyList.add("Chelsea");
        emptyList.add("Benfica");
        // nothing changes
        teamList.removeAll(emptyList);
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}, teamList.toArray());
        emptyList.add("Liverpool");
        emptyList.add("Manchester United");
        // some elements are removed
        teamList.removeAll(emptyList);
        assertArrayEquals(new Object[]{"Milan", "Real Madrid", "Bayern Monaco", "Ajax"}, teamList.toArray());
    }

    /**
     * Test of {@link ListAdapter#retainAll(HCollection)}
     * <br><br>
     * <strong>Summary</strong>: method for testing the removal of every element that isn't contained in the specified collection.
     * <br><br>
     * <strong>Design</strong>: check the method by using a collection that contains some elements of the list.
     * <br><br>
     * <strong>Description</strong>: retainAll() is called with an invalid parameter. Some elements are added to emptyList
     * then retainAll() is called on teamList.
     * <br><br>
     * <strong>Preconditions</strong>: add() and toArray() must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: the method must erase all the elements that aren't inside the specified collection.
     * <br><br>
     * <strong>Expected result</strong>: in the first case, the method must throw an exception. In the second case,
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
     * <strong>Summary</strong>: method for checking the emptying of the list.
     * <br><br>
     * <strong>Design</strong>: check the method by comparing the emptied list with an empty array.
     * <br><br>
     * <strong>Description</strong>: teamList is emptied.
     * <br><br>
     * <strong>Preconditions</strong>: toArray() must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: the list must be empty.
     * <br><br>
     * <strong>Expected result</strong>: toArray() must return an empty array.
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
     * <strong>Summary</strong>: method for checking if two instances of ListAdapter contain the same elements.
     * <br><br>
     * <strong>Design</strong>: check the method with an identical list and with a different one.
     * <br><br>
     * <strong>Description</strong>: a new list is created by copying all the elements of teamList. Then an element is removed from that list.
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
     * <strong>Summary</strong>: method for testing if different instances of ListAdapter have the same hashCode.
     * <br><br>
     * <strong>Design</strong>: check the method with a list with the same elements (but in different order) and an identical
     * list.
     * <br><br>
     * <strong>Description</strong>: newList is created with the elements of teamList. Every element of teamList is added to emptyList
     * (two elements are swapped). newList is emptied. emptyList is emptied then is filled with all the elements of teamList.
     * <br><br>
     * <strong>Preconditions</strong>: constructor(parameter), add(), clear() and addAll() must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: two identical lists must have the same hashCode (same elements in the same order).
     * Two different lists may have the same hashCode.
     * <br><br>
     * <strong>Expected result</strong>: hashCode must be the same in first and last case, different otherwise.
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
        // two elements are swapped so the lists aren't the same
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
     * <strong>Summary</strong>: method for checking if get() method returns the correct element inside the list.
     * <br><br>
     * <strong>Design</strong>: check the method by verifying the element returned in different positions.
     * <br><br>
     * <strong>Description</strong>: two elements inside teamList are inspected. Then an index which isn't inside teamList is inspected.
     * An element is added to teamList, then another two elements are inspected.
     * <br><br>
     * <strong>Preconditions</strong>: add() and size() methods must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: the element returned by get(i) must be the ith element.
     * <br><br>
     * <strong>Expected result</strong>: the first and last elements are inspected. An index out of bounds is inspected
     * but an exception is thrown.
     * Then an element is inserted and all subsequent elements are shifted.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testGet(){
        assertEquals("Milan", teamList.get(0));
        assertEquals("Ajax", teamList.get(teamList.size() - 1));
        assertThrows(IndexOutOfBoundsException.class, () -> teamList.get(teamList.size()));
        // all the following elements are shifted
        teamList.add(1, "Chelsea");
        assertEquals("Chelsea", teamList.get(1));
        assertEquals("Liverpool", teamList.get(2));
    }

    /**
     * Test of {@link ListAdapter#set(int, Object)}
     * <br><br>
     * <strong>Summary</strong>: method for checking if set() modifies the correct element of the list.
     * <br><br>
     * <strong>Design</strong>: check the method by verifying if the replacement of an element is successful.
     * <br><br>
     * <strong>Description</strong>: is changed the value of an element inside the list, then is checked if the modification of that element was
     * successful. Then set() is called on an element outside the list.
     * <br><br>
     * <strong>Preconditions</strong>: get() and size() must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: method set(i, value) must modify ith element if it is allowed.
     * <br><br>
     * <strong>Expected result</strong>: the first call of set() must be successful. the last call must throw an IndexOutOfBoundsException.
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
     * <strong>Summary</strong>: method for testing if the addition of an element in every position of the list works correctly.
     * <br><br>
     * <strong>Design</strong>: check the method by comparing the list with the expected array after insertions.
     * <br><br>
     * <strong>Description</strong>: an element is inserted inside teamList.
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
        // an element append to the list
        teamList.add(teamList.size(), "Chelsea");
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Porto", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax", "Chelsea"},
                teamList.toArray());
    }

    /**
     * Test of {@link ListAdapter#remove(int)}
     * <br><br>
     * <strong>Summary</strong>: method for checking if an element is removed from the list (specified by his index).
     * <br><br>
     * <strong>Design</strong>: when an element is removed, subsequent elements must be moved back.
     * <br><br>
     * <strong>Description</strong>: an element is removed from teamList, then the element in that position must be the element following
     * the one removed.
     * remove() method is called in a position which isn't inside teamList.
     * <br><br>
     * <strong>Preconditions</strong>: get(), size() and toArray must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: after removal all the successive elements of the removed one must be shifted.
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
     * <strong>Summary</strong>: method for checking if is returned the index of the first occurrence of a specified element.
     * <br><br>
     * <strong>Design</strong>: check the method by comparing the returned index of an element with the expected result.
     * <br><br>
     * <strong>Description</strong>: first is requested the index of an element inside teamList. Then an element is modified and is requested the index
     * of the first occurrence of that element. After that is requested the index of an element which isn't inside teamList.
     * <br><br>
     * <strong>Preconditions</strong>: set() method must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: indexOf() must return the first occurrence of the specified element.
     * <br><br>
     * <strong>Expected result</strong>: in the first two cases the method must return the correct index.
     * In the last case it must return an invalid index (the element isn't inside the list).
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
     * <strong>Summary</strong>: method for checking if is returned the index of the last occurrence of a specified element.
     * <br><br>
     * <strong>Design</strong>: check the method by comparing the returned last index of an element with the expected result.
     * <br><br>
     * <strong>Description</strong>: two elements already present inside teamList are added to the list. In the first and second cases
     * is required the index of the last occurrence of a specified element. In the last case is requested the index of
     * an element which isn't inside the list.
     * <br><br>
     * <strong>Preconditions</strong>: add() method must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: lastIndexOf() must return the last occurrence of the specified element.
     * <br><br>
     * <strong>Expected result</strong>: in the first two cases it must return the index of the two elements that
     * are added. In the last case it must return an invalid index (the element isn't inside the list).
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
     * <strong>Summary</strong>: method for checking the correct update of the father list if there is a structural change in the subList.
     * <br><br>
     * <strong>Design</strong>: different subLists are created and an element is inserted into the last one.
     * <br><br>
     * <strong>Description</strong>: is created a subList from teamList (exclusive first and last element). Then is created a subList from the
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
        // sub1 = {"Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco"}
        HList sub1 = teamList.subList(1, teamList.size() - 1);
        assertArrayEquals(new Object[]{"Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco"}, sub1.toArray());
        // sub2 = {"Real Madrid", "Manchester United"}
        HList sub2 = sub1.subList(1, sub1.size() - 1);
        assertArrayEquals(new Object[]{"Real Madrid", "Manchester United"}, sub2.toArray());
        // sub2 = {"Real Madrid", "Chelsea", "Manchester United"}
        sub2.add(1, "Chelsea");
        assertArrayEquals(new Object[]{"Real Madrid", "Chelsea", "Manchester United"}, sub2.toArray());
        assertArrayEquals(new Object[]{"Liverpool", "Real Madrid", "Chelsea", "Manchester United", "Bayern Monaco"}, sub1.toArray());
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Chelsea", "Manchester United", "Bayern Monaco", "Ajax"},
                teamList.toArray());
    }

    /**
     * Test of {@link ListAdapter.ListAdapterIterator#ListAdapterIterator()} and {@link ListAdapter.ListAdapterIterator#ListAdapterIterator(int)}
     * <br><br>
     * <strong>Summary</strong>: method for checking the correct behaviour of the public constructors of ListAdapterIterator.
     * <br><br>
     * <strong>Design</strong>: check if the two constructors create the iterator in the correct position by calling next().
     * <br><br>
     * <strong>Description</strong>: a new iterator is instantiated by the void constructor. Another iterator
     * is instantiated by the parameterized constructor.
     * <br><br>
     * <strong>Preconditions</strong>: next() must work in the right way.
     * <br><br>
     * <strong>Postconditions</strong>: the void constructor must create an iterator pointing 0 index.
     * The parameterized constructor must create an iterator pointing the specified index.
     * <br><br>
     * <strong>Expected result</strong>: in the first case the void constructor
     * must create an iterator pointing the first element of teamList. In the last case the parameterized
     * constructor must create an iterator pointing to the fourth element of the list.
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
     * <strong>Summary</strong>: method for checking if hasNext() returns true if there is an element after the current element.
     * <br><br>
     * <strong>Design</strong>: the iterator is moved on the list and the method is called.
     * <br><br>
     * <strong>Description</strong>: an iterator is created in front of the list and hasNext() in called. Then the iterator is
     * moved forward and hasNext() is called again. When the iterator came to the end of teamList, hasNext() is called again.
     * <br><br>
     * <strong>Preconditions</strong>: next() must work correctly.
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
        for(int i = 0; i < 4; i++){
            iter.next();
        }
        assertTrue(iter.hasNext());
        iter.next();
        assertFalse(iter.hasNext());
    }

    /**
     * Test of {@link ListAdapter.ListAdapterIterator#next()}
     * <br><br>
     * <strong>Summary</strong>: method for checking if next() method returns the correct element of the list.
     * <br><br>
     * <strong>Design</strong>: the expected element is compared with the effective element returned by this method.
     * The iterator is moved on the list.
     * <br><br>
     * <strong>Description</strong>: the iterator is moved forward and backward in teamList and next() method is called
     * many times.
     * <br><br>
     * <strong>Preconditions</strong>: previous() and nextIndex() methods must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: next() must always return the following element.
     * <br><br>
     * <strong>Expected result</strong>: the method must return always a valid index.
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
     * <strong>Summary</strong>: method for checking if hasPrevious() returns true if there is an element before the current element.
     * <br><br>
     * <strong>Design</strong>: the iterator is moved on the list and the method is called.
     * <br><br>
     * <strong>Description</strong>: a listIterator is created in front of the list and hasPrevious() in called. Then the iterator is
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
     * <strong>Summary</strong>: method for checking if previous() method returns the correct element of the list.
     * <br><br>
     * <strong>Design</strong>: the expected element is compared with the effective element returned by this method.
     * <br><br>
     * <strong>Description</strong>: iter is moved forward and backward, the method is called three times.
     * <br><br>
     * <strong>Preconditions</strong>: next() method must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: previous() must return always the correct value.
     * <br><br>
     * <strong>Expected result</strong>: in the first and the last
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
     * <strong>Summary</strong>: method for checking if nextIndex() method returns the index of the element
     * that will be returned with a next() call.
     * <br><br>
     * <strong>Design</strong>: check if the index returned by the method is the same of the index of the element returned by next().
     * <br><br>
     * <strong>Description</strong>: iter is in front of teamList, nextIndex() is called. Then iter is moved forward to the end of the list.
     * nextIndex() is called again.
     * <br><br>
     * <strong>Preconditions</strong>: next() and size() methods must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: nextIndex() must return the index of the next element.
     * <br><br>
     * <strong>Expected result</strong>: in the first case the method must return the first index
     * of teamList (the iterator is in front of the list).
     * In the second case it must return an invalid index.
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
     * <strong>Summary</strong>: method for checking if previousIndex() method returns the index of the element
     * that will be returned with a previous() call.
     * <br><br>
     * <strong>Design</strong>: check if the index returned by the method is the same of the index of the element returned by previous().
     * <br><br>
     * <strong>Description</strong>: iter is in front of teamList, previousIndex() is called. Then iter is moved forward
     * to the end of the list. The method is called again.
     * <br><br>
     * <strong>Preconditions</strong>: next() method must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: the method must return the index of the previous element.
     * <br><br>
     * <strong>Expected result</strong>: in the first case the method must return an invalid index (the iterator is
     * in front of the list). In the last case the method must return the last index of the list.
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
     * <strong>Summary</strong>: method for checking the correct behaviour of remove() method.
     * <br><br>
     * <strong>Design</strong>: check if the method removes the previous element visited by comparing the list with the
     * expected array after all the removals.
     * <br><br>
     * <strong>Description</strong>: remove() can be called only after a previous() or next() call. The method
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
        // teamList = {"Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
        assertEquals("Liverpool", iter.next());
        while(iter.hasNext())iter.next();
        iter.previous();
        // teamList = {"Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco"}
        iter.remove();
        assertEquals("Bayern Monaco", iter.previous());
        assertArrayEquals(new Object[]{"Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco"}, teamList.toArray());
    }

    /**
     * Test of {@link ListAdapter.ListAdapterIterator#set(Object)}
     * <br><br>
     * <strong>Summary</strong>: method for checking the correct behaviour of set() method.
     * <br><br>
     * <strong>Design</strong>: check if the method replaces the last element visited.
     * <br><br>
     * <strong>Description</strong>: like remove(), set() can be called only after a previous() or next() call.
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
     * <strong>Summary</strong>: method for checking if add() inserts the specified element in the position returned by nextIndex().
     * <br><br>
     * <strong>Design</strong>: check if the method adds the elements in the correct position by comparing the final result
     * with the expected array.
     * <br><br>
     * <strong>Description</strong>: two elements are added to the list. Then iter is moved back and another element
     * is added to teamList.
     * <br><br>
     * <strong>Preconditions</strong>: previous() and next() methods must work correctly.
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
        // teamList = {"Chelsea", "Porto", "Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
        assertEquals("Porto", iter.next());
        iter.previous();
        iter.add("Barcelona");
        // teamList = {"Chelsea", "Barcelona", "Porto", "Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
        assertEquals("Barcelona", iter.previous());
        iter.next();
        assertEquals("Porto", iter.next());
    }

    /**
     * <strong>Summary</strong>: test of the general behaviour of iterators.
     * <br><br>
     * <strong>Design</strong>: the iterator is moved forward and backward in teamList. Some elements are added, removed and
     * modified.
     * <br><br>
     * <strong>Description</strong>: some elements are added with the iterator. An element is changed, another one is removed.
     * An exception is expected when set() and get() are called with invalid arguments.
     * Another element is added then the iterator is moved backward and forward before removing another element.
     * <br><br>
     * <strong>Preconditions</strong>: all the methods must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: the list iterator must allow you to navigate
     * back and forth along the entire list sequentially and to be able to modify, remove and add data.
     * <br><br>
     * <strong>Expected result</strong>: the list must contain the same elements of the expected array.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testListIterator(){
        iter.add("Barcelona");
        iter.add("Benfica");
        // teamList = {"Barcelona", "Benfica", "Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
        assertThrows(IllegalStateException.class, () -> iter.set("Chelsea"));
        iter.previous();
        iter.set("Chelsea");
        // teamList = {"Barcelona", "Chelsea", "Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
        assertEquals("Chelsea", teamList.get(iter.nextIndex()));
        iter.remove();
        // teamList = {"Barcelona", "Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
        assertEquals("Barcelona", teamList.get(iter.previousIndex()));
        iter.add("Porto");
        // teamList = {"Barcelona", "Porto", "Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
        iter.previous();
        iter.previous();
        iter.next();
        iter.remove();
        // teamList = {"Porto", "Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
        assertArrayEquals(new Object[]{"Porto", "Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"},
                teamList.toArray());
        assertThrows(IndexOutOfBoundsException.class, () -> teamList.get(iter.previousIndex()));
        assertEquals("Porto", teamList.get(iter.nextIndex()));
    }

    /**
     * <strong>Summary</strong>: test of the general behaviour of lists.
     * <br><br>
     * <strong>Design</strong>: some elements are added, removed and modified inside the lists.
     * subLists are created for doing some things more easily. There is also a case of same hashCode but
     * different lists.
     * <br><br>
     * <strong>Description</strong>: an element is added to both lists. Check if teamList contains emptyList.
     * Add all the elements of teamList to emptyList. Remove some elements of emptyList by using a subList. Check
     * if the list is as expected. Then are removed all the elements (except the first one) from the lists.
     * The lists are modified, so they are different but with the same hashCode.
     * <br><br>
     * <strong>Preconditions</strong>: all the methods must work correctly.
     * <br><br>
     * <strong>Postconditions</strong>: the lists must allow you to do almost everything with the elements.
     * <br><br>
     * <strong>Expected result</strong>: the list contains exactly the elements contained by
     * the manually created Object arrays with the expected values.
     */
    // teamList = {"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
    @Test
    public void testList(){
        teamList.add("Barcelona");
        emptyList.add("Ajax");
        assertTrue(teamList.containsAll(emptyList));
        emptyList.addAll(teamList);
        // emptyList = {"Ajax", "Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco", "Ajax"}
        assertEquals(0, emptyList.indexOf("Ajax"));
        assertEquals(emptyList.size() - 2, emptyList.lastIndexOf("Ajax"));
        emptyList.removeAll(new ListAdapter(emptyList.subList(0, 1))); // removed all "Ajax"
        assertArrayEquals(new Object[]{"Milan", "Liverpool", "Real Madrid", "Manchester United", "Bayern Monaco",
         "Barcelona"}, emptyList.toArray());
        assertNotEquals(emptyList, teamList);
        teamList.subList(1, teamList.size()).clear(); // removed all the elements after the first one
        emptyList.subList(1, emptyList.size()).clear(); // removed all the elements after the first one
        assertEquals(emptyList, teamList);
        assertEquals(1, teamList.size());
        teamList.add(null);
        teamList.set(0, 1);
        // teamList = {1, null}
        emptyList.add(0, null);
        emptyList.set(1, 31);
        // emptyList = {null, 31}
        assertEquals(emptyList.hashCode(), teamList.hashCode());
        assertNotEquals(emptyList, teamList);
    }
}