package hw8;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;
import hw8.graph.Edge;
import hw8.graph.Graph;
import hw8.graph.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public abstract class GraphTest {

  protected Graph<String, String> graph;

  @BeforeEach
  public void setupGraph() {
    this.graph = createGraph();
  }

  protected abstract Graph<String, String> createGraph();

  @Test
  @DisplayName("insert(v) returns a vertex with given data")
  public void canGetVertexAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    assertEquals(v1.get(), "v1");
  }

  @Test
  @DisplayName("insert(U, V, e) returns an edge with given data")
  public void canGetEdgeAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    assertEquals(e1.get(), "v1-v2");
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void insertEdgeThrowsPositionExceptionWhenFirstVertexIsNull() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.insert(null, v, "e");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(U, null, e) throws exception")
  public void insertEdgeThrowsPositionExceptionWhenSecondVertexIsNull() {
    try{
      Vertex<String> v = graph.insert("v1");
      graph.insert(v, null, "e");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert duplicate vertex is not allowed")
  public void insertDuplicateVertexException() {
    try{
      graph.insert("v1");
      graph.insert("v1");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert same edge not allowed")
  public void insertDuplicateEdgeException() {
    try{
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      graph.insert(v1, v2, "v1-v2");
      graph.insert(v1, v2, "v1-v2");
      fail("The expected exception was not thrown");
  } catch (InsertionException ex) {
    return;
    }
  }

  @Test
  @DisplayName("insert duplicate edge after remove")
  public void insertDuplicateEdgeExceptionAfterRemove() {
    try{
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Vertex<String> v3 = graph.insert("v3");
      graph.insert(v1, v2, "v1-v2");
      Edge<String> edge1 = graph.insert(v1, v3, "v1-v3");
      graph.remove(edge1);
      graph.insert(v1, v2, "v1-v2");
      fail("exception not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert edge to another graph")
  public void insertEdgeToAnotherGraph() {
    try{
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Graph<String, String> newGraph = createGraph();
      newGraph.insert(v1, v2, "v1-v2");
      fail("positionexception not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert edge after removal of vertex1")
  public void insertEdgeAfterRemovalOfVertex1() {
    try{
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      graph.remove(v1);
      graph.insert(v1, v2, "v1-v2");
      fail("positionexception not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert edge after removal of vertex2")
  public void insertEdgeAfterRemovalOfVertex2() {
    try{
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      graph.remove(v2);
      graph.insert(v1, v2, "v1-v2");
      fail("position exception not thrown");
    } catch (PositionException ex) {
      return;
    }
  }
  @Test
  @DisplayName("insert duplicate edge after different insert in between")
  public void insertDuplicateEdgeAfterDifferentInsert() {
    try{
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      graph.insert(v1, v2, "v1-v2");
      graph.insert(v2, v1, "v2-v1");
      graph.insert(v1, v2, "v1-v2");
      fail("insertionexception not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert creates self-loop exception")
  public void insertSelfLoopException() {
    try{
      Vertex<String> v1 = graph.insert("v1");
      Edge<String> e1 = graph.insert(v1, v1, "v1");
      fail("The expected exception was not throw");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("remove vertex throws removalException")
  public void removeVertexThrowsRemovalException() {
    try{
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
      graph.remove(v1);
      fail("removal exception not thrown");
    } catch (RemovalException ex) {
      return;
    }
  }

  @Test
  @DisplayName("remove vertex throws PositionException")
  public void removeVertexThrowsPositionException() {
    try{
      graph.remove((Vertex<String>) null);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("remove vertex from another graph")
  public void removeVertexFromAnotherGraphThrowsPositionException() {
    try{
      Graph<String, String> newGraph = createGraph();
      Vertex<String> v1 = newGraph.insert("v1");
      graph.remove(v1);
      fail("position exception not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("remove vertex twice")
  public void removeVertexTwice() {
    try{
      Vertex<String> v1 = graph.insert("v1");
      graph.remove(v1);
      graph.remove(v1);
      fail("position exception not caught");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("remove invalid edge")
    public void removeInvalidEdgeThrowsPositionException() {
      try{
        Vertex<String> v1 = graph.insert("v1");
        Vertex<String> v2 = graph.insert("v2");
        graph.remove((Edge<String>) null);
        fail("position exception not caught");
      } catch (PositionException ex) {
        return;
      }
    }

  @Test
  @DisplayName("remove edge twice throws exception")
  public void removeEdgeTwiceThrowsPositionException() {
    try{
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
      graph.remove(e1);
      graph.remove(e1);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }


  @Test
  @DisplayName("from throws PositionException")
  public void fromThrowsPositionException() {
    try{
      graph.from((Edge<String>) null);
      fail("The excepted exception was not throw");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("from returns correct label")
  public void fromReturnsCorrectLabel() {
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
      assertEquals(v1, graph.from((Edge<String>) e1));
  }

  @Test
  @DisplayName("to throws PositionException")
  public void toThrowsPositionException() {
    try{
      graph.to((Edge<String>) null);
      fail("The excepted exception was not throw");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("to returns correct label")
  public void toReturnsCorrectLabel() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    assertEquals(v2, graph.to((Edge<String>) e1));
  }

  @Test
  @DisplayName("label edge throws PositionException")
  public void labelEdgeThrowsPositionException() {
    try {
      graph.label((Edge<String>) null);
      fail("The excepted exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("label edge asserts correct label")
  public void labelEdgeAssertsCorrectLabel() {

      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
      graph.label(e1, "v1-v2");
      assertEquals("v1-v2", graph.label(e1));
  }


  @Test
  @DisplayName("label vertex throws PositionException")
  public void labelVertexThrowsPositionException() {
    try{
      graph.label((Vertex<String>) null);
      fail("The excepted was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("label vertex returns correct label")
  public void labelVertexReturnsCorrectLabel() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    graph.label(v1, "v1");
    graph.label(v2, "v2");
    assertEquals("v1", graph.label(v1));
    assertEquals("v2", graph.label(v2));
  }

  @Test
  @DisplayName("label vertex returns correct label after labelling twice")
  public void labelVertexReturnsCorrectLabelAfterLabellingTwice() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    graph.label(v1, "v1");
    graph.label(v2, "v2");
    graph.label(v2, "v3");
    graph.label(v1, "v4");
    assertEquals("v4", graph.label(v1));
    assertEquals("v3", graph.label(v2));
  }

  @Test
  @DisplayName("label vertex returns correct label after labelling twice")
  public void labelEdgesReturnsCorrectLabelAfterLabellingTwice() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Vertex<String> v4 = graph.insert("v4");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> e2 = graph.insert(v2, v1, "v2-v1");
    Edge<String> e3 = graph.insert(v3, v4, "v3-v4");
    Edge<String> e4 = graph.insert(v4, v1, "v4-v1");
    graph.label(e1, "e1");
    graph.label(e2, "e2");
    graph.label(e3, "e3");
    graph.label(e4, "e4");
    graph.label(e1, "eA1");
    graph.label(e2, "eA2");
    graph.label(e3, "eA3");
    graph.label(e4, "eA4");
    assertEquals("eA1", graph.label(e1));
    assertEquals("eA2", graph.label(e2));
    assertEquals("eA3", graph.label(e3));
    assertEquals("eA4", graph.label(e4));
  }

  @Test
  @DisplayName("test Iterator for vertex")
  public void testVertexIterator(){
    graph.insert("v1");
    graph.insert("v2");
    graph.insert("v3");
    graph.insert("v4");
    HashSet<String> set = new HashSet<>();
    int count = 0;
    for (Vertex<String> v: graph.vertices()) {
      count++;
      set.add(v.get());
    }
    assertEquals(4, count);
    assertTrue(set.contains("v1"));
    assertTrue(set.contains("v2"));
    assertTrue(set.contains("v3"));
    assertTrue(set.contains("v4"));
  }

  @Test
  @DisplayName("test Iterator for vertex after remove")
  public void testVertexIteratorAfterRemove() {
    graph.insert("v1");
    graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    graph.remove(v3);
    HashSet<String> set = new HashSet<>();
    int count = 0;
    for (Vertex<String> v: graph.vertices()) {
      count++;
      set.add(v.get());
    }
    assertEquals(2, count);
    assertTrue(set.contains("v1"));
    assertTrue(set.contains("v2"));
    assertFalse(set.contains("v3"));
  }

  @Test
  @DisplayName("test Iterator for vertex after remove and then insert")
  public void testVertexIteratorRemoveAndThenInsert() {
    graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    graph.insert("v3");
    graph.insert("v4");
    graph.remove(v2);
    HashSet<String> set = new HashSet<>();
    int count = 0;
    for (Vertex<String> v: graph.vertices()) {
      count++;
      set.add(v.get());
    }
    assertEquals(3, count);
    assertTrue(set.contains("v1"));
    assertFalse(set.contains("v2"));
    assertTrue(set.contains("v3"));
    assertTrue(set.contains("v4"));
  }


  @Test
  @DisplayName("test Iterator for edge")
  public void testEdgeIterator(){
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Vertex<String> v4 = graph.insert("v4");
    Edge<String> edge1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> edge2 = graph.insert(v2, v1, "v2-v1");
    Edge<String> edge3 = graph.insert(v3, v4, "v3-v4");
    HashSet<Edge> set = new HashSet<>();
    int count = 0;
    for (Edge<String> edge: graph.edges()) {
      count++;
      set.add(edge);
    }
    assertEquals(3, count);
    assertTrue(set.contains(edge1));
    assertTrue(set.contains(edge2));
    assertTrue(set.contains(edge3));
  }

  @Test
  @DisplayName("test Iterator for edge after remove")
  public void testEdgeIteratorAfterRemove() {
    Vertex<String> v1= graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> edge1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> edge2 = graph.insert(v2, v1, "v2-v1");
    graph.remove(edge2);
    HashSet<Edge> set = new HashSet<>();
    int count = 0;
    for (Edge<String> edge: graph.edges()) {
      count++;
      set.add(edge);
    }
    assertEquals(1, count);
    assertTrue(set.contains(edge1));
    assertFalse(set.contains(edge2));
  }

  @Test
  @DisplayName("test Iterator for edge after remove and then insert")
  public void testEdgeIteratorRemoveAndThenInsert() {
    Vertex<String> v1= graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> edge1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> edge2 = graph.insert(v2, v1, "v2-v1");
    graph.remove(edge2);
    Edge<String> edge3 = graph.insert(v3, v2, "v2-v3");
    HashSet<Edge> set = new HashSet<>();
    int count = 0;
    for (Edge<String> edge: graph.edges()) {
      count++;
      set.add(edge);
    }
    assertEquals(2, count);
    assertTrue(set.contains(edge1));
    assertFalse(set.contains(edge2));
    assertTrue(set.contains(edge3));
  }

  @Test
  @DisplayName("test Iterator for incoming edges")
  public void testIncomingIterator() {
    Vertex<String> v1= graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    graph.insert(v2, v1, "edge1");
    graph.insert(v3, v1, "edge1");
    int count = 0;
    for (Edge<String> edge : graph.incoming(v1)) {
      assertEquals(edge.get(), "edge1");
      count++;
    }
    assertEquals(2, count);

  }

  @Test
  @DisplayName("test Iterator for outgoing edges")
  public void testOutgoingIterator() {
    Vertex<String> v1= graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    graph.insert(v1, v2, "e");
    graph.insert(v1, v3, "e");
    int count = 0;
    for (Edge<String> edge : graph.outgoing(v1)) {
      count++;
      assertEquals(edge.get(), "e");

    }
    assertEquals(2, count);
  }

  @Test
  @DisplayName("test for Iterator outgoing edges throws PositionException")
  public void testOutgoingIteratorThrowsPositionException() {
    try {
      for (Edge<String> edge : graph.outgoing(null)) {
        assertEquals(edge.get(), "e");
      }
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("test for Iterator incoming edges throws PositionException")
  public void testIncomingIteratorThrowsPositionException() {
    try {
      for (Edge<String> edge : graph.incoming(null)) {
        assertEquals(edge.get(), "e");
      }
    } catch (PositionException ex) {
      return;
    }
  }


  @Test
  @DisplayName("test from returns correct Vertex")
  public void testFromReturnsCorrectVertex() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e");
    assertEquals(v1, graph.from(e1));
  }

  @Test
  @DisplayName("test to returns correct Vertex")
  public void testToReturnsCorrectVertex() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e");
    assertEquals(v2, graph.to(e1));
  }

  @Test
  @DisplayName("test from throws PositionException")
  public void testFromThrowsPositionException() {
    try{
      Vertex<String> v1 = graph.insert("v1");
      Edge<String> e1 = graph.insert(null, v1, "e");
      graph.from(e1);
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("test to throws PositionException")
  public void testToThrowsPositionException() {
    try{
      Vertex<String> v1 = graph.insert("v1");
      Edge<String> e1 = graph.insert(v1, null, "e");
      graph.to(e1);
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("label throws PositionException for Vertex")
  public void labelThrowsPositionExceptionForVertex() {
    try{
      Vertex<String> v1 = null;
      graph.label(v1, "v1");
    } catch (PositionException ex) {
      return;
    }
  }
  @Test
  @DisplayName("label throws PositionException for Edge")
  public void labelThrowsPositionExceptionForEdge() {
    try{
      Edge<String> e1 = null;
      graph.label(e1, "e1");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("label assert label for vertex correctly")
  public void labelAssertsLabelForVertexCorrectly() {
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      graph.label(v1, "v1");
      graph.label(v2, "v2");
      assertEquals("v1", graph.label(v1));
      assertEquals("v2", graph.label(v2));
  }

  @Test
  @DisplayName("label assert label for Edge correctly")
  public void labelAssertsLabelForEdgeCorrectly() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> e2 = graph.insert(v2, v1, "v2-v1");
    graph.label(e1, "e1");
    graph.label(e2, "e2");
    assertEquals("e1", graph.label(e1));
    assertEquals("e2", graph.label(e2));
  }
  @Test
  @DisplayName("label assert label for Edge and Vertices correctly")
  public void labelAssertsLabelForEdgeAndVertexCorrectly() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Vertex<String> v4 = graph.insert("v4");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> e2 = graph.insert(v2, v1, "v2-v1");
    Edge<String> e3 = graph.insert(v3, v4, "v3-v4");
    Edge<String> e4 = graph.insert(v4, v3, "v4-v3");
    graph.label(e1, "e1");
    graph.label(e2, "e2");
    graph.label(e3, "e3");
    graph.label(e4, "e4");
    assertEquals("e1", graph.label(e1));
    assertEquals("e2", graph.label(e2));
    assertEquals("e3", graph.label(e3));
    assertEquals("e4", graph.label(e4));
    graph.label(v1, "v1");
    graph.label(v2, "v2");
    graph.label(v3, "v3");
    graph.label(v4, "v4");
    assertEquals("v1", graph.label(v1));
    assertEquals("v2", graph.label(v2));
    assertEquals("v3", graph.label(v3));
    assertEquals("v4", graph.label(v4));
  }

  @Test
  @DisplayName("clear label for Vertices correctly")
  public void ClearLabelForVertexCorrectly() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Vertex<String> v4 = graph.insert("v4");
    graph.label(v1, "v1");
    graph.label(v2, "v2");
    graph.label(v3, "v3");
    graph.label(v4, "v4");
    graph.clearLabels();
    assertEquals(null, graph.label(v1));
    assertEquals(null, graph.label(v2));
    assertEquals(null, graph.label(v3));
    assertEquals(null, graph.label(v4));
  }

  @Test
  @DisplayName("clear label for Edge correctly")
  public void ClearLabelForEdgeCorrectly() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Vertex<String> v4 = graph.insert("v4");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> e2 = graph.insert(v2, v1, "v2-v1");
    Edge<String> e3 = graph.insert(v3, v4, "v3-v4");
    Edge<String> e4 = graph.insert(v4, v3, "v4-v3");
    graph.label(e1, "e1");
    graph.label(e2, "e2");
    graph.label(e3, "e3");
    graph.label(e4, "e4");
    graph.clearLabels();
    assertEquals(null, graph.label(e1));
    assertEquals(null, graph.label(e2));
    assertEquals(null, graph.label(e3));
    assertEquals(null, graph.label(e4));
  }

  @Test
  @DisplayName("clear label for Edge and Vertices correctly")
  public void ClearLabelForEdgeAndVertexCorrectly() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Vertex<String> v4 = graph.insert("v4");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> e2 = graph.insert(v2, v1, "v2-v1");
    Edge<String> e3 = graph.insert(v3, v4, "v3-v4");
    Edge<String> e4 = graph.insert(v4, v3, "v4-v3");
    graph.label(e1, "e1");
    graph.label(e2, "e2");
    graph.label(e3, "e3");
    graph.label(e4, "e4");
    graph.label(v1, "v1");
    graph.label(v2, "v2");
    graph.label(v3, "v3");
    graph.label(v4, "v4");
    graph.clearLabels();
      assertEquals(null, graph.label(v1));
      assertEquals(null, graph.label(v2));
      assertEquals(null, graph.label(v3));
      assertEquals(null, graph.label(v4));
      assertEquals(null, graph.label(e1));
      assertEquals(null, graph.label(e2));
      assertEquals(null, graph.label(e3));
      assertEquals(null, graph.label(e4));
  }


}
