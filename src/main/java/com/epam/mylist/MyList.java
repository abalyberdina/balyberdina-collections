package com.epam.mylist;

import java.util.Iterator;

public interface MyList<Type> extends Iterable<Type> {
    void add(Type e);

    void add(int index, Type e);

    void addAll(Type[] c);

    void addAll(int index, Type[] c);

    Type get(int index);

    Type remove(int index);

    void clear();

    boolean isEmpty();

    void set(int index, Type e);

    int indexOf(Type o);

    int size();

    Type[] toArray();

    Iterator<Type> iterator();
}
