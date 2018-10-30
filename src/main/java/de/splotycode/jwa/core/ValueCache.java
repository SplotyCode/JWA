package de.splotycode.jwa.core;

import java.util.function.Supplier;

public class ValueCache<T> {

    private T value = null;
    private long start, delay;
    private Supplier<T> supplier;

    public ValueCache(T value, Supplier<T> supplier, long delay) {
        this(supplier, delay);
        this.value = value;
    }

    public ValueCache(Supplier<T> supplier, long delay) {
        start = System.currentTimeMillis();
        this.supplier = supplier;
        this.delay = delay;
    }

    public T getValue() {
        long now = System.currentTimeMillis();
        if (now - start > delay || value == null) {
            start = now;
            value = supplier.get();
        }
        return value;
    }
}
