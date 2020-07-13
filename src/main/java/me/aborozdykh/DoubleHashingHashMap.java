package me.aborozdykh;

import java.util.Arrays;
import me.aborozdykh.exception.NoSuchElementException;
import me.aborozdykh.exception.WrongKeyException;

/**
 * @author Andrii Borozdykh
 */
public class DoubleHashingHashMap implements OpenAddressHashMap {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private static final int RESIZE_MULTIPLIER = 2;
    private static final int FREE_CELL = Integer.MIN_VALUE;
    private long size;
    private int[] keys;
    private long[] values;
    private final double loadFactor;

    public DoubleHashingHashMap() {
        this.size = 0;
        this.keys = new int[DEFAULT_CAPACITY];
        this.values = new long[DEFAULT_CAPACITY];
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        Arrays.fill(keys, FREE_CELL);
    }

    public DoubleHashingHashMap(int capacity) {
        this.size = 0;
        this.keys = new int[capacity];
        this.values = new long[capacity];
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        Arrays.fill(keys, FREE_CELL);
    }

    public DoubleHashingHashMap(double loadFactor) {
        this.size = 0;
        this.keys = new int[DEFAULT_CAPACITY];
        this.values = new long[DEFAULT_CAPACITY];
        this.loadFactor = loadFactor;
        Arrays.fill(keys, FREE_CELL);
    }

    public DoubleHashingHashMap(int capacity, double loadFactor) {
        this.size = 0;
        this.keys = new int[capacity];
        this.values = new long[capacity];
        this.loadFactor = loadFactor;
        Arrays.fill(keys, FREE_CELL);
    }

    public void put(int key, long value) {
        if (key == FREE_CELL) {
            throw new WrongKeyException("Key " + FREE_CELL
                    + "is reserved. Please try to use another key.");
        }
        if (size >= loadFactor * hashMapCapacity()) {
            resize();
        }
        for (int i = 0; i < hashMapCapacity(); i++) {
            int index = hash(key, i);
            if (keys[index] == FREE_CELL) {
                keys[index] = key;
                size++;
            }
            if (keys[index] == key) {
                values[index] = value;
                return;
            }
        }
    }

    public long get(int key) {
        for (int i = 0; i < hashMapCapacity(); i++) {
            int index = hash(key, i);
            if (keys[index] == FREE_CELL) {
                throw new NoSuchElementException("No such key!");
            }
            if (keys[index] == key) {
                return values[index];
            }
        }
        throw new NoSuchElementException("No such key!");
    }

    public long size() {
        return size;
    }

    private int hash(int key, int i) {
        int hashFirst = Math.abs(key) % hashMapCapacity();
        int hashSecond = (Math.abs(key) % (hashMapCapacity() - 1)) + 1;
        return (hashFirst + i * hashSecond) % hashMapCapacity();
    }

    private int hashMapCapacity() {
        return keys.length;
    }

    private void resize() {
        size = 0;
        int newCapacity = hashMapCapacity() * RESIZE_MULTIPLIER;
        long[] oldValues = values;
        values = new long[newCapacity];
        int[] oldKeys = keys;
        keys = new int[newCapacity];
        Arrays.fill(keys, FREE_CELL);
        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != FREE_CELL) {
                put(oldKeys[i], oldValues[i]);
            }
        }
    }
}
