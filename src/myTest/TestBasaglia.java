package myTest;

import myAdapter.*;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class TestBasaglia {
  private HList listPlanets;
  private HList listEmpty;
  private String[] somePlanets = {"Mercury", "Venus", "Earth", "Mars"};
  @Before
  public void setUp() {
    listPlanets = new ListAdapter();
    listEmpty = new ListAdapter();
    for(String p: somePlanets) {
      listPlanets.add(p);
    }
  }

  @Test
  public void size() {
    assertEquals(0, listEmpty.size());
    assertEquals(4, listPlanets.size());
    listPlanets.add("Jupiter");
    assertEquals(5, listPlanets.size());
    listPlanets.remove(0);
    listPlanets.remove(0);
    assertEquals(3, listPlanets.size());
  }

  @Test
  public void isEmpty() {
    assertTrue(listEmpty.isEmpty());
    while(!listPlanets.isEmpty()) {
      listPlanets.remove(0);
    }
    assertTrue(listPlanets.isEmpty());
  }

  @Test
  public void contains() {
    assertTrue(listPlanets.contains("Earth"));
    assertFalse(listPlanets.contains("Death Star"));
    listEmpty.add(null);
    assertTrue(listEmpty.contains(null));
  }

  @Test
  public void testToArray() {
    assertArrayEquals(somePlanets, listPlanets.toArray());

    // Bigger array:
    Object[] bigger = new String[6];
    Object[] biggerReturn = listPlanets.toArray(bigger);
    /*
     *   I want them to be the same array in memory
     * */
    assertTrue(biggerReturn == bigger);
    assertArrayEquals(new String[]{"Mercury", "Venus", "Earth", "Mars", null, null}, biggerReturn);


    // Smaller array:

    Object[] smaller = new String[2];
    Object[] smallerReturn = listPlanets.toArray(smaller);
    /*
    *   I expect a new array to be created (and returned)
    *   with size corresponding to the list's dimension
    * */
    assertFalse(smallerReturn == smaller);
    assertEquals(4, smallerReturn.length);
    assertArrayEquals(somePlanets, smallerReturn);


    assertThrows(NullPointerException.class, () -> listPlanets.toArray(null));
  }

  @Test
  public void add() {
    listPlanets.add(0, "StrangePlanet");
    assertArrayEquals(new String[]{"StrangePlanet", "Mercury", "Venus", "Earth", "Mars"}, listPlanets.toArray());
    listPlanets.add(2,"Pippo");
    assertArrayEquals(new String[]{"StrangePlanet", "Mercury", "Pippo", "Venus", "Earth", "Mars"}, listPlanets.toArray());
    listPlanets.add("Jupiter");
    assertArrayEquals(new String[]{"StrangePlanet", "Mercury", "Pippo", "Venus", "Earth", "Mars", "Jupiter"}, listPlanets.toArray());
  }

  @Test
  public void remove() {
    listPlanets.remove("Earth");
    assertArrayEquals(new String[]{"Mercury", "Venus", "Mars"}, listPlanets.toArray());
    listPlanets.remove(1);
    assertArrayEquals(new String[]{"Mercury", "Mars"}, listPlanets.toArray());
    listPlanets.add(null);
    assertArrayEquals(new String[]{"Mercury", "Mars", null}, listPlanets.toArray());
    listPlanets.add("Mars");
    listPlanets.remove("Mars");
    assertArrayEquals(new String[]{"Mercury", null, "Mars"}, listPlanets.toArray());
  }

  @Test
  public void containsAll() {
    HCollection neighbourPlanets = new ListAdapter();
    neighbourPlanets.add("Venus");
    neighbourPlanets.add("Mars");
    assertTrue(listPlanets.containsAll(neighbourPlanets));
    neighbourPlanets.add("Pluto");
    assertFalse(listPlanets.containsAll(neighbourPlanets));
    assertThrows(NullPointerException.class, () -> neighbourPlanets.containsAll(null));
    listPlanets.add(null);
    listPlanets.add(0, null);
    HCollection nullElement = new ListAdapter();
    nullElement.add(null);
    assertTrue(listPlanets.containsAll(nullElement));
  }

  @Test
  public void addAll() {
    HCollection otherPlanes = new ListAdapter();
    otherPlanes.add("Jupiter");
    otherPlanes.add("Saturn");
    listPlanets.addAll(otherPlanes);
    assertArrayEquals(new String[]{"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn"}, listPlanets.toArray());
    HCollection otherList = new ListAdapter();
    otherList.add("Pippo");
    otherList.add("Pluto");
    listPlanets.addAll(1, otherList);
    assertArrayEquals(new String[]{"Mercury", "Pippo", "Pluto", "Venus", "Earth", "Mars", "Jupiter", "Saturn"}, listPlanets.toArray());
    assertThrows(NullPointerException.class, () -> listPlanets.addAll(null));
    assertThrows(IndexOutOfBoundsException.class, () -> listPlanets.addAll(70, otherList));
  }

  @Test
  public void removeAll() {
    HCollection planetsToRemove = new ListAdapter();
    planetsToRemove.add("Pippo");
    assertFalse(listPlanets.removeAll(planetsToRemove));
    planetsToRemove.add("Earth");
    assertTrue(listPlanets.removeAll(planetsToRemove));
    assertArrayEquals(new String[]{"Mercury", "Venus", "Mars"}, listPlanets.toArray());
    listPlanets.add("Mercury");
    HCollection mercuryList = new ListAdapter();
    mercuryList.add("Mercury");
    listPlanets.removeAll(mercuryList);
    assertArrayEquals(new String[]{"Venus", "Mars"}, listPlanets.toArray());
    assertThrows(NullPointerException.class, () -> listPlanets.removeAll(null));
  }

  @Test
  public void retainAll() {
    HCollection otherPlanets = new ListAdapter();
    otherPlanets.add("Mercury");
    otherPlanets.add("Mars");
    otherPlanets.add("Franco");
    assertTrue(listPlanets.retainAll(otherPlanets));
    assertArrayEquals(new String[]{"Mercury", "Mars"}, listPlanets.toArray());
    assertFalse(listPlanets.retainAll(otherPlanets));
    assertThrows(NullPointerException.class, () -> listPlanets.retainAll(null));
    listPlanets.add("Mercury");
    otherPlanets.remove("Mars");
    listPlanets.retainAll(otherPlanets);
    assertArrayEquals(new String[]{"Mercury", "Mercury"}, listPlanets.toArray());
  }

  @Test
  public void clear() {
    listPlanets.clear();
    assertTrue(listPlanets.isEmpty());
  }

  @Test
  public void testHashCode() {
    // Todo
  }

  @Test
  public void get() {
    assertEquals(listPlanets.get(1), "Venus");
    assertEquals(listPlanets.get(3), "Mars");
    assertThrows(IndexOutOfBoundsException.class, () -> listPlanets.get(-1));
  }

  @Test
  public void set() {
    assertEquals("Earth", listPlanets.set(2, "Terra"));
    assertArrayEquals(new String[] {"Mercury", "Venus", "Terra", "Mars"}, listPlanets.toArray());
    assertThrows(IndexOutOfBoundsException.class, () -> listPlanets.set(7, "Saturno"));
  }

  @Test
  public void indexOfLastIndexOf() {
    listPlanets.add("Mercury");
    assertEquals(0, listPlanets.indexOf("Mercury"));
    assertEquals(4, listPlanets.lastIndexOf("Mercury"));
    assertEquals(-1, listPlanets.indexOf("Death Star"));
    assertEquals(-1, listPlanets.lastIndexOf("Death Star"));
  }

  @Test
  public void testListIterator() {
    HListIterator it = listPlanets.listIterator();
    assertEquals("Mercury", it.next());
    assertEquals("Venus", it.next());
    assertThrows(IndexOutOfBoundsException.class, () -> listPlanets.listIterator(-1));
    it = listPlanets.listIterator(listPlanets.size());
    it.add("Jupiter");
    assertThrows(IndexOutOfBoundsException.class, () -> listPlanets.listIterator(listPlanets.size()+1));
  }

  @Test
  public void subList() {
    HList hot = listPlanets.subList(0,2);
    assertArrayEquals(new String[] {"Mercury", "Venus"}, hot.toArray());
    hot.set(0, "Mercurio");
    assertArrayEquals(new String[] {"Mercurio", "Venus", "Earth", "Mars"}, listPlanets.toArray());
    hot.add(0,"HottestPlanet");
    assertArrayEquals(new String[] {"HottestPlanet", "Mercurio", "Venus", "Earth", "Mars"}, listPlanets.toArray());
    HList sublist = listPlanets.subList(3,5);
    sublist.clear();
    assertArrayEquals(new String[] {"HottestPlanet", "Mercurio", "Venus"}, listPlanets.toArray());
    assertEquals(0, sublist.size());
    assertThrows(IndexOutOfBoundsException.class, () -> listPlanets.subList(-1, 0));
  }

  @Test
  public void testEquals() {
    assertEquals(listPlanets, listPlanets);
    HList otherListPlanets = new ListAdapter();
    for(String planet : somePlanets) {
      otherListPlanets.add(planet);
    }
    assertEquals(listPlanets, otherListPlanets);
    otherListPlanets.add("Jupiter");
    assertNotEquals(listPlanets, otherListPlanets);
  }
}