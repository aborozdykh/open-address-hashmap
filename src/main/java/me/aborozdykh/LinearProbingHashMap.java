package me.aborozdykh;

import java.util.Arrays;
import me.aborozdykh.exception.NoSuchElementException;
import me.aborozdykh.exception.WrongKeyException;

/**
 * @author Andrii Borozdykh
 */
public class LinearProbingHashMap implements OpenAddressHashMap {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private static final int RESIZE_MULTIPLIER = 2;
    private static final int FREE_CELL = Integer.MIN_VALUE;
    private long size;
    private int[] keys;
    private long[] values;

    public LinearProbingHashMap() {
        this.size = 0;
        this.keys = new int[DEFAULT_CAPACITY];
        this.values = new long[DEFAULT_CAPACITY];
        Arrays.fill(keys, FREE_CELL);
    }

    public LinearProbingHashMap(int capacity) {
        this.size = 0;
        this.keys = new int[capacity];
        this.values = new long[capacity];
        Arrays.fill(keys, FREE_CELL);
    }

    public void put(int key, long value) {
        if (key == FREE_CELL) {
            throw new WrongKeyException("Key " + FREE_CELL
                    + "is reserved. Please try to use another key.");
        }
        if (size >= DEFAULT_LOAD_FACTOR * hashMapCapacity()) {
            resize();
        }
        for (int i = hash(key); ; i++) {
            if (i == hashMapCapacity()) {
                i = 0;
            }
            if (keys[i] == FREE_CELL) {
                keys[i] = key;
                size++;
            }
            if (keys[i] == key) {
                values[i] = value;
                return;
            }
        }
    }

    public long get(int key) {
        for (int i = hash(key); ; i++) {
            if (i == hashMapCapacity()) {
                i = 0;
            }
            if (keys[i] == FREE_CELL) {
                throw new NoSuchElementException("No such key!");
            }
            if (keys[i] == key) {
                return values[i];
            }
        }
    }

    public long size() {
        return size;
    }

    private int hash(int key) {
        return Math.abs(key) % hashMapCapacity();
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
