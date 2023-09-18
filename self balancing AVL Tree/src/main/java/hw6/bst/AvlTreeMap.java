package hw6.bst;

import hw6.OrderedMap;
import java.util.Iterator;
import java.util.Stack;

/**
 * Map implemented as an AVL Tree.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class AvlTreeMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;

  private int size;


  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    root = insert(root, k, v);
    size++;
  }

  private Node<K, V> insert(Node<K, V> tempNode, K k, V v) {
    if (tempNode == null) {
      return new Node<>(k, v);
    }
    int cmp = k.compareTo(tempNode.key);
    if (cmp < 0) {
      tempNode.left = insert(tempNode.left, k, v);
    } else if (cmp > 0) {
      tempNode.right = insert(tempNode.right, k, v);
    } else {
      throw new IllegalArgumentException();
    }
    updateHeight(tempNode);
    return rotation(tempNode);
  }

  private Node<K, V> findForSure(K k) {
    Node<K, V> n = find(k);
    if (n == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    return n;
  }

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
  public V remove(K k) throws IllegalArgumentException {
    Node<K, V> node = findForSure(k);
    V value = node.value;
    root = remove(root, node);
    size--;
    return value;
  }

  private Node<K, V> remove(Node<K, V> subtreeRoot, Node<K, V> removeThis) {

    int cmp = subtreeRoot.key.compareTo(removeThis.key);
    if (cmp == 0) {
      return remove(removeThis);
    } else if (cmp > 0) {
      subtreeRoot.left = remove(subtreeRoot.left, removeThis);
    } else {
      subtreeRoot.right = remove(subtreeRoot.right, removeThis);
    }
    updateHeight(subtreeRoot);
    return rotation(subtreeRoot);
  }

  private Node<K, V> remove(Node<K, V> node) {
    // Easy if the node has 0 or 1 child.
    if (node.right == null) {
      return node.left;
    } else if (node.left == null) {
      return node.right;
    }

    // If it has two children, find the predecessor (max in left subtree),
    Node<K, V> toReplaceWith = max(node);
    // then copy its data to the given node (value change),
    node.key = toReplaceWith.key;
    node.value = toReplaceWith.value;
    // then remove the predecessor node (structural change).
    node.left = remove(node.left, toReplaceWith);
    //height(node);
    return rotation(node);
  }

  private Node<K, V> max(Node<K, V> node) {
    Node<K, V> curr = node.left;
    while (curr.right != null) {
      curr = curr.right;
    }
    return curr;
  }

  private int height(Node<K, V> node) {
    if (node == null) {
      return -1;
    }
    return node.height;
  }

  private int balanceFactor(Node<K, V> node) {
    if (node != null) {
      return height(node.left) - height(node.right);
    }
    return -10;
  }

  private void updateHeight(Node<K, V> node) {
    if (node == null) {
      return;
    }
    int leftHeight = (node.left == null) ? -1 : node.left.height;
    int rightHeight = (node.right == null) ? -1 : node.right.height;
    node.height = 1 + Math.max(leftHeight, rightHeight);
  }

  private Node<K, V> rotateRight(Node<K, V> node) {
    Node<K, V> leftNode = node.left;
    node.left = leftNode.right;
    leftNode.right = node;
    updateHeight(node);
    updateHeight(leftNode);
    return leftNode;
  }

  private Node<K, V> rotateLeft(Node<K, V> node) {
    Node<K, V> rightNode = node.right;
    node.right = rightNode.left;
    rightNode.left = node;
    updateHeight(node);
    updateHeight(rightNode);
    return rightNode;
  }

  private Node<K, V> rotation(Node<K, V> node) {
    int balance = balanceFactor(node);
    if (balance > 1) {
      if (balanceFactor(node.left) < 0) {
        node.left = rotateLeft(node.left);
      }
      return rotateRight(node);
    } else if (balance < -1) {
      if (balanceFactor(node.right) > 0) {
        node.right = rotateRight(node.right);
      }
      return rotateLeft(node);
    }
    return node;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    Node<K, V> n = findForSure(k);
    n.value = v;
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
   *
   * <p>Inner node class, each holds a key (which is what we sort the
   * BST by) as well as a value. We don't need a parent pointer as
   * long as we use recursive insert/remove helpers.</p>
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;

    int height;
    K key;
    V value;

    // Constructor to make node creation easier to read.
    Node(K k, V v) {
      // left and right default to null
      key = k;
      value = v;
    }

    @Override
    public String toString() {
      return key + ":" + value;
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
