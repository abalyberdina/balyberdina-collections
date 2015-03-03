package com.epam.mymap;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import com.epam.mymap.MyHashMap;
import com.epam.mymap.MyMap;
import com.epam.mymap.MyMap.Entry;

public class MyHashMapTest {
    MyMap<String, Object> hashMap;

    @Before
    public void init() {
        hashMap = new MyHashMap<>();
    }

    @Test
    public void sizeAddingElementCheckValue() {
        hashMap.put("first", 10);
        hashMap.put("second", null);
        hashMap.put("third", -1);
        assertEquals(3, hashMap.size());
    }

    @Test
    public void putNullKeyCheckSize() {
        hashMap.put(null, new Integer(12));
        assertEquals(1, hashMap.size());
    }

    @Test
    public void putFewValuesToOneKeyCheckSize() {
        hashMap.put(null, null);
        hashMap.put(null, 12);
        hashMap.put("first", 13);
        hashMap.put("first", 1);
        assertEquals(2, hashMap.size());
    }

    @Test
    public void putFewValuesToOneKeyCheckResultValue() {
        hashMap.put(null, null);
        hashMap.put(null, 12);
        assertEquals(12, hashMap.get(null));
    }

    @Test
    public void getNotExistedKeyShouldReturnNull() {
        hashMap.put("only", 12);
        assertNull(hashMap.get("first"));
    }

    @Test
    public void getNotExistedKeyWithSameHashShouldReturnNull() {
        hashMap.put("first", 12);
        assertNull(hashMap.get(null));
    }

    @Test
    public void getNullKeyCheckValue() {
        Integer value = 123;
        hashMap.put(null, value);
        assertEquals(value, hashMap.get(null));
    }

    @Test
    public void getExistedKeyCheckValues() {
        Integer value = 155;
        String key = "key";
        hashMap.put(key, value);
        assertEquals(value, hashMap.get(key));
    }

    @Test
    public void getFromEmptyMapShouldReturnNull() {
        assertNull(hashMap.get("Whatever"));
    }

    @Test
    public void isEmptyEmptyMapShouldBeTrue() {
        assertTrue(hashMap.isEmpty());
    }

    @Test
    public void isEmptyNotEmptyMapShouldBeFalse() {
        hashMap.put("key", 1);
        assertFalse(hashMap.isEmpty());
    }

    @Test
    public void clearEmptyArrayCheckSize() {
        hashMap.clear();
        assertEquals(0, hashMap.size());
    }

    @Test
    public void clearNotEmptyArrayCheckSize() {

        hashMap.clear();
        hashMap.put("key", 1234);
        hashMap.put("key1", 1234);
        hashMap.put("key2", 12345);
        hashMap.put("key3", 12346);
        hashMap.clear();
        assertEquals(0, hashMap.size());
    }

    @Test
    public void clearNotEmptyArrayTryGetDeletedElement() {
        hashMap.put("key", 1234);
        hashMap.put("key3", 1234);
        hashMap.put("key2", 12345);
        hashMap.put("key1", 12346);
        hashMap.clear();
        Object result = hashMap.get("key");
        assertEquals(null, result);
    }

    @Test
    public void containsKeyExistedKeyShouldBeTrue() {
        hashMap.put("key", 1234);
        hashMap.put("key3", 1234);
        hashMap.put("key2", 12345);
        assertTrue(hashMap.containsKey("key2"));
    }

    @Test
    public void containsKeyNotExistedKeyShouldBeFalse() {
        hashMap.put("key", 1234);
        hashMap.put("key3", 1234);
        hashMap.put("key2", 12345);
        assertFalse(hashMap.containsKey("key223"));
    }

    @Test
    public void containsKeyNotExistedKeyWithSameHash() {
        hashMap.put(null, 12345);
        assertFalse(hashMap.containsKey("first"));
    }

    @Test
    public void containsKeyEmptyMapShouldBeFalse() {
        assertFalse(hashMap.containsKey("aa"));
    }

    @Test
    public void containsValueNotExistedValueReturnFalse() {
        hashMap.put("key", 1234);
        hashMap.put("key3", 1234);
        hashMap.put("key2", 12345);
        assertFalse(hashMap.containsValue(12));
    }

    @Test
    public void containsValueExistedValueReturnTrue() {
        hashMap.put("key", 1234);
        hashMap.put("key3", 1234);
        hashMap.put("key2", 12345);
        assertTrue(hashMap.containsValue(1234));
    }

    @Test
    public void containsValueEmptyArrayReturnFalse() {
        assertFalse(hashMap.containsValue(1234));
    }

    @Test
    public void removeExistedElementCheckSize() {
        hashMap.put("key", 1234);
        hashMap.put("key3", 1234);
        hashMap.put("key2", 12345);
        hashMap.remove("key");
        assertEquals(2, hashMap.size());
    }

    @Test
    public void removeExistedElementCheckReturnedValue() {
        hashMap.put("key", 1234);
        hashMap.put("key3", 1234);
        hashMap.put("key2", 12345);
        assertEquals(1234, hashMap.remove("key"));
    }

    @Test
    public void removeNotExistedElementShouldBeSameSize() {
        hashMap.put("key", 1234);
        hashMap.put("key3", 1234);
        hashMap.put("key2", 12345);
        assertEquals(3, hashMap.size());
    }

    @Test
    public void removeNotExistedElementShouldReturnNull() {
        hashMap.put("key4", 1234);
        hashMap.put("key3", 1234);
        hashMap.put("key2", 12345);
        assertEquals(null, hashMap.remove("key"));
    }

    @Test
    public void removeNotExistedElementWithSameHashShouldReturnNull() {
        hashMap.put("first", 1234);
        assertEquals(null, hashMap.remove(null));
    }

    @Test
    public void removeElementFromEmptyArray() {
        assertEquals(null, hashMap.remove("ababa"));
    }

    @Test
    public void increaseCapacityCheckCapacityAfterIncrease() {
        hashMap = new MyHashMap<>(2);
        hashMap.put("6", 1234);
        hashMap.put("2", 1234);
        hashMap.put("4", 12345);
        assertEquals(4, ((MyHashMap) hashMap).getCapacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMapWithNegativeCapacityIllegalArgumentException() {
        hashMap = new MyHashMap<>(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMapWithNegativeLoadFactorIllegalArgumentException() {
        hashMap = new MyHashMap<>(1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMapWithZeroCapacityIllegalArgumentException() {
        hashMap = new MyHashMap<>(0);
    }

    @Test
    public void iteratorWhileIterateShouldBeTheSameAsToArray() {
        String[] keys = { "New String1", "One", "blablabla", "Hello World",
                "HelloJava", "Two", "Test1" };
        for (int i = 0; i < keys.length; i++) {
            hashMap.put(keys[i], null);
        }
        Iterator<? extends Entry<String, Object>> iter = hashMap.entryIterator();
        String[] res = new String[keys.length];
        int index = 0;
        while (iter.hasNext()) {
            res[index++] = iter.next().getKey();
        }
        assertArrayEquals(keys, res);
    }
    
    
    @Test(expected = NoSuchElementException.class)
    public void iteratorNotEmptyMap() {
        hashMap.put("element", null);
        Iterator<? extends Entry<String, Object>> iter = hashMap.entryIterator();
        while (iter.hasNext()) {
            iter.next();
        }
        iter.next();
    }
    
    @Test(expected = NoSuchElementException.class)
    public void iteratorNextEmptyListNoSuchElementException() {
        hashMap.entryIterator().next();
    }
    
     @Test
    public void iteratorRemoveRemoveAllElementsSizeShouldBeZero() {
        String[] keys = { "New String1", "One", "blablabla", "Hello World",
                "HelloJava", "Two", "Test1" };
        for (int i = 0; i < keys.length; i++) {
            hashMap.put(keys[i], i);
        }
        Iterator<? extends Entry<String, Object>> iter = hashMap
                .entryIterator();
        try {
            while (iter.hasNext()) {
                iter.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(0, hashMap.size());
    }

    @Test
    public void iteratorRevoveRemoveCurrentElementCheckSize() {
        String[] keys = { "New String1", "One", "blablabla", "Hello World",
                "HelloJava", "Two", "Test1" };
        for (int i = 0; i < keys.length; i++) {
            hashMap.put(keys[i], i);
        }
        Iterator<? extends Entry<String, Object>> iter = hashMap
                .entryIterator();
        iter.next();
        iter.remove();
        assertEquals(keys.length - 1, hashMap.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorRemoveEmptyMapNoSuchElementException() {
        hashMap.entryIterator().remove();
    }
}
