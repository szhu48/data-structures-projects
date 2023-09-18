package hw8.spp;

import hw8.graph.Edge;
import hw8.graph.Graph;
import hw8.graph.Vertex;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraStreetSearcher extends StreetSearcher {

  /**
   * Creates a StreetSearcher object.
   *
   * @param graph an implementation of Graph ADT.
   */

  HashMap<Vertex<String>, Double> distance;
  PriorityQueue<VertexPair> pqueue;
  HashSet<Vertex<String>> explored;

  /**
   * Constructor for StreetSearcher.
   * @param graph the graph of implementation
   */
  public DijkstraStreetSearcher(Graph<String, String> graph) {
    super(graph);
    distance = new HashMap<>();
    pqueue = new PriorityQueue<>();
    explored = new HashSet<>();
  }



  private class VertexPair implements Comparable<VertexPair> {
    Vertex<String> v1;
    double dist;

    VertexPair(Vertex<String> vertex, double distance1) {
      this.v1 = vertex;
      this.dist = distance1;
    }

    @Override
    public int compareTo(VertexPair o) {
      if (this.dist < o.dist) {
        return -1;
      } else if (this.dist > o.dist) {
        return 1;
      } else {
        return 0;
      }
    }

  }

  @Override
  public void findShortestPath(String startName, String endName) {
    Vertex<String> start = vertices.get(startName);
    Vertex<String> end = vertices.get(endName);
    try {
      checkValidEndpoint(startName);
      checkValidEndpoint(endName);
    } catch (IllegalArgumentException ex) {
      System.out.println("invalid");
      return;
    }
    VertexPair start1 = new VertexPair(start, 0.0);
    distance.put(start, 0.0);
    pqueue.add(start1);
    double totalDist = -1;  // totalDist must be update below
    while (!pqueue.isEmpty()) {
      VertexPair curr = pqueue.poll();
      if (!explored.contains(curr.v1)) {
        explored.add(curr.v1);
        for (Edge<String> edge : graph.outgoing(curr.v1)) {
          if (!explored.contains(graph.to(edge))) {
            double d = distance.getOrDefault(curr.v1, MAX_DISTANCE) + (double) (graph.label(edge));
            if (d < distance.getOrDefault(graph.to(edge), MAX_DISTANCE)) {
              distance.put(graph.to(edge), d);
              graph.label(graph.to(edge), edge);
              VertexPair addPair = new VertexPair(graph.to(edge), d);
              pqueue.add(addPair);
            }
          }
        }
      }
    }
    totalDist = distance.getOrDefault(end, MAX_DISTANCE);

    // These method calls will create and print the path for you
    List<Edge<String>> path = getPath(end, start);
    if (VERBOSE) {
      printPath(path, totalDist);
    }
  }
}