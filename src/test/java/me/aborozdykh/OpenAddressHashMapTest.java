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

    @Test
    public void putAndGetOk() {
        var openAddressHashMap = getOpenAddressHashMap();
        openAddressHashMap.put(0, 0);
        openAddressHashMap.put(38, 1);
        openAddressHashMap.put(24, 6);
        openAddressHashMap.put(31, 7);
        long FirstActualValue = openAddressHashMap.get(0);
        long SecondActualValue = openAddressHashMap.get(38);
        long ThirdActualValue = openAddressHashMap.get(24);
        long FourthActualValue = openAddressHashMap.get(31);
        Assert.assertEquals("Test failed! Expected value " + 0 + ", but was " + FirstActualValue, 0, FirstActualValue);
        Assert.assertEquals("Test failed! Expected value " + 1 + ", but was " + SecondActualValue, 1, SecondActualValue);
        Assert.assertEquals("Test failed! Expected value " + 6 + ", but was " + ThirdActualValue, 6, ThirdActualValue);
        Assert.assertEquals("Test failed! Expected value " + 7 + ", but was " + FourthActualValue, 7, FourthActualValue);
    }

    @Test
    public void putAndGetWithNegativeKeysOk() {
        var openAddressHashMap = getOpenAddressHashMap();
        openAddressHashMap.put(38, 1);
        openAddressHashMap.put(-38, 4);
        openAddressHashMap.put(-54, 5);
        long FirstActualValue = openAddressHashMap.get(38);
        long SecondActualValue = openAddressHashMap.get(-38);
        long ThirdActualValue = openAddressHashMap.get(-54);
        Assert.assertEquals("Test failed! Expected value " + 1 + ", but was " + FirstActualValue, 1, FirstActualValue);
        Assert.assertEquals("Test failed! Expected value " + 4 + ", but was " + SecondActualValue, 4, SecondActualValue);
        Assert.assertEquals("Test failed! Expected value " + 5 + ", but was " + ThirdActualValue, 5, ThirdActualValue);
    }

    @Test
    public void putAndGetSameElementsOk() {
        var openAddressHashMap = getOpenAddressHashMap();
        openAddressHashMap.put(0, 0);
        openAddressHashMap.put(38, 1);
        openAddressHashMap.put(0, 100);
        openAddressHashMap.put(38, 107);
        long FirstActualValue = openAddressHashMap.get(0);
        long SecondActualValue = openAddressHashMap.get(38);
        Assert.assertEquals("Test failed! Expected value " + 100 + ", but was " + FirstActualValue, 100, FirstActualValue);
        Assert.assertEquals("Test failed! Expected value " + 107 + ", but was " + SecondActualValue, 107, SecondActualValue);
    }

    @Test
    public void putAndGetWithCollisionOk() {
        var openAddressHashMap = getOpenAddressHashMap();
        openAddressHashMap.put(38, 1);
        openAddressHashMap.put(39, 2);
        openAddressHashMap.put(54, 3);
        openAddressHashMap.put(-38, 4);
        long FirstActualValue = openAddressHashMap.get(38);
        long SecondActualValue = openAddressHashMap.get(39);
        long ThirdActualValue = openAddressHashMap.get(54);
        long FourthActualValue = openAddressHashMap.get(-38);
        Assert.assertEquals("Test failed! Expected value " + 1 + ", but was " + FirstActualValue, 1, FirstActualValue);
        Assert.assertEquals("Test failed! Expected value " + 2 + ", but was " + SecondActualValue, 2, SecondActualValue);
        Assert.assertEquals("Test failed! Expected value " + 3 + ", but was " + ThirdActualValue, 3, ThirdActualValue);
        Assert.assertEquals("Test failed! Expected value " + 4 + ", but was " + FourthActualValue, 4, FourthActualValue);
    }

    @Test
    public void putAndGetWithCustomCapacityOk() {
        var openAddressHashMap = getOpenAddressHashMap(10);
        openAddressHashMap.put(0, 0);
        openAddressHashMap.put(38, 1);
        openAddressHashMap.put(24, 6);
        openAddressHashMap.put(31, 7);
        long FirstActualValue = openAddressHashMap.get(0);
        long SecondActualValue = openAddressHashMap.get(38);
        long ThirdActualValue = openAddressHashMap.get(24);
        long FourthActualValue = openAddressHashMap.get(31);
        Assert.assertEquals("Test failed! Expected value " + 0 + ", but was " + FirstActualValue, 0, FirstActualValue);
        Assert.assertEquals("Test failed! Expected value " + 1 + ", but was " + SecondActualValue, 1, SecondActualValue);
        Assert.assertEquals("Test failed! Expected value " + 6 + ", but was " + ThirdActualValue, 6, ThirdActualValue);
        Assert.assertEquals("Test failed! Expected value " + 7 + ", but was " + FourthActualValue, 7, FourthActualValue);
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
            Assert.assertEquals("Test failed! Expected value " + i + ", but was " + actualValue, i, actualValue);

        }
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
        Assert.assertEquals("Test failed! The size isn't correct. Expected " + expectedSize + ", but was " + actualSize, expectedSize, actualSize);
    }

    @Test
    public void getSizeWithCollisionOk() {
        var openAddressHashMap = getOpenAddressHashMap();
        int expectedSize = 3;
        openAddressHashMap.put(38, 1);
        openAddressHashMap.put(-38, 4);
        openAddressHashMap.put(-54, 5);
        long actualSize = openAddressHashMap.size();
        Assert.assertEquals("Test failed! The size isn't correct. Expected " + expectedSize + ", but was " + actualSize, expectedSize, actualSize);
    }

    @Test
    public void getSizeWithCollisionAtZeroPositionOk() {
        var openAddressHashMap = getOpenAddressHashMap();
        int expectedSize = 3;
        openAddressHashMap.put(0, 0);
        openAddressHashMap.put(-38, 4);
        openAddressHashMap.put(32, 9);
        long actualSize = openAddressHashMap.size();
        Assert.assertEquals("Test failed! The size isn't correct. Expected " + expectedSize + ", but was " + actualSize, expectedSize, actualSize);
    }

    @Test
    public void getSizeWithCollisionAtLastPositionOk() {
        var openAddressHashMap = getOpenAddressHashMap();
        int expectedSize = 3;
        openAddressHashMap.put(31, 7);
        openAddressHashMap.put(79, 8);
        openAddressHashMap.put(0, 0);
        long actualSize = openAddressHashMap.size();
        Assert.assertEquals("Test failed! The size isn't correct. Expected " + expectedSize + ", but was " + actualSize, expectedSize, actualSize);
    }

    @Test
    public void getSizeAfterResizeOk() {
        var openAddressHashMap = getOpenAddressHashMap();
        int expectedSize = 25;
        for (int i = 0; i < expectedSize; i++) {
            openAddressHashMap.put(i, i);
        }
        long actualSize = openAddressHashMap.size();
        Assert.assertEquals("Test failed! The size isn't correct. Expected " + expectedSize + ", but was " + actualSize, expectedSize, actualSize);
    }

    @Test(expected = NoSuchElementException.class)
    public void getByNonExistedKey() {
        var openAddressHashMap = getOpenAddressHashMap();
        long value = openAddressHashMap.get(1);
    }
}
