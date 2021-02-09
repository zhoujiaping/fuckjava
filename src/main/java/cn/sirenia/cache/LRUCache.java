package cn.sirenia.cache;

import java.util.LinkedHashMap;
import java.util.Map;
//copy from com.alibaba.druid.util.LRUCache;
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = 1L;
    private final int maxSize;

    public LRUCache(int maxSize) {
        this(maxSize, 16, 0.75F, false);
    }

    public LRUCache(int maxSize, int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
        this.maxSize = maxSize;
    }
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > this.maxSize;
    }
}