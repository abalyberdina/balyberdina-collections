package com.epam.mylist;

import java.util.Comparator;

public class ReverseComparator<Type> implements Comparator<Type> {
    @Override
    public int compare(Object objectA, Object objectB) {
        if (objectA == null) {
            throw new IllegalArgumentException();
        }
        Type first = (Type) objectA;
        Type second = (Type) objectB;
        int compareResult = ((Comparable<Type>) first).compareTo(second);
        if (compareResult > 0) {
            return -1;
        } else if (compareResult < 0) {
            return 1;
        } else {
            return 0;
        }
    }

}
