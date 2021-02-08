package com.luizaprestes.frame.registries;

import java.util.Collection;
import java.util.WeakHashMap;

public class WeakRegistry<K, V> extends WeakHashMap<K, V> {

    public void register(K k, V v) {
        put(k, v);
    }

    public V getByValue(K k) {
        return get(k);
    }

    public Collection<V> getAll() {
        return values();
    }

    private void wipeAll() {
        clear();
    }

}
