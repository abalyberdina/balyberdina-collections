package com.epam.mymap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import com.epam.mylist.ReverseComparator;
import com.epam.mymap.MyHashMap;
import com.epam.mymap.MyMap;
import com.epam.mymap.MyTreeMap;
import com.epam.mymap.MyMap.Entry;

public class MyTreeMapTest {
    MyMap<String, Object> treeMap;

    @Before
    public void init() {
        treeMap = new MyTreeMap<>();
    }

    @Test
    public void sizeAddingElementCheckValue() {
        treeMap.put("first", 10);
        treeMap.put("second", null);
        treeMap.put("third", -1);
        assertEquals(3, treeMap.size());
    }

    @Test
    public void putNullKeyCheckSize() {
        treeMap.put(null, new Integer(12));
        assertEquals(1, treeMap.size());
    }

    @Test
    public void putFewValuesToOneKeyCheckSize() {
        treeMap.put("first", 13);
        treeMap.put("first", 1);
        assertEquals(1, treeMap.size());
    }

    @Test
    public void putFewValuesToOneKeyCheckResultValue() {
        treeMap.put("Element", null);
        treeMap.put("Element", 12);
        assertEquals(12, treeMap.get("Element"));
    }

    @Test
    public void getNotExistedKeyShouldReturnNull() {
        treeMap.put("only", 12);
        assertNull(treeMap.get("first"));
    }

    @Test
    public void getExistedKeyCheckValues() {
        Integer value = 155;
        String key = "key";
        treeMap.put(key, value);
        assertEquals(value, treeMap.get(key));
    }

    @Test
    public void getFromEmptyMapShouldReturnNull() {
        assertNull(treeMap.get("Whatever"));
    }

    @Test
    public void isEmptyEmptyMapShouldBeTrue() {
        assertTrue(treeMap.isEmpty());
    }

    @Test
    public void isEmptyNotEmptyMapShouldBeFalse() {
        treeMap.put("key", 1);
        assertFalse(treeMap.isEmpty());
    }

    @Test
    public void clearEmptyArrayCheckSize() {
        treeMap.clear();
        assertEquals(0, treeMap.size());
    }

    @Test
    public void clearNotEmptyArrayCheckSize() {

        treeMap.clear();
        treeMap.put("key", 1234);
        treeMap.put("key1", 1234);
        treeMap.put("key2", 12345);
        treeMap.put("key3", 12346);
        treeMap.clear();
        assertEquals(0, treeMap.size());
    }

    @Test
    public void clearNotEmptyArrayTryGetDeletedElement() {
        treeMap.put("key", 1234);
        treeMap.put("key3", 1234);
        treeMap.put("key2", 12345);
        treeMap.put("key1", 12346);
        treeMap.clear();
        Object result = treeMap.get("key");
        assertEquals(null, result);
    }

    @Test
    public void containsKeyExistedKeyShouldBeTrue() {
        treeMap.put("key", 1234);
        treeMap.put("key3", 1234);
        treeMap.put("key2", 12345);
        assertTrue(treeMap.containsKey("key2"));
    }

    @Test
    public void containsKeyNotExistedKeyShouldBeFalse() {
        treeMap.put("key", 1234);
        treeMap.put("key3", 1234);
        treeMap.put("key2", 12345);
        assertFalse(treeMap.containsKey("key223"));
    }

    @Test
    public void containsKeyEmptyMapShouldBeFalse() {
        assertFalse(treeMap.containsKey("aa"));
    }

    @Test
    public void containsValueNotExistedValueReturnFalse() {
        treeMap.put("key", 1234);
        treeMap.put("key3", 1234);
        treeMap.put("key2", 12345);
        assertFalse(treeMap.containsValue(12));
    }

    @Test
    public void containsValueExistedValueReturnTrue() {
        treeMap.put("key", 1234);
        treeMap.put("key3", 1234);
        treeMap.put("key2", 12345);
        assertTrue(treeMap.containsValue(1234));
    }

    @Test
    public void containsValueEmptyArrayReturnFalse() {
        assertFalse(treeMap.containsValue(1234));
    }

    @Test
    public void removeExistedElementCheckSize() {
        treeMap.put("key", 1234);
        treeMap.put("key3", 1234);
        treeMap.put("key2", 12345);
        treeMap.remove("key");
        assertEquals(2, treeMap.size());
    }

    @Test
    public void removeExistedElementCheckReturnedValue() {
        treeMap.put("key", 1234);
        treeMap.put("key3", 1234);
        treeMap.put("key2", 12345);
        assertEquals(1234, treeMap.remove("key"));
    }

    @Test
    public void removeNotExistedElementShouldBeSameSize() {
        treeMap.put("key", 1234);
        treeMap.put("key3", 1234);
        treeMap.put("key2", 12345);
        assertEquals(3, treeMap.size());
    }

    @Test
    public void removeNotExistedElementShouldReturnNull() {
        treeMap.put("key4", 1234);
        treeMap.put("key3", 1234);
        treeMap.put("key2", 12345);
        assertEquals(null, treeMap.remove("key"));
    }

    @Test
    public void removeElementFromEmptyArray() {
        assertEquals(null, treeMap.remove("ababa"));
    }

    @Test
    public void increaseCapacityCheckCapacityAfterIncrease() {
        treeMap = new MyHashMap<>(2);
        treeMap.put("6", 1234);
        treeMap.put("2", 1234);
        treeMap.put("4", 12345);
        assertEquals(4, ((MyHashMap) treeMap).getCapacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMapWithNegativeCapacityIllegalArgumentException() {
        treeMap = new MyHashMap<>(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMapWithNegativeLoadFactorIllegalArgumentException() {
        treeMap = new MyHashMap<>(1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMapWithZeroCapacityIllegalArgumentException() {
        treeMap = new MyHashMap<>(0);
    }

    @Test
    public void iteratorWhileIterateShouldBeTheSameAsToArray() {
        String[] keys = { "New String1", "One", "blablabla", "Hello World",
                "HelloJava", "Two", "Test1" };
        for (int i = 0; i < keys.length; i++) {
            treeMap.put(keys[i], null);
        }
        Iterator<? extends Entry<String, Object>> iter = treeMap.entryIterator();
        String[] res = new String[keys.length];
        int index = 0;
        while (iter.hasNext()) {
            res[index++] = iter.next().getKey();
        }
        Arrays.sort(keys);
        assertArrayEquals(keys,res);
    }
    
    @Test(expected = NoSuchElementException.class)
    public void iteratorEmptyMapShouldReturnNull() {
        assertNull(treeMap.entryIterator().next());
    }
    
    @Test(expected = NoSuchElementException.class)
    public void iteratorNotEmptyMap() {
        treeMap.put("element", null);
        Iterator<? extends Entry<String, Object>> iter = treeMap.entryIterator();
        while (iter.hasNext()) {
            iter.next();
        }
        iter.next();
    }
    
    @Test
    public void removeAllMapFilledWithManyElements() {
        String[] s = new String[] {"19", "0", "8", "7", "10", "14", "2", "13", "4", "5", "1", 
                "9", "12", "3", "11", "6", "17", "16", "18", "15"};
        
        for(int i = 0; i < s.length; i++) {
            treeMap.put(s[i], i);
        }
        for(int i = 0; i < s.length; i++) {
            treeMap.remove(s[i]);
        }
        assertEquals(0, treeMap.size());
    }
    
    @Test
    public void treeMapWithReverseComparator() {
        Comparator<String> rc = new ReverseComparator<String>();
        treeMap = new MyTreeMap<>(rc);
        String[] keys = { "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (int i = 0; i < keys.length; i++) {
            treeMap.put(keys[i], null);
        }
        Iterator<? extends Entry<String, Object>> iter = treeMap.entryIterator();
        String[] res = new String[keys.length];
        int index = 0;
        while (iter.hasNext()) {
            res[index++] = iter.next().getKey();
        }
        Arrays.sort(keys, rc);
        assertArrayEquals(keys,res);
    }
}
