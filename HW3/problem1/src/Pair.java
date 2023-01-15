class Pair<K,V>{
    K key;
    V value;
    Pair(K key, V value){
        this.key = key;
        this.value = value;
    }


    public V getValue() {
        return value;
    }

    public K getKey() {
        return key;
    }

    public void setValue(V newValue) {
        value = newValue;
    }
}

