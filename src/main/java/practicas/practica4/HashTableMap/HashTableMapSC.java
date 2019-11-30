package practicas.practica4.HashTableMap;

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
 * @author Jose Miguel Zamora Batista.
 */
@SuppressWarnings("unchecked")
public class HashTableMapSC<K, V> implements Map<K, V> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    protected int prime;
    protected long scale, shift;

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
        this.threshold = (int) (cap * DEFAULT_LOAD_FACTOR);
        this.bucket = (List<HashEntry<K,V>>[]) new LinkedList[Math.max(cap, DEFAULT_CAPACITY)];

        Random rand = new Random();
        this.scale = rand.nextInt(prime - 1) + 1;
        this.shift = rand.nextInt(prime);

        for(int i = 0; i < capacity; ++i) bucket[i] = new LinkedList<>();
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

    @Override
    public V get(K key) {
        HashEntry<K,V> entry = checkKey(key);
        return entry == null ? null : entry.value;
    }

    @Override
    public V put(K key, V value) {
        HashEntry<K,V> entry = checkKey(key);

        if(entry!=null)
            return entry.setValue(value);

        int indexToInsert = hashValue(key);
        entry = new HashEntry<>(key, value);
        bucket[indexToInsert].add(entry);

        if((size + 1) > threshold)
            rehash(capacity * 2);

        size++;
        return null;
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
        return new HashTableMapIterator<>(this.bucket);
    }

    @Override
    public Iterable<K> keys() {
        return () -> new HashTableMapKeyIterator<K, V>(new HashTableMapIterator<>(bucket));
    }

    @Override
    public Iterable<V> values() {
        return () -> new HashTableMapValueIterator<K, V>(new HashTableMapIterator<>(bucket));
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        return () -> new HashTableMapIterator<>(bucket);
    }

    /**
     * Determines whether a key is valid.
     *
     * @param k Key
     */
    private HashEntry<K,V> checkKey(K k) {
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

        this.capacity = newCap;
        this.threshold = (int) (this.capacity * DEFAULT_LOAD_FACTOR);

        List<HashEntry<K, V>>[] newBucket = new LinkedList[capacity];
        for(int i = 0; i < newBucket.length; ++i) newBucket[i] = new LinkedList<>();

        for(int i = 0; i < bucket.length; ++i) {
            if (!bucket[i].isEmpty()) {
                for(HashEntry<K,V> entry : bucket[i]) {
                    int index = hashValue(entry.getKey());
                    newBucket[index].add(entry);
                }
            }

            bucket[i].clear();
            bucket[i] = null;
        }

        bucket = newBucket;
    }

    private static class HashEntry<T, U> implements Entry<T, U> {

        protected T key;
        protected U value;

        HashEntry(T k, U v) {
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

        U setValue(U val) {
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i<bucket.length; ++i) {
            s.append("[").append(i).append("]:");
            for (int j = 0; j < bucket[i].size(); ++j)
                s.append(bucket[i].get(j).value).append(" ");
            s.append("\n");
        }

        return s.toString();
    }

    private class HashTableMapIterator<T, U> implements Iterator<Entry<T, U>> {

        private int index, top;

        private LinkedList<HashEntry<T, U>> queue;
        private List<HashEntry<T, U>>[] map;

        public HashTableMapIterator(List<HashEntry<T, U>>[] map) {
            this.map = map;
            this.index = 0;
            this.top = map.length - 1;

            while(this.map[index].isEmpty())
                ++index;

            while(this.map[top].isEmpty())
                --top;

            queue = new LinkedList<>();
            queue.addAll(this.map[index]);
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public Entry<T, U> next() {
            Entry<T, U> entry = queue.removeFirst();
            while(index<top && queue.isEmpty()) {
                ++index;
                if(map[index].isEmpty())
                    continue;

                queue.addAll(map[index]);
            }

            return entry;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapKeyIterator<T, U> implements Iterator<T> {

        HashTableMapIterator<T, U> it;

        public HashTableMapKeyIterator(HashTableMapIterator<T, U> it) {
            this.it = it;
        }

        @Override
        public T next() {
            return this.it.next().getKey();
        }

        @Override
        public boolean hasNext() {
            return this.it.hasNext();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapValueIterator<T, U> implements Iterator<U> {

        HashTableMapIterator<T, U> it;

        public HashTableMapValueIterator(HashTableMapIterator<T, U> it) {
            this.it = it;
        }

        @Override
        public U next() {
            return this.it.next().getValue();
        }

        @Override
        public boolean hasNext() {
            return this.it.hasNext();
        }

        @Override
        public void remove() {
            //NO HAY QUE IMPLEMENTARLO
            throw new UnsupportedOperationException("Not implemented.");
        }
    }
}
