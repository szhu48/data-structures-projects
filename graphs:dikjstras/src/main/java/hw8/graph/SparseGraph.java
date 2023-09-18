package hw8.graph;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

/**
 * An implementation of Graph ADT using incidence lists
 * for sparse graphs where most nodes aren't connected.
 *
 * @param <V> Vertex element type.
 * @param <E> Edge element type.
 */
public class SparseGraph<V, E> implements Graph<V, E> {

  private HashSet<Vertex<V>> graph = new HashSet<>();
  private HashSet<Edge<E>> setEdge = new HashSet<>();

  // Converts the vertex back to a VertexNode to use internally
  private VertexNode<V> convert(Vertex<V> v) throws PositionException {
    try {
      VertexNode<V> gv = (VertexNode<V>) v;
      if (gv.owner != this) {
        throw new PositionException();
      }
      return gv;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  // Converts an edge back to a EdgeNode to use internally
  private EdgeNode<E> convert(Edge<E> e) throws PositionException {
    try {
      EdgeNode<E> ge = (EdgeNode<E>) e;
      if (ge.owner != this) {
        throw new PositionException();
      }
      return ge;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  @Override
  public Vertex<V> insert(V v) throws InsertionException {
    if (v == null) {
      throw new InsertionException();
    }
    VertexNode<V> vertex = new VertexNode(v);
    vertex.owner = this;

    if (graph.contains(vertex)) {
      throw new InsertionException();
    }

    graph.add(vertex);
    return vertex;
  }

  @Override
  public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
      throws PositionException, InsertionException {

    VertexNode<V> fr = convert(from);
    VertexNode<V> too = convert(to);

    if (fr.equals(too)) {
      throw new InsertionException();
    }
    EdgeNode<E> edge1 = new EdgeNode(fr, too, e);
    edge1.owner = this;
    if (edge1.owner != fr.owner || edge1.owner != too.owner) {
      throw new PositionException();
    }
    if (setEdge.contains(edge1) || fr.outgoing.contains(edge1) || too.incoming.contains(edge1)) {
      throw new InsertionException();
    }

    setEdge.add(edge1);
    fr.outgoing.add(edge1);
    too.incoming.add(edge1);
    return edge1;
  }

  @Override
  public V remove(Vertex<V> v) throws PositionException, RemovalException {
    if (v == null) {
      throw new PositionException();
    }

    VertexNode<V> node = convert(v);

    if (!node.incoming.isEmpty() || !node.outgoing.isEmpty()) {
      throw new RemovalException();
    }
    if (!graph.contains(node)) {
      throw new PositionException();
    }

    graph.remove(node);
    node.owner = null;
    return node.data;
  }

  @Override
  public E remove(Edge<E> e) throws PositionException {
    EdgeNode<E> edge = convert(e);
    VertexNode<V> from = edge.from;
    VertexNode<V> to = edge.to;
    if (setEdge.contains(e)) {
      setEdge.remove(edge);
      from.outgoing.remove(edge.data);
      to.incoming.remove(edge.data);
      edge.owner = null;
      return edge.data;
    }
    throw new PositionException();
  }

  @Override
  public Iterable<Vertex<V>> vertices() {
    return Collections.unmodifiableCollection(graph);
  }

  @Override
  public Iterable<Edge<E>> edges() {
    return Collections.unmodifiableSet(setEdge);
  }

  @Override
  public Iterable<Edge<E>> outgoing(Vertex<V> v) throws PositionException {
    VertexNode<V> vertex = convert(v);
    return Collections.unmodifiableSet(vertex.outgoing);
  }

  @Override
  public Iterable<Edge<E>> incoming(Vertex<V> v) throws PositionException {
    VertexNode<V> vertex = convert(v);
    return Collections.unmodifiableSet(vertex.incoming);
  }

  @Override
  public Vertex<V> from(Edge<E> e) throws PositionException {
    EdgeNode<E> edge = convert(e);
    if (!setEdge.contains(e)) {
      throw new PositionException();
    }

    return edge.from;
  }

  @Override
  public Vertex<V> to(Edge<E> e) throws PositionException {
    EdgeNode<E> edge = convert(e);
    if (!setEdge.contains(e)) {
      throw new PositionException();
    }

    return edge.to;
  }

  @Override
  public void label(Vertex<V> v, Object l) throws PositionException {
    VertexNode<V> node = convert(v);
    node.label = l;
  }

  @Override
  public void label(Edge<E> e, Object l) throws PositionException {
    EdgeNode<E> edge = convert(e);
    edge.label = l;
  }

  @Override
  public Object label(Vertex<V> v) throws PositionException {
    VertexNode<V> vertex = convert(v);
    return vertex.label;
  }

  @Override
  public Object label(Edge<E> e) throws PositionException {
    EdgeNode<E> edge = convert(e);
    return edge.label;
  }

  @Override
  public void clearLabels() {
    for (Edge<E> edge: setEdge) {
      EdgeNode<E> node = convert(edge);
      node.label = null;
    }
    for (Vertex<V> vertex : graph) {
      VertexNode<V> v = convert(vertex);
      v.label = null;
    }
  }

  @Override
  public String toString() {
    GraphPrinter<V, E> gp = new GraphPrinter<>(this);
    return gp.toString();
  }

  // Class for a vertex of type V
  private final class VertexNode<V> implements Vertex<V> {
    V data;
    Graph<V, E> owner;
    Object label;
    HashSet<Edge<E>> incoming;
    HashSet<Edge<E>> outgoing;

    VertexNode(V v) {
      this.data = v;
      this.label = null;
      this.incoming = new HashSet<>();
      this.outgoing = new HashSet<>();
    }

    @Override
    public V get() {
      return this.data;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      VertexNode obj1 = (VertexNode) obj;
      if (this.data.equals(obj1.data) && this.owner.equals(obj1.owner)) {
        return true;
      }
      return false;
    }

    @Override
    public int hashCode() {
      return Objects.hash(data, owner);
    }
  }

  //Class for an edge of type E
  private final class EdgeNode<E> implements Edge<E> {
    E data;
    Graph<V, E> owner;
    VertexNode<V> from;
    VertexNode<V> to;
    Object label;

    // Constructor for a new edge
    EdgeNode(VertexNode<V> f, VertexNode<V> t, E e) {
      this.from = f;
      this.to = t;
      this.data = e;
      this.label = null;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      EdgeNode<?> edgeNode = (EdgeNode<?>) o;
      return Objects.equals(owner, edgeNode.owner)
              && Objects.equals(from, edgeNode.from) && Objects.equals(to, edgeNode.to);
    }

    @Override
    public int hashCode() {
      return Objects.hash(owner, from, to);
    }

    @Override
    public E get() {
      return this.data;
    }
  }
}
