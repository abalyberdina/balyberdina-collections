package com.epam.mylist.myarraylist;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.epam.mylist.MyIndexOutOfBoundsException;
import com.epam.mylist.MyList;
import com.epam.mylist.myarraylist.MyArrayList;
import com.epam.mylist.mylinkedlist.MyLinkedList;

public class MyArrayListTest {
    MyArrayList<Integer> mal;

    @Before
    public void init() {
        mal = new MyArrayList<>();
    }

    @Test
    public void sizeAddFewElementsCheckSize() {
        mal.add(1);
        mal.add(2);
        mal.add(3);
        assertEquals(3, mal.size());
    }

    @Test
    public void sizeEmptyArrayShouldBeZero() {
        assertEquals(0, mal.size());
    }

    @Test
    public void toArrayAddFewElementsCheckEquality() {
        mal.add(12);
        mal.add(1);
        mal.add(22);
        assertArrayEquals(mal.toArray(), new Integer[] { 12, 1, 22 });
    }

    @Test
    public void toArrayEmptyArrayShouldReturnNull() {
        mal.add(12);
        mal.add(1);
        mal.add(22);
        assertArrayEquals(mal.toArray(), new Integer[] { 12, 1, 22 });
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void addAtIndexNegativeIndexMyIndexOutOfBoundsException() {
        mal.add(-1, null);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void addAtTooBigIndexIndexMyIndexOutOfBoundsException() {
        mal.add(1);
        mal.add(3, 3);
    }

    @Test
    public void addAtLastIndexCheckEquality() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        for (Integer i : ints) {
            mal.add(i);
        }
        mal.add(6, 6);
        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5, 7, 6 }, mal.toArray());
    }

    @Test
    public void addAtCorrectIndexCheckEquality() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        for (Integer i : ints) {
            mal.add(i);
        }
        mal.add(5, 6);
        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5, 6, 7 }, mal.toArray());
    }

    @Test
    public void addAllCheckSize() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        mal.add(5, 6);
        assertEquals(mal.size(), 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAllNullValueIllegalArgumentException() {
        mal.addAll(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAllAtIndexNullValueIllegalArgumentException() {
        mal.addAll(0, null);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void addAllAtIndexNegativeIndexMyIndexOutOfBound() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(-1, ints);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void addAllAtIndexIndexTooBigMyIndexOutOfBound() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(2, ints);
    }

    @Test
    public void addAllAtLastIndexCheckEquality() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        mal.addAll(ints.length, ints);
        Integer[] res = new Integer[ints.length * 2];
        System.arraycopy(ints, 0, res, 0, ints.length);
        System.arraycopy(ints, 0, res, ints.length, ints.length);
        assertArrayEquals(res, mal.toArray());
    }

    @Test
    public void addAllAtMiddleIndexCheckEquality() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        mal.addAll(2, ints);
        assertArrayEquals(new Integer[] { 1, 2, 1, 2, 3, 4, 5, 7, 3, 4, 5, 7 },
                mal.toArray());
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void getIndexNegativeIndexIndexIllegalArgumentException() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        mal.get(-1);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void getIndexWithTooBigIndexIllegalArgumentException() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        mal.get(11);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void getIndexEmptyArrayIllegalArgumentException() {
        mal.get(11);
    }

    @Test
    public void getIndexCorrectArgumentCheckEquality() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        assertEquals(ints[1], mal.get(1));
    }

    @Test
    public void removeFirstNotEmptyArray() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        mal.remove(0);
        assertEquals(mal.get(0), ints[1]);
    }

    @Test
    public void removeLastNotEmptyArray() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        mal.remove(5);
        assertEquals(mal.get(4), ints[4]);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void removeEmptyArrayIllegalArgumentException() {
        mal.remove(0);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void removeIndexTooBigIllegalArgumentException() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        mal.remove(6);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void removeNegativeIndexIllegalArgumentException() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        mal.remove(-4);
    }

    @Test
    public void removeIndexInMiddleCheckEquality() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        mal.remove(4);
        mal.remove(1);
        assertArrayEquals(mal.toArray(), new Integer[] { 1, 3, 4, 7 });
    }

    @Test
    public void removeIndexInMiddleCheckSize() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        mal.remove(4);
        mal.remove(1);
        assertEquals(4, mal.size());
    }

    @Test
    public void clearEmptyArray() {
        mal.clear();
        assertArrayEquals(mal.toArray(), new Integer[] {});
    }

    @Test
    public void clearNotEmptyArray() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        mal.clear();
        assertEquals(mal.size(), 0);
    }

    @Test
    public void isEmptyEmptyArrayReturnTrue() {
        assertEquals(mal.isEmpty(), true);
    }

    @Test
    public void isEmptyNotEmptyArrayReturnFalse() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        assertEquals(mal.isEmpty(), false);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void setNegativeIndexIllegalArgumentException() {
        mal.set(-4, new Integer(Integer.MAX_VALUE));
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void setIndexIsTooBigIllegalArgumentException() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        mal.set(7, 42);
    }

    @Test
    public void setIndexIsCorrectCheckEquality() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        mal.set(4, Integer.MAX_VALUE);
        ints[4] = Integer.MAX_VALUE;
        assertArrayEquals(mal.toArray(), ints);
    }

    @Test
    public void indexOfNotExistentValueReturnsMinusOne() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        assertEquals(-1, mal.indexOf(123456));
    }

    @Test
    public void indexOfExistentValueReturnsIndex() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        assertEquals(3, mal.indexOf(4));
    }

    @Test
    public void iteratorWhileIterateShouldBeTheSameAsToArray() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.addAll(ints);
        Iterator<Integer> iter = mal.iterator();
        Integer[] res = new Integer[ints.length];
        int index = 0;
        while (iter.hasNext()) {
            res[index++] = iter.next();
        }
        assertArrayEquals(ints, res);
    }
    
    @Test(expected = NoSuchElementException.class)
    public void iteratorNextEmptyListNoSuchElementException() {
        mal.iterator().next();
    }

    @Test
    public void iteratorRemoveShouldRemoveAll() {
        int size = 5;
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            mal.add(random.nextInt());
        }
        Iterator<Integer> iter = mal.iterator();
        while (iter.hasNext()) {
            iter.remove();
        }
        assertEquals(mal.size(), 0);
    }

    @Test
    public void tryIncreaseCapacityMinCapacityUnknownShouldGrown() {
        int capacity = mal.getCapacity();
        Random random = new Random();
        while (mal.getCapacity() <= capacity) {
            mal.add(random.nextInt());
        }
        assertEquals(20, mal.getCapacity());
    }

    @Test
    public void tryIncreaseCapacityCustomCapacityShouldGrown() {
        int capacity = 4;
        mal = new MyArrayList<Integer>(capacity);
        Random random = new Random();
        while (mal.getCapacity() <= capacity) {
            mal.add(random.nextInt());
        }
        assertEquals(8, mal.getCapacity());
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void ensureCapacityCapacityTooSmallMyIndexOutOfBoundsException() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            mal.add(random.nextInt());
        }
        mal.ensureCapacity(2);
    }

    @Test
    public void ensureCapacityCapacityIsCorrectAndSmallerCheckEquality() {
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            mal.add(random.nextInt());
        }
        mal.ensureCapacity(15);
        assertEquals(15, mal.getCapacity());
    }

    @Test
    public void ensureCapacityCapacityIsCorrectAndBiggerCheckEquality() {
        mal.ensureCapacity(15);
        assertEquals(15, mal.getCapacity());
    }

    @Test
    public void trimToSizeEmptyArrayCapacityShouldBeZero() {
        mal.trimToSize();
        assertEquals(0, mal.getCapacity());
    }

    @Test
    public void trimToSizeEmptyArrayAddElementsAfterTrimShouldWorkCorrect() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        mal.trimToSize();
        mal.addAll(ints);
        assertArrayEquals(ints, mal.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNotEmptyListNullValueSentIllegalArgumentException() {
        mal = new MyArrayList<Integer>(null);
    }

    @Test
    public void createNotEmptyListArrayListSentCheckEquality() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        MyList<Integer> temp = new MyArrayList<>();
        temp.addAll(ints);
        mal = new MyArrayList<>(temp);
        assertArrayEquals(ints, mal.toArray());
    }

    @Test
    public void createNotEmptyListLinkedListSentCheckEquality() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4, 5, 7 };
        MyList<Integer> temp = new MyLinkedList<>();
        temp.addAll(ints);
        mal = new MyArrayList<>(temp);
        assertArrayEquals(ints, mal.toArray());
    }
}
