package hw7.hashing;

import hw7.Map;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OpenAddressingHashMap<K, V> implements Map<K, V> {
  Node<K, V>[] map;
  int currCapacity;
  int[] primes = {3, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421,
    12853, 25717, 51437, 102877, 205759, 411527, 823117,
    1646237, 3292489, 6584983, 13169977};
  int primeIndex;
  int numTombstones;
  int numElementsWithTombStones;

  /** Constructor for HashMap.
   *
   */
  public OpenAddressingHashMap() {
    this.numTombstones = 0;
    this.numElementsWithTombStones = 0;
    this.primeIndex = 0;
    this.currCapacity = primes[primeIndex];
    map = new Node[currCapacity];
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("key is null");
    }
    if (!has(k)) {
      Node<K, V> node = new Node<>(k, v);
      insert(node);
      numElementsWithTombStones++;

      if ((float) numElementsWithTombStones / currCapacity >= 0.75) {
        if (primeIndex < 22) {
          primeIndex++;
          currCapacity = primes[primeIndex];
        }
        if (primeIndex >= 22) {
          currCapacity *= 2;
        }
        Node<K, V>[] temp = map;
        map = new Node[currCapacity];
        numElementsWithTombStones -= numTombstones;
        numTombstones = 0;
        for (Node<K, V> n: temp) {
          if (n != null) {
            insert(n);
          }
        }
      }
    } else {
      throw new IllegalArgumentException("duplicate key");
    }
  }

  private void insert(Node<K, V> n) {
    int indexToInsertAt = find(n.key);
    map[indexToInsertAt] = n;
  }


  private int hashFunction(K key) throws IllegalArgumentException {
    if (key == null) {
      throw new IllegalArgumentException("key is null");
    }
    return Math.abs(((key.hashCode() & 0x7fffffff) % currCapacity));
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    int indexOfNodeToRemove = find(k);
    Node<K, V> node = map[indexOfNodeToRemove];
    if (node == null) {
      throw new IllegalArgumentException("node doesn't exist");
    }
    V returnValue = node.value;
    remove(node, indexOfNodeToRemove);
    numTombstones++;
    return returnValue;
  }

  private V remove(Node<K, V> node, int indexOfNodeToRemove) {
    node.ts = true;
    return null;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    if (k == null || !has(k)) {
      throw new IllegalArgumentException();
    }
    int index = find(k);
    map[index].value = v;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException();
    }
    int index = find(k);
    if (map[index] == null) {
      throw new IllegalArgumentException();
    }
    return map[index].value;
  }

  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }
    return map[find(k)] != null;
  }

  private int find(K k) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException();
    }

    int index = hashFunction(k);

    while (map[index] != null) {
      if (map[index].ts || !map[index].key.equals(k)) {
        index++;
        index = index % currCapacity;
      } else {
        return index;
      }
    }
    return index;
  }


  @Override
  public int size() {
    return numElementsWithTombStones - numTombstones;
  }

  @Override
  public Iterator<K> iterator() {
    return new OpenAddressingIterator();
  }

  private class OpenAddressingIterator implements Iterator {

    int index;
    int ctr;

    OpenAddressingIterator() {
      this.index = 0;
      this.ctr = 0;
    }

    @Override
    public boolean hasNext() {
      return ctr < OpenAddressingHashMap.this.size();
    }

    @Override
    public K next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      while (map[index] == null || map[index].ts) {
        index++;
      }
      ctr++;
      return map[index++].key;
    }

  }

  private class Node<K, V> {
    K key;
    V value;
    boolean ts;

    public Node(K k, V v) {
      this.key = k;
      this.value = v;
      this.ts = false;
    }
  }
}
