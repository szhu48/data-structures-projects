package hw7.hashing;

import hw7.Map;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class ChainingHashMap<K, V> implements Map<K, V> {

  private LinkedList<Node<K, V>>[] map;
  private int size;
  private int primeIndex;
  private int currCapacity;
  private final int[] primes = {3, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853, 25717, 51437, 102877, 205759,
    411527, 823117, 1646237, 3292489, 6584983, 13169977};

  /** Constructor for HashMap.
   *
   */
  public ChainingHashMap() {
    this.size = 0;
    this.primeIndex = 0;
    this.currCapacity = primes[primeIndex];
    map = new LinkedList[currCapacity];
  }

  private int hashFunction(K key) throws IllegalArgumentException {
    if (key == null) {
      throw new IllegalArgumentException("key is null");
    }
    return Math.abs(((key.hashCode() & 0x7fffffff) % currCapacity));
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("key is null");
    }
    if (!has(k)) {
      insert(k, v, hashFunction(k));
    } else {
      throw new IllegalArgumentException("duplicate key");
    }
  }

  private void insert(K k, V v, int hashCode) {
    Node<K, V> head = new Node<>(k, v, hashCode);

    if (map[hashCode] == null) {
      LinkedList<Node<K, V>> list = new LinkedList<>();
      list.add(head);
      map[hashCode] = list;
      size++;
    } else {
      map[hashCode].add(head);
      size++;
    }

    if ((float) size / currCapacity >= 0.75) {
      if (primeIndex < 22) {
        primeIndex++;
        currCapacity = primes[primeIndex];
      }
      if (primeIndex >= 22) {
        currCapacity *= 2;
      }
      LinkedList<Node<K, V>>[] temp = map;
      size = 0;
      map = new LinkedList[currCapacity];
      for (LinkedList<Node<K, V>> map : temp) {
        if (map != null) {
          for (Node<K, V> node : map) {
            insert(node.key, node.value);
          }
        }
      }
    }
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    Node<K, V> node = find(hashFunction(k), k);
    if (node == null) {
      throw new IllegalArgumentException("node doesn't exist");
    }
    return remove(node);

  }

  private V remove(Node<K, V> node) {
    int index = hashFunction(node.key);
    V value = node.value;
    map[index].remove(node);
    if (map[index].size() == 0) {
      map[index] = null;
    }
    size--;
    return value;
  }

  @Override
  public void put(K k, V v) {
    if (k == null || !has(k)) {
      throw new IllegalArgumentException();
    }
    int index = hashFunction(k);
    Node<K, V> node = find(index, k);
    node.value = v;
  }


  @Override
  public V get(K k) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException();
    }
    Node<K, V> node = find(hashFunction(k), k);
    if (node == null) {
      throw new IllegalArgumentException();
    }
    return node.value;
  }


  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }
    int index = hashFunction(k);
    return (find(index, k) != null);
  }

  private Node<K, V> find(int hashCode, K k) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException();
    }

    LinkedList<Node<K, V>> list = map[hashCode];
    if (list == null) {
      return null;
    }
    for (Node<K, V> node : list) {
      if (node.key.equals(k)) {
        return node;
      }
    }
    return null;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<K> iterator() {
    return new ChainingHashMapIterator();
  }

  private class ChainingHashMapIterator implements Iterator {
    int index; //tracks which list is at
    int ctr; //tracks the index of the node
    private int numElements; //numberElements so far

    ChainingHashMapIterator() {
      this.index = 0;
      this.ctr = 0;
      this.numElements = 0;
    }

    @Override
    public boolean hasNext() {
      return numElements < ChainingHashMap.this.size;
    }

    @Override
    public K next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      if (map[index] != null && (ctr < map[index].size())) {
        numElements++;
        return map[index].get(ctr++).key;
      } else {
        index++;
        ctr = 0;
        while (map[index] == null) {
          index++;
        }
        numElements++;
        return map[index].get(ctr++).key;
      }
    }
  }

  private class Node<K, V> {

    K key;
    V value;
    int hashCode;

    Node(K k, V v, int hashCode) {
      this.key = k;
      this.value = v;
      this.hashCode = hashCode;
    }
  }

}
