package com.epam.mylist;

import java.util.Comparator;
import java.util.RandomAccess;

public class MyCollections {
    public static <Type> void sort(MyList<Type> list) {

        if (list == null) {
            throw new IllegalArgumentException();
        }
        if (list instanceof RandomAccess) {
            quickSort(list, null);
        } else {
            bubbleSort(list);
        }
    }

    public static <Type> void sort(MyList<Type> list, Comparator<Type> c) {
        if (list == null || c == null) {
            throw new IllegalArgumentException();
        }
        quickSort(list, c);
    }

    public static <Type> void swap(MyList<Type> list, int i, int j) {
        if (list == null) {
            throw new IllegalArgumentException();
        }
        if (i < 0 || i >= list.size() || j < 0 || j >= list.size()) {
            throw new MyIndexOutOfBoundsException();
        }
        Type temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public static <Type> void copy(MyList<Type> dest, MyList<Type> src) {
        if (dest == null || src == null) {
            throw new IllegalArgumentException();
        }
        dest.clear();
        dest.addAll(src.toArray());
    }

    public static <Type> void reverse(MyList<Type> list) {
        if (list == null) {
            throw new IllegalArgumentException();
        }
        Type[] objs = list.toArray();
        Type temp = null;
        for (int i = 0; i < list.size() / 2; i++) {
            temp = objs[i];
            objs[i] = objs[list.size() - 1 - i];
            objs[list.size() - 1 - i] = temp;
        }
        list.clear();
        list.addAll(objs);
    }

    public static <Type> int binarySearch(RandomAccess list, Type key) {
        if (list == null || key == null || !(list instanceof MyList)) {
            throw new IllegalArgumentException();
        }
        MyList<Type> myList = (MyList<Type>) list;
        return binarySearch(myList, key, 0, myList.size() - 1);
    }

    private static <Type> int binarySearch(MyList<Type> list, Type key, int lo,
            int hi) {
        int middle = -1;
        int compareResult = -1;
        if (hi < lo) {
            return -lo - 1;
        }
        middle = lo + (hi - lo) / 2;
        compareResult = ((Comparable<Type>) list.get(middle)).compareTo(key);
        if (compareResult == 0) {
            return middle;
        } else if (compareResult < 0) {
            return binarySearch(list, key, middle + 1, hi);
        } else {
            return binarySearch(list, key, lo, middle - 1);
        }
    }

    private static <Type> void quickSort(MyList<Type> list,
            Comparator<Type> comparator) {
        quickSort(list, 0, list.size() - 1, comparator);
    }

    private static <Type> void quickSort(MyList<Type> list, int lo, int hi,
            Comparator<Type> comparator) {
        if (hi < lo) {
            return;
        }
        int lower = lo, greater = hi, i = lo;
        Type pivot = list.get(lo);
        int compareResult = -1;
        while (i <= greater) {
            if (comparator == null) {
                compareResult = ((Comparable<Type>) list.get(i))
                        .compareTo(pivot);
            } else {
                compareResult = comparator.compare(list.get(i), pivot);
            }
            if (compareResult < 0) {
                swap(list, lower++, i++);
            } else if (compareResult > 0) {
                swap(list, i, greater--);
            } else {
                i++;
            }
        }
        quickSort(list, lo, lower - 1, comparator);
        quickSort(list, greater + 1, hi, comparator);
    }

    private static <Type> void bubbleSort(MyList<Type> list) {
        for (int barrier = list.size() - 1; barrier > 0; barrier--) {
            onePath(list, barrier);
        }
    }

    public static <Type> void onePath(MyList<Type> list, int barrier) {
        for (int index = 0; index < barrier; index++) {
            if (((Comparable<Type>) list.get(index)).compareTo(list
                    .get(index + 1)) > 0) {
                swap(list, index, index + 1);
            }
        }
    }
}