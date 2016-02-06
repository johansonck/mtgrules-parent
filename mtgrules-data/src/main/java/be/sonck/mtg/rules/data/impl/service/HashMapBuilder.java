package be.sonck.mtg.rules.data.impl.service;

import com.google.common.collect.ImmutableMap;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by johansonck on 15/07/15.
 */
public class HashMapBuilder<K, V> {

    private final Map<K, V> map = new LinkedHashMap<>();

    public Map<K, V> build() {
        return map;
    }

    public HashMapBuilder<K, V> entry(K key, V value) {
        map.put(key, value);

        ImmutableMap.<String, String>builder().put("test", "test").build();

        return this;
    }
}
