/**
 * @author Andrii Borozdykh
 */
public class LinearProbingHashMapTest extends OpenAddressHashMapTest {
    @Override
    public OpenAddressHashMap getOpenAddressHashMap() {
        OpenAddressHashMap openAddressHashMap = new LinearProbingHashMap();
        return openAddressHashMap;
    }

    @Override
    public OpenAddressHashMap getOpenAddressHashMap(int capacity) {
        OpenAddressHashMap openAddressHashMap = new LinearProbingHashMap();
        return openAddressHashMap;
    }
}
