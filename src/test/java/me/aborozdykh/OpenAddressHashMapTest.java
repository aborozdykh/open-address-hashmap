package me.aborozdykh;

import me.aborozdykh.exception.NoSuchElementException;
import me.aborozdykh.exception.WrongKeyException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrii Borozdykh
 */
public abstract class OpenAddressHashMapTest {
    protected abstract OpenAddressHashMap getOpenAddressHashMap();

    protected abstract OpenAddressHashMap getOpenAddressHashMap(int capacity);

    protected abstract OpenAddressHashMap getOpenAddressHashMap(double loadFactor);

    protected abstract OpenAddressHashMap getOpenAddressHashMap(int capacity, double loadFactor);

    @Test
    public void putAndGetOk() {
        var openAddressHashMap = getOpenAddressHashMap();
        openAddressHashMap.put(0, 0);
        openAddressHashMap.put(38, 1);
        openAddressHashMap.put(24, 6);
        openAddressHashMap.put(31, 7);
        long firstActualValue = openAddressHashMap.get(0);
        long secondActualValue = openAddressHashMap.get(38);
        long thirdActualValue = openAddressHashMap.get(24);
        long fourthActualValue = openAddressHashMap.get(31);
        Assert.assertEquals("Test failed! Expected value " + 0 + ", but was "
                + firstActualValue, 0, firstActualValue);
        Assert.assertEquals("Test failed! Expected value " + 1 + ", but was "
                + secondActualValue, 1, secondActualValue);
        Assert.assertEquals("Test failed! Expected value " + 6 + ", but was "
                + thirdActualValue, 6, thirdActualValue);
        Assert.assertEquals("Test failed! Expected value " + 7 + ", but was "
                + fourthActualValue, 7, fourthActualValue);
    }

    @Test
    public void putAndGetWithNegativeKeysOk() {
        var openAddressHashMap = getOpenAddressHashMap();
        openAddressHashMap.put(38, 1);
        openAddressHashMap.put(-38, 4);
        openAddressHashMap.put(-54, 5);
        long firstActualValue = openAddressHashMap.get(38);
        long secondActualValue = openAddressHashMap.get(-38);
        long thirdActualValue = openAddressHashMap.get(-54);
        Assert.assertEquals("Test failed! Expected value " + 1 + ", but was "
                + firstActualValue, 1, firstActualValue);
        Assert.assertEquals("Test failed! Expected value " + 4 + ", but was "
                + secondActualValue, 4, secondActualValue);
        Assert.assertEquals("Test failed! Expected value " + 5 + ", but was "
                + thirdActualValue, 5, thirdActualValue);
    }

    @Test
    public void putAndGetSameElementsOk() {
        var openAddressHashMap = getOpenAddressHashMap();
        openAddressHashMap.put(0, 0);
        openAddressHashMap.put(38, 1);
        openAddressHashMap.put(0, 100);
        openAddressHashMap.put(38, 107);
        long firstActualValue = openAddressHashMap.get(0);
        long secondActualValue = openAddressHashMap.get(38);
        Assert.assertEquals("Test failed! Expected value " + 100 + ", but was "
                + firstActualValue, 100, firstActualValue);
        Assert.assertEquals("Test failed! Expected value " + 107 + ", but was "
                + secondActualValue, 107, secondActualValue);
    }

    @Test
    public void putAndGetWithCollisionOk() {
        var openAddressHashMap = getOpenAddressHashMap();
        openAddressHashMap.put(38, 1);
        openAddressHashMap.put(39, 2);
        openAddressHashMap.put(54, 3);
        openAddressHashMap.put(-38, 4);
        long firstActualValue = openAddressHashMap.get(38);
        long secondActualValue = openAddressHashMap.get(39);
        long thirdActualValue = openAddressHashMap.get(54);
        long fourthActualValue = openAddressHashMap.get(-38);
        Assert.assertEquals("Test failed! Expected value " + 1 + ", but was "
                + firstActualValue, 1, firstActualValue);
        Assert.assertEquals("Test failed! Expected value " + 2 + ", but was "
                + secondActualValue, 2, secondActualValue);
        Assert.assertEquals("Test failed! Expected value " + 3 + ", but was "
                + thirdActualValue, 3, thirdActualValue);
        Assert.assertEquals("Test failed! Expected value " + 4 + ", but was "
                + fourthActualValue, 4, fourthActualValue);
    }

    @Test
    public void putAndGetWithCustomCapacityOk() {
        var openAddressHashMap = getOpenAddressHashMap(10);
        openAddressHashMap.put(0, 0);
        openAddressHashMap.put(38, 1);
        openAddressHashMap.put(24, 6);
        openAddressHashMap.put(31, 7);
        long firstActualValue = openAddressHashMap.get(0);
        long secondActualValue = openAddressHashMap.get(38);
        long thirdActualValue = openAddressHashMap.get(24);
        long fourthActualValue = openAddressHashMap.get(31);
        Assert.assertEquals("Test failed! Expected value " + 0 + ", but was "
                + firstActualValue, 0, firstActualValue);
        Assert.assertEquals("Test failed! Expected value " + 1 + ", but was "
                + secondActualValue, 1, secondActualValue);
        Assert.assertEquals("Test failed! Expected value " + 6 + ", but was "
                + thirdActualValue, 6, thirdActualValue);
        Assert.assertEquals("Test failed! Expected value " + 7 + ", but was "
                + fourthActualValue, 7, fourthActualValue);
    }

    @Test
    public void putAndGetBigCapacity() {
        int bigCapacity = 50_000_000;
        var openAddressHashMap = getOpenAddressHashMap();
        for (int i = 0; i <= bigCapacity; i++) {
            openAddressHashMap.put(i, i);
        }
        for (int i = 0; i <= bigCapacity; i++) {
            long actualValue = openAddressHashMap.get(i);
            Assert.assertEquals("Test failed! Expected value " + i + ", but was "
                    + actualValue, i, actualValue);

        }
    }

    @Test
    public void putManyGetOneWithBigCapacity() {
        int bigCapacity = 50_000_000;
        var openAddressHashMap = getOpenAddressHashMap();
        for (int i = 0; i <= bigCapacity; i++) {
            openAddressHashMap.put(i, i);
        }
        long actualValue = openAddressHashMap.get(25_000_000);
        Assert.assertEquals("Test failed! Expected value " + 25_000_000 + ", but was "
                
                + actualValue, 25_000_000, actualValue);
    }

    @Test(expected = WrongKeyException.class)
    public void putFreeCellMarkerAsKey() {
        var openAddressHashMap = getOpenAddressHashMap();
        openAddressHashMap.put(Integer.MIN_VALUE, 10);
    }

    @Test(expected = NoSuchElementException.class)
    public void getFreeCellMarkerAsKey() {
        var openAddressHashMap = getOpenAddressHashMap();
        openAddressHashMap.get(Integer.MIN_VALUE);
    }

    @Test
    public void getSizeOfEmptyHashMapOk() {
        var openAddressHashMap = getOpenAddressHashMap();
        int expectedSize = 0;
        long actualSize = openAddressHashMap.size();
        Assert.assertEquals("Test failed! The size isn't correct. Expected "
                + expectedSize + ", but was " + actualSize, expectedSize, actualSize);
    }

    @Test
    public void getSizeWithCollisionOk() {
        var openAddressHashMap = getOpenAddressHashMap();
        openAddressHashMap.put(38, 1);
        openAddressHashMap.put(-38, 4);
        openAddressHashMap.put(-54, 5);
        long actualSize = openAddressHashMap.size();
        int expectedSize = 3;
        Assert.assertEquals("Test failed! The size isn't correct. Expected "
                + expectedSize + ", but was " + actualSize, expectedSize, actualSize);
    }

    @Test
    public void getSizeWithCollisionAtZeroPositionOk() {
        var openAddressHashMap = getOpenAddressHashMap();
        openAddressHashMap.put(0, 0);
        openAddressHashMap.put(-38, 4);
        openAddressHashMap.put(32, 9);
        long actualSize = openAddressHashMap.size();
        int expectedSize = 3;
        Assert.assertEquals("Test failed! The size isn't correct. Expected "
                + expectedSize + ", but was " + actualSize, expectedSize, actualSize);
    }

    @Test
    public void getSizeWithCollisionAtLastPositionOk() {
        var openAddressHashMap = getOpenAddressHashMap();
        openAddressHashMap.put(31, 7);
        openAddressHashMap.put(79, 8);
        openAddressHashMap.put(0, 0);
        long actualSize = openAddressHashMap.size();
        int expectedSize = 3;
        Assert.assertEquals("Test failed! The size isn't correct. Expected "
                + expectedSize + ", but was " + actualSize, expectedSize, actualSize);
    }

    @Test
    public void getSizeAfterResizeOk() {
        var openAddressHashMap = getOpenAddressHashMap();
        int expectedSize = 25;
        for (int i = 0; i < expectedSize; i++) {
            openAddressHashMap.put(i, i);
        }
        long actualSize = openAddressHashMap.size();
        Assert.assertEquals("Test failed! The size isn't correct. Expected "
                + expectedSize + ", but was " + actualSize, expectedSize, actualSize);
    }

    @Test
    public void getSizeAfterResizeWithCustomLoadFactorOk() {
        var openAddressHashMap = getOpenAddressHashMap(0.5);
        int expectedSize = 25;
        for (int i = 0; i < expectedSize; i++) {
            openAddressHashMap.put(i, i);
        }
        long actualSize = openAddressHashMap.size();
        Assert.assertEquals("Test failed! The size isn't correct. Expected "
                + expectedSize + ", but was " + actualSize, expectedSize, actualSize);
    }

    @Test
    public void getSizeAfterResizeWithCustomLoadFactorAndCustomCapacityOk() {
        var openAddressHashMap = getOpenAddressHashMap(32, 0.5);
        int expectedSize = 25;
        for (int i = 0; i < expectedSize; i++) {
            openAddressHashMap.put(i, i);
        }
        long actualSize = openAddressHashMap.size();
        Assert.assertEquals("Test failed! The size isn't correct. Expected "
                + expectedSize + ", but was " + actualSize, expectedSize, actualSize);
    }

    @Test(expected = NoSuchElementException.class)
    public void getByNonExistedKey() {
        var openAddressHashMap = getOpenAddressHashMap();
        long value = openAddressHashMap.get(1);
    }
}
