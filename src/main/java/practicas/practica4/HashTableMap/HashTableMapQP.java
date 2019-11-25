package practicas.practica4.HashTableMap;

import practicas.practica4.AbstractHashTableMap;

/**
 * @param <K> The hey
 * @param <V> The stored value
     */
public class HashTableMapQP<K, V> extends AbstractHashTableMap<K, V> {

    private static int C1 = 2;
    private static int C2 = 3;

    public HashTableMapQP(int size) {
        super(size);
    }

    public HashTableMapQP() {
        super();
    }

    public HashTableMapQP(int p, int cap) {
        super(p,cap);
    }

    @Override
    protected int offset(K key, int i) {
        this.checkKey(key);
        return (this.hashValue(key) + C1*i + C2*i*i) % this.capacity;
    }

}
