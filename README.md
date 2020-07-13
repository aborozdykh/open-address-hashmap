# Implementation HashMap with open addressing

This test case contains two implementations of open addressing HashMap with int keys and long values.   

## Summary

  - [Implementation details](#implementation-details)
  - [Run and test](#run-and-test)
  - [Authors](#authors)

### Implementation details
    
There are two types of open addressing HashMap's implementation in this test case. The first one uses linear probing for collision resolution, and the second one uses double hashing.

There are two types of constructors in each implementation: the first one is without parameters (default constructor), and the second one is with custom capacity.

Each implementation has following parameters by default:

    - Size: 0
    - Initial capacity: 16
    - Load factor: 0.75 
    - Keys array: int[capacity]
    - Values array: long[capacity]

Each implementation has following public methods:

    - put(int key, long value);
    - get(int key);
    - size();

If the HashMap is 75% full, it will be resized.

Linear probing implementation uses only one hash function:

    private int hash(int key) {
        return Math.abs(key) % hashMapCapacity();
    }
    
Double hashing implementation uses two hash functions:

    private int hash(int key, int i) {
        int hashFirst = Math.abs(key) % hashMapCapacity();
        int hashSecond = (Math.abs(key) % (hashMapCapacity() - 1)) + 1;
        return (hashFirst + i * hashSecond) % hashMapCapacity();
    }

  - Maven Checkstyle Plugin and Travis CI have been configured
  - Unit tests are present

## Run and test

You need for test:

  - JDK 8 or higher
  - Apache Maven

## Authors

  - [Andrii Borozdykh](https://github.com/aborozdykh/)
