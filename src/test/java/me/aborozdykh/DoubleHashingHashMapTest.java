package me.aborozdykh;

/**
 * @author Andrii Borozdykh
 */
public class DoubleHashingHashMapTest extends OpenAddressHashMapTest {
    @Override
    public OpenAddressHashMap getOpenAddressHashMap() {
        return new DoubleHashingHashMap();
    }

    @Override
    public OpenAddressHashMap getOpenAddressHashMap(int capacity) {
        return new DoubleHashingHashMap(capacity);
    }

    @Override
    protected OpenAddressHashMap getOpenAddressHashMap(double loadFactor) {
        return new DoubleHashingHashMap(loadFactor);
    }

    @Override
    protected OpenAddressHashMap getOpenAddressHashMap(int capacity, double loadFactor) {
        return new DoubleHashingHashMap(capacity, loadFactor);
    }
}
