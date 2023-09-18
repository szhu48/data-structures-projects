package hw6.bst;

import hw6.OrderedMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

/**
 * Map implemented as a Treap.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class TreapMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'rand'. ***/
  private static Random rand;
  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;
  private int size;

  /**
   * Make a TreapMap.
   */
  public TreapMap() {
    rand = new Random();
  }

  /**
   * Make a TreapMap that takes a seed.
   * @param seed is used to test the TreapMap
   */
  public TreapMap(int seed) {
    rand = new Random(seed);
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    root = insert(root, k, v);
    size++;
  }

  private Node<K, V> insert(Node<K, V> node, K k, V v) {
    if (node == null) {
      return new Node<>(k, v);
    }
    int cmp = k.compareTo(node.key);
    if (cmp < 0) {
      node.left = insert(node.left, k, v);
      if (node.left.priority < node.priority) {
        return rotateRight(node);
      }
    } else if (cmp > 0) {
      node.right = insert(node.right, k, v);
      if (node.right.priority < node.priority) {
        return rotateLeft(node);
      }
    } else {
      throw new IllegalArgumentException("duplicate key " + k);
    }
    return node;
  }


  private Node<K, V> rotateRight(Node<K, V> node) {
    Node<K, V> leftNode = node.left;
    node.left = leftNode.right;
    leftNode.right = node;
    return leftNode;
  }

  private Node<K, V> rotateLeft(Node<K, V> node) {
    Node<K, V> rightNode = node.right;
    node.right = rightNode.left;
    rightNode.left = node;
    return rightNode;
  }

  private Node<K, V> remove(Node<K, V> subtreeRoot, Node<K, V> toRemove) {
    int cmp = subtreeRoot.key.compareTo(toRemove.key);
    if (cmp == 0) {
      return remove(subtreeRoot);
    } else if (cmp > 0) {
      subtreeRoot.left = remove(subtreeRoot.left, toRemove);
    } else {
      subtreeRoot.right = remove(subtreeRoot.right, toRemove);
    }
    return subtreeRoot;
  }


  // Remove given node and return the remaining tree (structural change).
  private Node<K, V> remove(Node<K, V> node) {
    if (node.left == null && node.right == null) {
      node = null;
    } else if (node.left != null && node.right != null) {
      if (node.left.priority < node.right.priority) {
        node = rotateRight(node);
        node.right = remove(node.right);
      } else {
        node = rotateLeft(node);
        node.left = remove(node.left);
      }
    } else {
      if (node.left != null) {
        return node.left;
      } else {
        return node.right;
      }
    }
    return node;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    Node<K, V> node = findForSure(k);
    V value = node.value;
    root = remove(root, node);
    size--;
    return value;
  }

  private Node<K, V> findForSure(K k) {
    Node<K, V> n = find(k);
    if (n == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    return n;
  }

  // Return node for given key.
  private Node<K, V> find(K k) {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    Node<K, V> n = root;
    while (n != null) {
      int cmp = k.compareTo(n.key);
      if (cmp < 0) {
        n = n.left;
      } else if (cmp > 0) {
        n = n.right;
      } else {
        return n;
      }
    }
    return null;
  }


  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    Node<K, V> node = findForSure(k);
    node.value = v;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    Node<K, V> node = findForSure(k);
    return node.value;
  }

  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }
    return find(k) != null;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<K> iterator() {
    return new InorderIterator();
  }

  // Iterative in-order traversal over the keys
  private class InorderIterator implements Iterator<K> {
    private final Stack<Node<K, V>> stack;

    InorderIterator() {
      stack = new Stack<>();
      pushLeft(root);
    }

    private void pushLeft(Node<K, V> curr) {
      while (curr != null) {
        stack.push(curr);
        curr = curr.left;
      }
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public K next() {
      Node<K, V> top = stack.pop();
      pushLeft(top.right);
      return top.key;
    }
  }

  /*** Do not change this function's name or modify its code. ***/
  @Override
  public String toString() {
    return BinaryTreePrinter.printBinaryTree(root);
  }


  /**
   * Feel free to add whatever you want to the Node class (e.g. new fields).
   * Just avoid changing any existing names, deleting any existing variables,
   * or modifying the overriding methods.
   * Inner node class, each holds a key (which is what we sort the
   * BST by) as well as a value. We don't need a parent pointer as
   * long as we use recursive insert/remove helpers. Since this is
   * a node class for a Treap we also include a priority field.
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;
    K key;
    V value;
    int priority;

    // Constructor to make node creation easier to read.
    Node(K k, V v) {
      // left and right default to null
      key = k;
      value = v;
      priority = generateRandomInteger();
    }

    // Use this function to generate random values
    // to use as node priorities as you insert new
    // nodes into your TreapMap.
    private int generateRandomInteger() {
      // Note: do not change this function!
      return rand.nextInt();
    }

    @Override
    public String toString() {
      return key + ":" + value + ":" + priority;
    }

    @Override
    public BinaryTreeNode getLeftChild() {
      return left;
    }

    @Override
    public BinaryTreeNode getRightChild() {
      return right;
    }
  }
}
