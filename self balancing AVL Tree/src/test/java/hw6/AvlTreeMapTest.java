package hw6;

import hw6.bst.AvlTreeMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to AVL Tree.
 */
@SuppressWarnings("All")
public class AvlTreeMapTest extends BinarySearchTreeMapTest {

  @Override
  protected Map<String, String> createMap() {
    return new AvlTreeMap<>();
  }

  @Test
  public void insertLeftRotation() {
    map.insert("1", "a");
    // System.out.println(avl.toString());
    // must print
    /*
        1:a
     */

    map.insert("2", "b");
    // System.out.println(avl.toString());
    // must print
    /*
        1:a,
        null 2:b
     */

    map.insert("3", "c"); // it must do a left rotation here!
    // System.out.println(avl.toString());
    // must print
    /*
        2:b,
        1:a 3:c
     */

    String[] expected = new String[]{
            "2:b",
            "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightRotation() {
    map.insert("3", "c");
    map.insert("2", "b");
    map.insert("1", "a");

    String[] expected = new String[]{
            "2:b",
            "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertLeftRightRotation() {
    map.insert("7", "l");
    map.insert("3", "l");
    map.insert("5", "o");

    String[] expected = new String[]{
            "5:o",
            "3:l 7:l"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightLeftRotation() {
    map.insert("3", "l");
    map.insert("7", "l");
    map.insert("5", "o");

    String[] expected = new String[]{
            "5:o",
            "3:l 7:l"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertLeftRotationNotRoot() {
    map.insert("N", "14");
    map.insert("K", "11");
    map.insert("G", "7");
    map.insert("Q", "17");
    map.insert("S", "19");

    String[] expected = new String[]{
            "K:11",
            "G:7 Q:17",
            "null null N:14 S:19"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightRotationNotRoot() {

    map.insert("K", "11");
    map.insert("G", "7");
    map.insert("Q", "17");
    map.insert("F", "6");
    map.insert("E", "5");

    String[] expected = new String[]{
            "K:11",
            "F:6 Q:17",
            "E:5 G:7 null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightLeftNotationNotRoot() {
    map.insert("K", "11");
    map.insert("G", "7");
    map.insert("Q", "17");
    map.insert("O", "15");
    map.insert("L", "12");
    map.insert("J", "10");
    map.insert("I", "9");
    String[] expected = new String[]{
            "K:11",
            "I:9 O:15",
            "G:7 J:10 L:12 Q:17"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertLeftRightNotationNotRoot() {
    map.insert("K", "11");
    map.insert("O", "15");
    map.insert("I", "9");
    map.insert("G", "7");
    map.insert("J", "10");
    map.insert("M", "13");
    map.insert("N", "14");
    String[] expected = new String[]{
            "K:11",
            "I:9 N:14",
            "G:7 J:10 M:13 O:15"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightLeftNotationSubRoot(){
    //10, 5, 20, 3, 7, 15, 25, 22, 26, 23
    map.insert("J", "10");
    map.insert("E", "5");
    map.insert("T", "20");
    map.insert("C", "3");
    map.insert("G", "7");
    map.insert("O", "15");
    map.insert("Y", "25");
    map.insert("V", "22");
    map.insert("Z", "26");
    map.insert("W", "23");

    String[] expected = new String[]{
            "J:10",
            "E:5 V:22",
            "C:3 G:7 T:20 Y:25",
            "null null null null O:15 null W:23 Z:26"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

  }
  @Test
  public void insertLeftRightNotationSubRoot(){
    //10, 5, 20, 3, 7, 15, 25, 22, 26, 23
    map.insert("O", "15");
    map.insert("J", "10");
    map.insert("T", "20");
    map.insert("S", "19");
    map.insert("Y", "25");
    map.insert("K", "11");
    map.insert("F", "6");
    map.insert("I", "9");
    map.insert("G", "7");
    map.insert("H", "8");

    String[] expected = new String[]{
            "O:15",
            "I:9 T:20",
            "G:7 J:10 S:19 Y:25",
            "F:6 H:8 null K:11 null null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

  }

  @Test
  public void removeLeafNodeForRightRotation(){
    map.insert("O", "15");
    map.insert("J", "10");
    map.insert("T", "20");
    map.insert("S", "19");
    map.insert("Y", "25");
    map.insert("K", "11");
    map.insert("F", "6");
    map.insert("I", "9");
    map.insert("D", "4");
    map.insert("C", "3");
    map.insert("E", "5");
    map.remove("E");
    map.remove("I");

    String[] expected = new String[]{
            "O:15",
            "F:6 T:20",
            "D:4 J:10 S:19 Y:25",
            "C:3 null null K:11 null null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

  }

  @Test
  public void removeLeafNodeForLeftRotation(){
    map.insert("E", "5");
    map.insert("D", "4");
    map.insert("J", "10");
    map.insert("L", "12");
    map.insert("G", "7");
    map.remove("D");

    String[] expected = new String[]{
            "J:10",
            "E:5 L:12",
            "null G:7 null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

  }

  @Test
  public void removeLeafNodesForRightLeftRotation(){
    map.insert("D", "4");
    map.insert("G", "7");
    map.insert("B", "2");
    map.insert("E", "5");
    map.remove("B");

    String[] expected = new String[]{
            "E:5",
            "D:4 G:7"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

  }

  @Test
  public void removeSubTreeForRightLeftRotation(){
    map.insert("D", "4");
    map.insert("G", "7");
    map.insert("B", "2");
    map.insert("A", "1");
    map.insert("C", "3");
    map.insert("E", "5");
    map.remove("A");
    map.remove("C");
    map.remove("B");

    String[] expected = new String[]{
            "E:5",
            "D:4 G:7"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

  }

  @Test
  public void removeLeafNodesForLeftRightRotation() {
    map.insert("O", "15");
    map.insert("J", "10");
    map.insert("T", "20");
    map.insert("S", "19");
    map.insert("U", "21");
    map.insert("H", "7");
    map.insert("K", "11");
    map.insert("I", "8");
    map.remove("K");
    map.remove("S");
    map.remove("U");
    String[] expected = new String[]{
            "O:15",
            "I:8 T:20",
            "H:7 J:10 null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void putValue() {
    map.insert("A", "1");
    map.put("A", "2");
    assertEquals("2", map.get("A"));

  }

  @Test
  public void putValueForTwoDifferentKeys() {
    map.insert("A", "1");
    map.insert("B", "2");
    map.put("A", "10");
    map.put("B", "20");
    assertEquals("20", map.get("B"));
    assertEquals("10", map.get("A"));
  }

  @Test
  public void testHasMethod() {
    map.insert("B", "2");
    map.insert("A", "1");
    map.insert("C", "3");
    assertEquals(false, map.has("G"));
  }

  @Test
  public void testCorrectSizeForInsert() {
    map.insert("B", "2");
    map.insert("A", "1");
    map.insert("C", "3");
    assertEquals(3, map.size());
  }

  @Test
  public void testCorrectSizeAfterRemove() {
    map.insert("B", "2");
    map.insert("A", "1");
    map.insert("C", "3");
    map.insert("D", "4");
    map.remove("D");
    assertEquals(3, map.size());
  }

  @Test
  public void removeForOneNode() {
    map.insert("B", "2");
    map.remove("B");
    assertEquals(0, map.size());
  }

  @Test
  public void removeTargetLeafNode() {
    map.insert("3", "c");
    map.insert("2", "b");
    map.insert("4", "d");
    map.remove("4");

    String[] expected = new String[]{
            "3:c",
            "2:b null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeOnEmptyTree() {
    try {
      map.remove("3");
      fail("exception not thrown");
    }
    catch (IllegalArgumentException ex) {
      return;
    }
  }

  @Test
  public void insertRemoveInsertAgain() {
    map.insert("O", "15");
    map.insert("J", "10");
    map.insert("H", "7");
    map.insert("K", "11");
    map.insert("I", "8");
    map.remove("K");
    map.insert("L", "12");
    String[] expected = new String[]{
            "J:10",
            "H:7 O:15",
            "null I:8 L:12 null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

}
