package hw6;

import hw6.bst.TreapMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to Treap.
 */
@SuppressWarnings("All")
public class TreapMapTest extends BinarySearchTreeMapTest {

  @Override
  protected Map<String, String> createMap() {
    return new TreapMap<>();
  }

  @BeforeEach
  protected void createNewMap() {
    map = new TreapMap<>(10);
  }

  @Test
  public void insertRightRotation() {
    map.insert("3", "c"); //-1157793070
    map.insert("2", "b"); //1913984760
    map.insert("1", "a"); //1107254586

    String[] expected = new String[]{
          "3:c:-1157793070",
            "1:a:1107254586 null",
            "null 2:b:1913984760 null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertLeftRotation() {
    map.insert("1", "a"); //-1157793070
    map.insert("2", "b"); //1913984760
    map.insert("3", "c"); //1107254586

    String[] expected = new String[]{
            "1:a:-1157793070",
                    "null 3:c:1107254586",
                    "null null 2:b:1913984760 null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightLeftRotation() {
    //-1157793070 1913984760 1107254586 1773446580 254270492 -1408064384 1048475594
    // -140 -115 104 110 177 191 254

    map.insert("J", "10"); //-1157793070
    map.insert("O", "15"); //1913984760
    map.insert("M", "13"); //1107254586
    map.insert("Q", "17"); //1773446580


    String[] expected = new String[]{
          "J:10:-1157793070",
            "null M:13:1107254586",
            "null null null Q:17:1773446580",
            "null null null null null null O:15:1913984760 null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertLeftRightRotation() {
    //-1157793070 1913984760 1107254586 1773446580 254270492 -1408064384 1048475594
    // -115 191 110 177 254 -140 104
    // -140 -115 104 110 177 191 254

    map.insert("J", "10"); //-1157793070
    map.insert("L", "12"); //1913984760
    map.insert("E", "5"); //1107254586
    map.insert("C", "3"); //1773446580
    map.insert("M", "13"); //254270492
    map.insert("D", "4"); //-1408064384

    String[] expected = new String[]{
            "D:4:-1408064384",
            "C:3:1773446580 J:10:-1157793070",
            "null null E:5:1107254586 M:13:254270492",
            "null null null null null null L:12:1913984760 null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

  }
  @Test
  public void removeLeafNodeNoRotation() {
    //-1157793070 1913984760 1107254586 1773446580 254270492 -1408064384 1048475594
    // -115 191 110 177 254 -140 104
    // -140 -115 104 110 177 191 254

    map.insert("J", "10"); //-1157793070
    map.insert("L", "12"); //1913984760
    map.insert("F", "6"); //1107254586
    map.insert("D", "4"); //1773446580
    map.insert("B", "2"); //254270492
    map.remove("B");

    String[] expected = new String[]{
            "J:10:-1157793070",
            "F:6:1107254586 L:12:1913984760",
            "D:4:1773446580 null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

  }

  @Test
  public void removeParentNodeNoRotation() {
    //-1157793070 1913984760 1107254586 1773446580 254270492 -1408064384 1048475594
    // -115 254 191 110 177 -140 104
    // -140 -115 104 110 177 191 254

    map.insert("J", "10"); //-1157793070
    map.insert("L", "12"); //1913984760
    map.insert("F", "6"); //1107254586
    map.insert("D", "4"); //1773446580
    map.insert("B", "2"); //254270492
    map.remove("B");

    String[] expected = new String[]{
            "J:10:-1157793070",
            "F:6:1107254586 L:12:1913984760",
            "D:4:1773446580 null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

  }
  @Test
  @DisplayName("In this case, you remove Root")
  public void removeRightRotation() {
    //-1157793070 1913984760 1107254586 1773446580 254270492 -1408064384 1048475594
    // -115 254 191 110 177 -140 104
    // -140 -115 104 110 177 191 254

    map.insert("J", "10"); //-1157793070
    map.insert("L", "12"); //1913984760
    map.insert("F", "6"); //1107254586
    map.insert("D", "4"); //1773446580
    map.insert("B", "2"); //254270492
    map.remove("J");

    String[] expected = new String[]{
            "B:2:254270492",
            "null F:6:1107254586",
            "null null D:4:1773446580 L:12:1913984760"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeLeftRotation() {
    //-1157793070 1913984760 1107254586 1773446580 254270492 -1408064384 1048475594
    // -115 254 191 110 177 -140 104
    // -140 -115 104 110 177 191 254

    map.insert("E", "5"); //-1157793070
    map.insert("G", "7"); //1913984760
    map.insert("D", "4"); //1107254586
    map.insert("I", "9"); //1773446580
    map.insert("H", "8"); //254270492
    map.remove("H");

    String[] expected = new String[]{
            "E:5:-1157793070",
            "D:4:1107254586 I:9:1773446580",
            "null null G:7:1913984760 null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeLeftRightRotation() {
    map.insert("E", "5"); //-1157793070
    map.insert("G", "7"); //1913984760
    map.insert("D", "4"); //1107254586
    map.insert("I", "9"); //1773446580
    map.insert("H", "8"); //254270492
    map.remove("H");
    map.remove("E");

    String[] expected = new String[]{
            "D:4:1107254586",
            "null I:9:1773446580",
            "null null G:7:1913984760 null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeRightLeftRotation() {
    map.insert("F", "6"); //-1157793070
    map.insert("E", "5"); //1913984760
    map.insert("H", "8"); //1107254586
    map.insert("C", "3"); //1773446580
    map.insert("D", "4"); //254270492
    map.remove("D");
    map.remove("F");

    String[] expected = new String[]{
            "H:8:1107254586",
            "C:3:1773446580 null",
            "null E:5:1913984760 null null"
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
  public void testCorrectSizeAfterRemove() {
    map.insert("B", "2");
    map.insert("A", "1");
    map.insert("C", "3");
    map.insert("D", "4");
    map.remove("D");
    assertEquals(3, map.size());
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
  public void insertRemoveInsertAgain() {
    map.insert("F", "6"); //-1157793070
    map.insert("E", "5"); //1913984760
    map.insert("H", "8"); //1107254586
    map.insert("C", "3"); //1773446580
    map.remove("E");
    map.insert("D", "4"); //254270492

    String[] expected = new String[]{
            "F:6:-1157793070",
            "D:4:254270492 H:8:1107254586",
            "C:3:1773446580 null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

  }

}