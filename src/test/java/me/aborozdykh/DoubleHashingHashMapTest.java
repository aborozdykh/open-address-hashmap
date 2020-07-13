package me.aborozdykh;

/**
 * @author Andrii Borozdykh
 */
public class DoubleHashingHashMapTest extends OpenAddressHashMapTest {
    @Override
    public OpenAddressHashMap getOpenAddressHashMap() {
        OpenAddressHashMap openAddressHashMap = new DoubleHashingHashMap();
        return openAddressHashMap;
    }

    @Override
    public OpenAddressHashMap getOpenAddressHashMap(int capacity) {
        OpenAddressHashMap openAddressHashMap = new DoubleHashingHashMap();
        return openAddressHashMap;
    }
}