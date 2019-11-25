package practicas.practica4.HashTableMap;

import practicas.practica4.AbstractHashTableMap;

/**
 * @param <K> The hey
 * @param <V> The stored value
 */
public class HashTableMapDH<K, V> extends AbstractHashTableMap<K, V> {

    public HashTableMapDH(int size) {
        super(size);
    }

    public HashTableMapDH() {
        super();
    }

    public HashTableMapDH(int p, int cap) {
        super(p, cap);
    }

    @Override
    protected int offset(K key, int i) {
        this.checkKey(key);
        return (hashValue(key) + doubleHashValue(key) * i) % capacity;
    }

    public int doubleHashValue(K key) {
        return prime - (key.hashCode() % prime);
    }
}
