package com.epam.mylist.mylinkedlist;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import com.epam.mylist.MyIndexOutOfBoundsException;
import com.epam.mylist.mylinkedlist.MyLinkedList;

public class MyLinkedListTest {

    MyLinkedList<String> mll;

    @Before
    public void initialize() {
        mll = new MyLinkedList<>();
    }

    @Test
    public void addFirstGetFirstEmptyListCheckEquality() {
        String element = "Test 1";
        mll.addFirst(element);
        assertEquals(element, mll.getFirst());
    }

    @Test
    public void addLastGetLastEmptyListCheckEquality() {
        String element = "Test 2";
        mll.addLast(element);
        assertEquals(element, mll.getLast());
    }

    @Test
    public void addFirstGetLastEmptyListCheckEquality() {
        String element = "Test 3";
        mll.addFirst(element);
        assertEquals(element, mll.getLast());
    }

    @Test
    public void addLastGetFirstEmptyListCheckEquality() {
        String element = "Test 4";
        mll.addLast(element);
        assertEquals(element, mll.getFirst());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeFirstInEmptyArrayCatchException() {
        mll.removeFirst();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeLastInEmptyArrayCatchException() {
        mll.removeLast();
    }

    @Test
    public void addFirstNotEmptyArrayAddInRightPlace() {
        String[] args = { "Test1", "blablabla", "123" };
        for (int i = 0; i < args.length; i++) {
            mll.addFirst(args[i]);
        }
        assertEquals(args[2], mll.getFirst());
    }

    @Test
    public void addLastNotEmptyArrayAddInRightPlace() {
        String[] args = { "Test1", "blablabla", "123" };
        for (int i = 0; i < args.length; i++) {
            mll.addLast(args[i]);
        }
        assertEquals(args[2], mll.getLast());
    }

    @Test
    public void addAllEmptyArrayAddInRightOrder() {
        String[] args = { "Test1", "blablabla" };
        mll.addAll(args);
        String[] res = new String[args.length];
        res[0] = mll.getFirst();
        res[1] = mll.getLast();
        assertArrayEquals(args, res);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAllEmptyArgumentIllegalArgumentException() {
        mll.addAll(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAllInIndexEmptyArgumentIllegalArgumentException() {
        mll.addAll(0, null);
    }

    @Test
    public void sizeEmptyArrayEqualsToZero() {
        assertEquals(0, mll.size());
    }

    @Test
    public void sizeNotEmptyArrayEqualsToNumOfElements() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(args);
        assertEquals(args.length, mll.size());
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void getFirstEmptyArrayReturnsNull() {
        assertNull(mll.getFirst());
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void getLastEmptyArrayReturnsNull() {
        assertNull(mll.getLast());
    }

    @Test
    public void sizeNotEmptyArrayCheckEquality() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(args);
        assertEquals(args.length, mll.size());
    }

    @Test
    public void toArrayNotEmptyCheckEquality() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(args);
        String[] res = mll.toArray();

        assertArrayEquals(args, res);
    }

    @Test
    public void toArrayEmptyArrayReturnsNull() {
        assertNull(mll.toArray());
    }

    @Test
    public void removeFirstNotEmptyArray() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(args);
        mll.removeFirst();
        assertEquals(mll.getFirst(), args[1]);
    }

    @Test
    public void removeLastNotEmptyArray() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(args);
        mll.removeLast();
        assertEquals(mll.getLast(), args[2]);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void getIndexNegativeIndexIndexIllegalArgumentException() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(args);
        mll.get(-1);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void getIndexWithTooBigIndexIllegalArgumentException() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(args);
        mll.get(11);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void getIndexEmptyArrayIllegalArgumentException() {
        mll.get(11);
    }

    @Test
    public void getIndexCorrectArgumentCheckEquality() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(args);
        assertEquals(args[1], mll.get(1));
    }

    @Test
    public void myLinkedListCreateNotEmptyArrayCheckEquality() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };

        mll.addAll(args);
        MyLinkedList<String> mll2 = new MyLinkedList<>(mll);
        assertArrayEquals(mll.toArray(), mll2.toArray());

    }

    @Test(expected = IllegalArgumentException.class)
    public void myLinkedListCreateNotEmptyArrayWithNullValueIllegalArgumentException() {
        mll = new MyLinkedList<>(null);
    }

    @Test
    public void addElementEmptyArrayCheckEquality() {
        mll.add("string");
        assertEquals(mll.getFirst(), "string");
    }

    @Test
    public void addElementNotEmptyArrayCheckEquality() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(args);
        mll.add("string");
        assertEquals(mll.getLast(), "string");
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void addAtIndexNegativeIndexIllegalArgumentException() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(args);
        mll.add(-1, "string");
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void addAtIndexIndexTooBigIllegalArgumentException() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(args);
        mll.add(21, "string");
    }

    @Test
    public void addAtIndexRightIndexIllegalArgumentException() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(args);
        mll.add(2, "string");
        assertEquals(mll.get(2), "string");
    }

    @Test
    public void addAtIndexFirstIndexIllegalArgumentException() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(args);
        mll.add(0, "string");
        assertEquals(mll.get(0), "string");
    }

    @Test
    public void addAtIndexLastIndexIllegalArgumentException() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(args);
        mll.add(4, "string");
        assertEquals(mll.get(4), "string");
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void addAllAtIndexIndexTooBigIllegalArgumentException() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(3, args);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void addAllAtIndexNegativeIndexIllegalArgumentException() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(-3, args);
    }

    @Test
    public void addAllFirstIndexCheckEquality() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        String[] arrayStart = { "One", "Two" };
        mll.addAll(arrayStart);
        mll.addAll(0, args);
        String[] result = new String[args.length + arrayStart.length];
        System.arraycopy(args, 0, result, 0, args.length);
        System.arraycopy(arrayStart, 0, result, args.length, arrayStart.length);
        assertArrayEquals(mll.toArray(), result);
    }

    @Test
    public void addAllLastIndexCheckEquality() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        String[] arrayStart = { "One", "Two" };
        mll.addAll(arrayStart);
        mll.addAll(2, args);
        String[] result = new String[args.length + arrayStart.length];
        System.arraycopy(arrayStart, 0, result, 0, arrayStart.length);
        System.arraycopy(args, 0, result, arrayStart.length, args.length);
        assertArrayEquals(mll.toArray(), result);
    }

    @Test
    public void addAllMiddleIndexCheckEquality() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        String[] arrayStart = { "One", "Two" };
        String[] arrayEnd = { "pre-Last", "Last" };
        mll.addAll(arrayStart);
        mll.addAll(arrayEnd);
        mll.addAll(2, args);
        String[] result = new String[args.length + arrayStart.length
                + arrayEnd.length];
        System.arraycopy(arrayStart, 0, result, 0, arrayStart.length);
        System.arraycopy(args, 0, result, arrayStart.length, args.length);
        System.arraycopy(arrayEnd, 0, result, arrayStart.length + args.length,
                arrayEnd.length);
        assertArrayEquals(mll.toArray(), result);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void removeEmptyArrayIllegalArgumentException() {
        mll.remove(0);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void removeIndexTooBigIllegalArgumentException() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(args);
        mll.remove(4);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void removeNegativeIndexIllegalArgumentException() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava" };
        mll.addAll(args);
        mll.remove(-4);
    }

    @Test
    public void removeIndexInMiddleCheckEquality() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava",
                "New String1", "One", "Two" };
        mll.addAll(args);
        mll.remove(6);
        mll.remove(0);
        mll.remove(2);
        assertArrayEquals(mll.toArray(), new String[] { "blablabla",
                "Hello World", "New String1", "One" });
    }

    @Test
    public void clearEmptyArray() {
        mll.clear();
        assertEquals(mll.size(), 0);
    }

    @Test
    public void clearNotEmptyArray() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava",
                "New String1", "One", "Two" };
        mll.addAll(args);
        mll.clear();
        assertEquals(mll.size(), 0);
    }

    @Test
    public void isEmptyEmptyArrayReturnTrue() {
        assertEquals(mll.isEmpty(), true);
    }

    @Test
    public void isEmptyNotEmptyArrayReturnFalse() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava",
                "New String1", "One", "Two" };
        mll.addAll(args);
        assertEquals(mll.isEmpty(), false);
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void setNegativeIndexIllegalArgumentException() {
        mll.set(-4, "don't work");
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void setIndexIsTooBigIllegalArgumentException() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava",
                "New String1", "One", "Two" };
        mll.addAll(args);
        mll.set(7, "don't work");
    }

    @Test
    public void setIndexIsCorrectCheckEquality() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava",
                "New String1", "One", "Two" };
        mll.addAll(args);
        mll.set(4, "Hello form forth index");
        args[4] = "Hello form forth index";
        assertArrayEquals(mll.toArray(), args);
    }

    @Test
    public void indexOfNotExistentValueReturnsMinusOne() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava",
                "New String1", "One", "Two" };
        mll.addAll(args);
        assertEquals(-1, mll.indexOf("Non-existed value"));
    }

    @Test
    public void indexOfExistentValueReturnsIndex() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava",
                "New String1", "One", "Two" };
        mll.addAll(args);
        assertEquals(5, mll.indexOf("One"));
    }

    @Test
    public void iteratorWhileIterateShouldBeTheSameAsToArray() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava",
                "New String1", "One", "Two" };
        mll.addAll(args);
        Iterator<String> iter = mll.iterator();
        String[] res = new String[args.length];
        int index = 0;
        while (iter.hasNext()) {
            res[index++] = iter.next();
        }
        assertArrayEquals(args, res);
    }

    @Test
    public void iteratorRemoveShouldRemoveAll() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava",
                "New String1", "One", "Two" };
        mll.addAll(args);
        Iterator<String> iter = mll.iterator();
        while (iter.hasNext()) {
            iter.remove();
        }
        assertEquals(mll.size(), 0);
    }

    @Test
    public void iteratorRemoveShouldRemoveMiddle() {
        String[] args = { "Test1", "blablabla", "Hello World"};
        mll.addAll(args);
        Iterator<String> iter = mll.iterator();
        iter.next();
        iter.remove();
        assertEquals(mll.size(), 2);
    }

    @Test
    public void descendingIteratorWhileIterateShouldBeTheSameAsToArray() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava",
                "New String1", "One", "Two" };
        mll.addAll(args);
        Iterator<String> iter = mll.descendingIterator();
        String[] res = new String[args.length];
        int index = args.length - 1;
        while (iter.hasNext()) {
            res[index--] = iter.next();
        }
        assertArrayEquals(args, res);
    }

    @Test
    public void descendingIteratorRemoveShouldRemoveAll() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava",
                "New String1", "One", "Two" };
        mll.addAll(args);
        Iterator<String> iter = mll.descendingIterator();
        while (iter.hasNext()) {
            iter.remove();
        }
        assertEquals(mll.size(), 0);
    }

    @Test
    public void offerAddNullValueShouldReturnNull() {
        mll.offer(null);
        assertNull(mll.getFirst());
    }

    @Test
    public void offerAddListOfValuesShouldReturnTheSame() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava",
                "New String1", "One", "Two" };
        for (String arg : args) {
            mll.offer(arg);
        }
        assertArrayEquals(mll.toArray(), args);
    }

    @Test
    public void peekAddsArrayOfElementsCheckRightOrder() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava",
                "New String1", "One", "Two" };
        for (String arg : args) {
            mll.offer(arg);
        }
        assertEquals(args[0], mll.peek());
    }

    @Test
    public void pollAddsArrayOfElementsCheckRightOrder() {
        String[] args = { "Test1", "blablabla", "Hello World", "HelloJava",
                "New String1", "One", "Two" };
        for (String arg : args) {
            mll.offer(arg);
        }
        String[] res = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            res[i] = mll.poll();
        }

        assertArrayEquals(res, args);
    }

    @Test
    public void pushAddValuesCheckOrder() {
        String[] args = { "Hello", "To", "be", "or", "not", " to", "be" };
        for (String arg : args) {
            mll.push(arg);
        }
        assertArrayEquals(mll.toArray(), args);
    }

    @Test
    public void popAddValuesCheckOrder() {
        String[] args = { "Hello", "To", "be", "or", "not", " to", "be" };
        for (String arg : args) {
            mll.push(arg);
        }
        String[] res = new String[args.length];
        for (int i = args.length - 1; i >= 0; i--) {
            res[i] = mll.pop();
        }
        assertArrayEquals(res, args);
    }
    
    @Test(expected = NoSuchElementException.class)
    public void iteratorNextEmptyListNoSuchElementException() {
        mll.iterator().next();
    }
    
    @Test(expected = NoSuchElementException.class)
    public void descendingIteratorNextEmptyListNoSuchElementException() {
        mll.descendingIterator().next();
    }
}
