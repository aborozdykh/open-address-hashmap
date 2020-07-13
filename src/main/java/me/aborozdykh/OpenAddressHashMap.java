package me.aborozdykh;

public interface OpenAddressHashMap {
    void put(int key, long value);

    long get(int key);

    long size();
}
