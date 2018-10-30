package de.splotycode.jwa.core;

import lombok.Setter;

public class ValueCacheListener<T> {

    private T value = null;
    private long start, delay;
    private CacheListener listener;

    public ValueCacheListener(T value, CacheListener listener, long delay) {
        this(listener, delay);
        this.value = value;
    }

    public ValueCacheListener(CacheListener listener, long delay) {
        start = System.currentTimeMillis();
        this.listener = listener;
        this.delay = delay;
    }

    public T getValue() {
        long now = System.currentTimeMillis();
        if (now - start > delay || value == null) {
            start = now;
            listener.refresh();
        }
        return value;
    }

    public void setValue(T value) {
        start = System.currentTimeMillis();
        this.value = value;
    }

    public interface CacheListener {

        void refresh();

    }

}
