package com.epam.mylist.myarraylist;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;

import com.epam.mylist.MyIndexOutOfBoundsException;
import com.epam.mylist.MyList;

public class MyArrayList<Type> implements MyList<Type>, RandomAccess {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] list;
    private int size = 0;
    private int minCapacity = -1;

    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public MyArrayList(MyList<Type> c) {
        this();
        if (c == null) {
            throw new IllegalArgumentException();
        }
        addAll(c.toArray());
        minCapacity = c.size();
    }

    public MyArrayList(int initialCapacity) {
        list = new Object[initialCapacity];
        this.minCapacity = initialCapacity;
    }

    public void ensureCapacity(int minCap) {
        if (minCap < size) {
            throw new MyIndexOutOfBoundsException();
        }
        Object[] temp = new Object[minCap];
        System.arraycopy(list, 0, temp, 0, size);
        list = temp;
        this.minCapacity = minCap;
    }

    public void trimToSize() {
        Object[] temp = new Object[size];
        System.arraycopy(list, 0, temp, 0, size);
        list = temp;
    }

    @Override
    public void add(Type e) {
        tryIncreaseCapacity();
        list[size++] = e;
    }

    @Override
    public void add(int index, Type e) {
        tryIncreaseCapacity();
        if (index < 0 || index > size) {
            throw new MyIndexOutOfBoundsException();
        }
        if (index == size) {
            list[size++] = e;
            return;
        }
        System.arraycopy(list, index, list, index + 1, size - index);
        list[index] = e;
        size++;
    }

    @Override
    public void addAll(Type[] c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        if (size + c.length > list.length) {
            increaseCapacity();
        }
        System.arraycopy(c, 0, list, size, c.length);
        size += c.length;
    }

    @Override
    public void addAll(int index, Type[] c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        if (index < 0 || index > size) {
            throw new MyIndexOutOfBoundsException();
        }
        if (size + c.length > list.length) {
            increaseCapacity();
        }
        if (index != size) {
            System.arraycopy(list, index, list, index + c.length, size - index);
        }
        System.arraycopy(c, 0, list, index, c.length);
        size += c.length;
    }

    @Override
    public Type get(int index) {
        if (index < 0 || index >= size) {
            throw new MyIndexOutOfBoundsException();
        }
        return (Type) list[index];
    }

    @Override
    public Type remove(int index) {
        if (index < 0 || index >= size) {
            throw new MyIndexOutOfBoundsException();
        }
        Type temp = (Type) list[index];
        list[index] = null;
        if (index != size - 1) {
            System.arraycopy(list, index + 1, list, index, size - index - 1);
        }
        size--;
        return temp;
    }

    @Override
    public void clear() {
        for (int i = size - 1; i >= 0; i--) {
            remove(i);
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void set(int index, Type e) {
        if (index < 0 || index >= size) {
            throw new MyIndexOutOfBoundsException();
        }
        list[index] = e;

    }

    @Override
    public int indexOf(Type o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(list[i], o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Type[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = list[i];
        }
        return (Type[]) result;
    }

    @Override
    public Iterator<Type> iterator() {
        return new MyIterator();
    }

    public int getCapacity() {
        return list.length;
    }

    private void tryIncreaseCapacity() {
        if (size == list.length) {
            increaseCapacity();
        }
    }

    private void increaseCapacity() {
        Object[] temp;
        if (list.length == 0) {
            temp = new Object[DEFAULT_CAPACITY];
        } else {
            temp = new Object[size * 2];
        }
        System.arraycopy(list, 0, temp, 0, size);
        list = temp;
    }

    private class MyIterator implements Iterator<Type> {
        private int position = 0;

        @Override
        public boolean hasNext() {
            if (position < size) {
                return true;
            }
            return false;
        }

        @Override
        public Type next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Type data = get(position++);
            return data;
        }

        @Override
        public void remove() {
            MyArrayList.this.remove(position);
        }

    }

}
