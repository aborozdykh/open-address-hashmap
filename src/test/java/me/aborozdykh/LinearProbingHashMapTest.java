package me.aborozdykh;

/**
 * @author Andrii Borozdykh
 */
public class LinearProbingHashMapTest extends OpenAddressHashMapTest {
    @Override
    public OpenAddressHashMap getOpenAddressHashMap() {
        return new LinearProbingHashMap();
    }

    @Override
    public OpenAddressHashMap getOpenAddressHashMap(int capacity) {
        return new LinearProbingHashMap(capacity);
    }
}
