package practicas.practica4.HashTableMap;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import practicas.practica4.Entry;
import practicas.practica4.Map;

/**
 * Separate chaining table implementation of hash tables. Note that all
 * "matching" is based on the equals method.
 *
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro, JD. Quintana
 */
public class HashTableMapSC<K, V> implements Map<K, V> {

    private static final int DEFAULT_CAPACITY = 1000;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    protected int prime;
    protected long scale, shift; // the shift and scaling factors
    private double maxLoadFactor;
    private int capacity, threshold, size = 0;
    private List<HashEntry<K,V >>[] bucket;

    /**
     * Creates a hash table with prime factor 109345121 and capacity 1000.
     */
    public HashTableMapSC() {
        this(109345121, DEFAULT_CAPACITY);
    }

    /**
     * Creates a hash table with prime factor 109345121 and given capacity.
     *
     * @param cap initial capacity
     */
    public HashTableMapSC(int cap) {
        this(109345121, cap);
    }

    /**
     * Creates a hash table with the given prime factor and capacity.
     *
     * @param p   prime number
     * @param cap initial capacity
     */
    public HashTableMapSC(int p, int cap) {
        this.prime = p;
        this.capacity = cap;
        this.bucket = (List<HashEntry<K,V>>[]) new LinkedList[this.capacity]; // safe cast
        Arrays.fill(bucket, new LinkedList<>());
        Random rand = new Random();
        this.scale = rand.nextInt(prime - 1) + 1;
        this.shift = rand.nextInt(prime);
    }

    /**
     * Hash function applying MAD method to default hash code.
     *
     * @param key Key
     * @return the hash value
     */
    protected int hashValue(K key) {
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    // todo: mezclar funciones get e insert
    @Override
    public V get(K key) {
        HashEntry<K,V> entry = checkKey(key);
        return entry == null ? null : entry.value;
    }

    @Override
    public V put(K key, V value) {
        HashEntry<K,V> oldEntry = checkKey(key);
        V val;

        if(oldEntry==null) {
            val = null;
            bucket[hashValue(key)].add(new HashEntry<>(key, value));
            size++;
        }

        else{
            val = oldEntry.value;
            oldEntry.setValue(value);
        }
        return val;
    }

    @Override
    public V remove(K key) {
        HashEntry<K,V> entry = checkKey(key);
        bucket[hashValue(key)].remove(entry);

        if(entry!=null)
            size--;

        return entry == null ? null : entry.value;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public Iterable<K> keys() {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public Iterable<V> values() {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        throw new RuntimeException("Not yet implemented.");
    }

    /**
     * Determines whether a key is valid.
     *
     * @param k Key
     */
    private HashEntry<K,V> checkKey(K k) {
        // We cannot check the second test (i.e., k instanceof K) since we do not know the class K
        if (k == null)
            throw new IllegalStateException("Invalid key: null.");

        int index = hashValue(k);
        for(HashEntry<K,V> entry : bucket[index])
            if(entry.getKey().equals(k))
                return entry;

        return null;
    }

    /**
     * Increase/reduce the size of the hash table and rehashes all the entries.
     */
    protected void rehash(int newCap) {
        throw new RuntimeException("Not yet implemented.");
    }

    private class HashEntry<T, U> implements Entry<T, U> {

        protected T key;
        protected U value;

        public HashEntry(T k, U v) {
            key = k;
            value = v;
        }

        @Override
        public U getValue() {
            return value;
        }

        @Override
        public T getKey() {
            return key;
        }

        public U setValue(U val) {
            U oldValue = value;
            value = val;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (o.getClass() != this.getClass())
                return false;

            HashEntry<T, U> ent;

            try {
                ent = (HashEntry<T, U>) o;
            } catch (ClassCastException ex) {
                return false;
            }
            return (ent.getKey().equals(this.key))
                    && (ent.getValue().equals(this.value));
        }
    }

    private class HashTableMapIterator<T, U> implements Iterator<Entry<T, U>> {

        private int posMap;
        private int posChain;
        private List<HashEntry<T, U>>[] map;

        public HashTableMapIterator(List<HashEntry<T, U>>[] map, int numElems) {
                this.map = map;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Entry<T, U> next() {
            return null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }

    }

    private class HashTableMapKeyIterator<T, U> implements Iterator<T> {

        public HashTableMapKeyIterator(HashTableMapIterator<T, U> it) {
            throw new RuntimeException("Not yet implemented.");
        }

        @Override
        public T next() {
            throw new RuntimeException("Not yet implemented.");
        }

        @Override
        public boolean hasNext() {
            throw new RuntimeException("Not yet implemented.");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapValueIterator<T, U> implements Iterator<U> {

        public HashTableMapValueIterator(HashTableMapIterator<T, U> it) {
            throw new RuntimeException("Not yet implemented.");
        }

        @Override
        public U next() { throw new RuntimeException("Not yet implemented.");
        }

        @Override
        public boolean hasNext() {
            throw new RuntimeException("Not yet implemented.");
        }

        @Override
        public void remove() {
            //NO HAY QUE IMPLEMENTARLO
            throw new UnsupportedOperationException("Not implemented.");
        }
    }
}
