package com.epam.mylist;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.epam.mylist.MyCollections;
import com.epam.mylist.MyIndexOutOfBoundsException;
import com.epam.mylist.MyList;
import com.epam.mylist.ReverseComparator;
import com.epam.mylist.myarraylist.MyArrayList;
import com.epam.mylist.mylinkedlist.MyLinkedList;

public class MyCollectionTest {

    MyArrayList<Integer> arrList;
    MyList<Integer> linkedList;

    @Before
    public void init() {
	arrList = new MyArrayList<>();
	linkedList = new MyLinkedList<>();
    }

    @Test
    public void binarySearchSearchInNotEmptyArrayWrongKeyReturnMinusOne() {
	Integer[] arr = new Integer[] { 16, 2, 8, 12, 1 };
	Arrays.sort(arr);
	arrList.addAll(arr);
	int result = MyCollections.binarySearch(arrList, new Integer(3));
	assertEquals(result, -3);
    }

    @Test
    public void binarySearchSearchInNotEmptyArrayCorrectKeyCheckPosition() {
	Integer[] arr = new Integer[] { 16, 2, 8, 12, 1 };
	Arrays.sort(arr);
	arrList.addAll(arr);
	int result = MyCollections.binarySearch(arrList, new Integer(8));
	assertEquals(result, 2);
    }

    @Test
    public void binarySearchSearchInNotEmptyArrayKeyBiggerThanMaxElementReturnMinusOne() {
	Integer[] arr = new Integer[] { 16, 2, 8, 12, 1 };
	Arrays.sort(arr);
	arrList.addAll(arr);
	int result = MyCollections.binarySearch(arrList, new Integer(20));
	assertEquals(result, -6);
    }

    @Test
    public void binarySearchSearchInNotEmptyArrayKeySmallerThanMaxElementReturnMinusOne() {
	Integer[] arr = new Integer[] { 16, 2, 8, 12, 1 };
	Arrays.sort(arr);
	arrList.addAll(arr);
	int result = MyCollections.binarySearch(arrList, new Integer(0));
	assertEquals(result, -1);
    }

    @Test
    public void binarySearchTrySearchInEmptyArrayReturnMinusOne() {
	int result = MyCollections.binarySearch(arrList, new Integer(3));
	assertEquals(result, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void binarySearchNullValueSentAsArrayIllegalArgumentException() {
	MyCollections.binarySearch(null, new Integer(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void binarySearchNullValueSentAsKeyIllegalArgumentException() {
	MyCollections.binarySearch(arrList, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void binarySearchWrongCollectionSentIllegalArgumentException() {
	MyCollections.binarySearch(new ArrayList<Integer>(), new Integer(15));
    }

    @Test
    public void reverseCorrectArraySentCheckEquality() {
	Integer[] arr = new Integer[] { 1, 2, 6, 7, 8 };
	linkedList.addAll(arr);
	MyCollections.reverse(linkedList);

	assertArrayEquals(linkedList.toArray(), new Integer[] { 8, 7, 6, 2, 1 });
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseNullArrayIllegalArgumentException() {
	MyCollections.reverse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void copyNullArrayDestignationIllegalArgumentException() {
	MyCollections.copy(null, linkedList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void copyNullArraySourceIllegalArgumentException() {
	MyCollections.copy(linkedList, null);
    }

    @Test
    public void copyFromMyLinkedToMyArrayListWhichIsEmptyCheckEquality() {
	Random random = new Random();
	int size = 9;
	for (int i = 0; i < size; i++) {
	    linkedList.add(random.nextInt());
	}
	MyCollections.copy(arrList, linkedList);
	assertArrayEquals(linkedList.toArray(), arrList.toArray());
    }

    @Test
    public void copyFromMyLinkedToMyArrayListWhichIsNotEmptyCheckEquality() {
	Random random = new Random();
	int size = 9;
	for (int i = 0; i < size; i++) {
	    linkedList.add(random.nextInt());
	    arrList.add(random.nextInt());
	}
	MyCollections.copy(arrList, linkedList);
	assertArrayEquals(linkedList.toArray(), arrList.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void swapNullArrayIllegalArgumentException() {
	MyCollections.swap(null, 0, 1);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void swapNegativeFirstIndexMyIndexOutOfBoundsException() {
	MyCollections.swap(linkedList, -1, 0);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void swapTooBigFirstIndexMyIndexOutOfBoundsException() {
	MyCollections.swap(arrList, 1, 0);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void swapNegativeSecondIndexMyIndexOutOfBoundsException() {
	linkedList.add(new Integer(1));
	MyCollections.swap(linkedList, 0, -2);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void swapTooBigSecondIndexMyIndexOutOfBoundsException() {
	linkedList.add(new Integer(1));
	MyCollections.swap(linkedList, 0, 4);
    }

    @Test
    public void swapCorrectDataCheckEquality() {
	Integer[] args = new Integer[] { 1, 2, 5, 4, 3, 6 };
	linkedList.addAll(args);
	MyCollections.swap(linkedList, 2, 3);
	MyCollections.swap(linkedList, 2, 4);
	assertArrayEquals(new Integer[] { 1, 2, 3, 5, 4, 6 },
		linkedList.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void sortNullListIllegalArgumentException() {
	MyCollections.sort(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sortWithComparatorNullListIllegalArgumentException() {
	MyCollections.sort(null, new ReverseComparator<Integer>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void sortWithComparatorNullComparatorIllegalArgumentException() {
	MyCollections.sort(arrList, null);
    }

    @Test
    public void sortRandomAccessObjectCorrectValueCheckEquality() {
	int size = 10;
	Integer[] args = new Integer[10];
	Random random = new Random();
	for (int i = 0; i < size; i++) {
	    args[i] = random.nextInt();
	}
	arrList.addAll(args);
	MyCollections.sort(arrList);
	Arrays.sort(args);
	assertArrayEquals(args, arrList.toArray());
    }

    @Test
    public void sortNotRandomAccessObjectCorrectValueCheckEquality() {
	int size = 10;
	Integer[] args = new Integer[10];
	Random random = new Random();
	for (int i = 0; i < size; i++) {
	    args[i] = random.nextInt();
	}
	linkedList.addAll(args);
	MyCollections.sort(linkedList);
	Arrays.sort(args);
	assertArrayEquals(args, linkedList.toArray());
    }
    
    @Test
    public void sortWithComparatorCorrectValuesCheckEquality(){
	int size = 10;
	Integer[] args = new Integer[10];
	Random random = new Random();
	for (int i = 0; i < size; i++) {
	    args[i] = random.nextInt();
	}
	linkedList.addAll(args);
	(new MyCollections()).sort(linkedList, new ReverseComparator<Integer>());
	Arrays.sort(args);
	MyCollections.reverse(linkedList);
	assertArrayEquals(args, linkedList.toArray());
    }
 
}
