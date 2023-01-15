package server;

public class Triple<K, V, T> {
    public K key;
    public V value;
    public T third;

    Triple(K key, V value, T third) {
        this.key = key;
        this.value = value;
        this.third = third;
    }
}
