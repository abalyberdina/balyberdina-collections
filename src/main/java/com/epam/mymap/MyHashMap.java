package com.epam.mymap;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import com.epam.mylist.MyList;
import com.epam.mylist.myarraylist.MyArrayList;
import com.epam.mylist.mylinkedlist.MyLinkedList;

public class MyHashMap<K, V> implements MyMap<K, V> {
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private MyArrayList<MyLinkedList<SimpleEntry<K, V>>> hashMap;
    private int capacity = DEFAULT_INITIAL_CAPACITY;
    private float loadFactor = DEFAULT_LOAD_FACTOR;
    private int size = 0;

    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY);
        // - constructs an empty HashMap with the default initial capacity (16)
        // and the default load factor (0.75).
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
        // - constructs an empty HashMap with the specified initial capacity and
        // the default load factor (0.75). Throws: IllegalArgumentException - if
        // the initial capacity is negative.
    }

    public MyHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0 || loadFactor <= 0) {
            throw new IllegalArgumentException();
        }
        hashMap = new MyArrayList<MyLinkedList<SimpleEntry<K, V>>>(
                initialCapacity);
        capacity = initialCapacity;
        this.loadFactor = loadFactor;
        // - constructs an empty HashMap with the specified initial capacity and
        // load factor. Throws: IllegalArgumentException - if the initial
        // capacity is negative or the load factor is nonpositive.
    }

    @Override
    public V put(K key, V value) {
        SimpleEntry<K, V> se = null;
        int index = getPosition(key);
        MyList<SimpleEntry<K, V>> ml;
        if (hashMap.size() == 0) {
            for (int i = 0; i < capacity; i++) {
                hashMap.add(new MyLinkedList<SimpleEntry<K, V>>());
            }
        }
        if (hashMap.get(index).size() != 0) {
            ml = hashMap.get(index);
            for (int i = 0; i < ml.size(); i++) {
                if (Objects.equals(ml.get(i).getKey(), key)) {
                    V temp = ml.get(i).getValue();
                    ml.get(i).setValue(value);
                    return temp;
                }
            }
        }
        tryIncreaseCapacity();
        se = new SimpleEntry<>(key, value);
        hashMap.get(index).add(se);
        size++;
        return value;
    }

    @Override
    public V get(Object key) {
        int index = getPosition((K) key);
        MyList<SimpleEntry<K, V>> ml;
        if (hashMap.size() != 0 && hashMap.get(index).size() != 0) {
            ml = hashMap.get(index);
            for (int i = 0; i < ml.size(); i++) {
                if (Objects.equals(ml.get(i).getKey(), key)) {
                    return ml.get(i).getValue();
                }
            }
        }
        return null;
    }

    @Override
    public void clear() {
        if (!isEmpty()) {
            for (int i = 0; i < capacity; i++) {
                hashMap.get(i).clear();
            }
            hashMap.clear();
            size = 0;
        }
    }

    @Override
    public boolean containsKey(Object key) {
        int index = getPosition((K) key);
        MyList<SimpleEntry<K, V>> ml;
        if (hashMap.size() != 0 && hashMap.get(index).size() != 0) {
            ml = hashMap.get(index);
            for (int i = 0; i < ml.size(); i++) {
                if (Objects.equals(ml.get(i).getKey(), key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        MyList<SimpleEntry<K, V>> ml;
        if (size != 0) {
            for (int i = 0; i < capacity; i++) {
                ml = hashMap.get(i);
                for (int j = 0; j < ml.size(); j++) {
                    if (Objects.equals(ml.get(j).getValue(), value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V remove(Object key) {
        int index = getPosition((K) key);
        MyList<SimpleEntry<K, V>> ml;
        if (hashMap.size() != 0 && hashMap.get(index).size() != 0) {
            ml = hashMap.get(index);
            for (int i = 0; i < ml.size(); i++) {
                if (Objects.equals(ml.get(i).getKey(), key)) {
                    V temp = ml.get(i).getValue();
                    ml.remove(i);
                    size--;
                    return temp;
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<SimpleEntry<K, V>> entryIterator() {
        return new MyIterator();
    }

    public int getCapacity() {
        return capacity;
    }

    private int getPosition(K key) {
        return getHash(key) % capacity;
    }

    private int getPosition(K key, int newCapacity) {
        return getHash(key) % newCapacity;
    }

    private int getHash(K key) {
        if (key == null) {
            return 0;
        }
        return Math.abs(key.hashCode());
    }

    private void tryIncreaseCapacity() {
        if (size > loadFactor * capacity) {
            increaseCapacity();
        }
    }

    private void increaseCapacity() {
        int newCapacity = capacity * 2;
        transformMap(newCapacity);
    }

    private void transformMap(int newCapacity) {
        MyArrayList<MyLinkedList<SimpleEntry<K, V>>> temp =
                new MyArrayList<MyLinkedList<SimpleEntry<K, V>>>(
                newCapacity);
        for (int i = 0; i < newCapacity; i++) {
            temp.add(new MyLinkedList<SimpleEntry<K, V>>());
        }
        MyLinkedList<SimpleEntry<K, V>> mll = null;
        SimpleEntry<K, V> se = null;
        int index = -1;
        for (int i = 0; i < capacity; i++) {
            if (hashMap.get(i).size() != 0) {
                mll = hashMap.get(i);
                while (!mll.isEmpty()) {
                    se = mll.removeLast();
                    index = getPosition(se.getKey(), newCapacity);
                    temp.get(index).add(se);
                }
            }
        }
        hashMap = temp;
        capacity = newCapacity;
    }

    private class SimpleEntry<K, V> implements MyMap.Entry<K, V> {
        private K key;
        private V value;

        public SimpleEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public Object setValue(Object newValue) {
            this.value = (V) newValue;
            return newValue;
        }

    }

    private class MyIterator implements Iterator<SimpleEntry<K, V>> {
        private int count = 0;
        private int positionInArrayList = 0;
        private Iterator<SimpleEntry<K, V>> iterator = null;

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public SimpleEntry<K, V> next() {
            SimpleEntry<K, V> result = null;
            if (size != 0) {
                for (; positionInArrayList < capacity; positionInArrayList++) {

                    if (hashMap.get(positionInArrayList).size() != 0) {
                        if (iterator == null) {
                            iterator = hashMap.get(positionInArrayList)
                                    .iterator();
                        } else if (!iterator.hasNext()) {
                            iterator = null;
                            continue;
                        }
                        count++;
                        result = iterator.next();
                        return result;
                    }
                }

            }
            throw new NoSuchElementException();
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
