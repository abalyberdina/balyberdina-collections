package com.epam.mylist.mylinkedlist;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import com.epam.mylist.MyIndexOutOfBoundsException;
import com.epam.mylist.MyList;

/**
 * Class which represents doubly-linked list
 * 
 * @author Anastasiia_Balyberdina
 *
 * @param <Type>
 *            - type of elements in linked list
 */
public class MyLinkedList<Type> implements MyList<Type>, MyStack<Type>,
        MyQueue<Type> {

    private Node<Type> first;
    private Node<Type> last;
    private int size;

    public MyLinkedList() {
        first = null;
        last = null;
        size = 0;
    }

    public MyLinkedList(MyList<Type> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                this.addLast((Type) list.get(i));
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void addFirst(Type data) {
        Node<Type> node = new Node<>(data);
        if (addFirstElementInList(node)) {
            return;
        }
        node.setPrev(null);
        node.setNext(first);
        first.setPrev(node);
        first = node;
        size++;
    }

    public void addLast(Type data) {
        Node<Type> node = new Node<>(data);
        if (addFirstElementInList(node)) {
            return;
        }
        node.setPrev(last);
        last.setNext(node);
        last = node;
        size++;
    }

    public Type getFirst() {
        if (size == 0) {
            throw new MyIndexOutOfBoundsException();
        }
        return first.getElement();
    }

    public Type getLast() {
        if (size == 0) {
            throw new MyIndexOutOfBoundsException();
        }
        return last.getElement();
    }

    public Type removeFirst() {
        Node<Type> temp = first;
        if (size == 0) {
            throw new MyIndexOutOfBoundsException();
        }
        first = temp.getNext();
        if (first != null) {
            first.setPrev(null);
        }
        size--;
        return temp.getElement();
    }

    public Type removeLast() {
        Node<Type> temp = last;
        if (size == 0) {
            throw new MyIndexOutOfBoundsException();
        }
        last = temp.getPrev();
        if (last != null) {
            last.setNext(null);
        }
        size--;
        return temp.getElement();
    }

    @Override
    public void add(Type e) {
        addLast(e);
    }

    @Override
    public void add(int index, Type e) {
        if (index == 0) {
            addFirst(e);
            return;
        }
        if (index == size) {
            addLast(e);
            return;
        }
        if (index < 0 || index >= size) {
            throw new MyIndexOutOfBoundsException();
        }
        Node<Type> node = first;
        Node<Type> newNode = new Node<>(e);
        for (int i = 1; i < index; i++) {
            node = node.getNext();
        }
        newNode.setNext(node.getNext());
        newNode.setPrev(node);
        node.getNext().setPrev(newNode);
        node.setNext(newNode);
        size++;
    }

    @Override
    public void addAll(Type[] c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < c.length; i++) {
            addLast(c[i]);
        }
    }

    @Override
    public void addAll(int index, Type[] c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        for (int i = index; i < c.length + index; i++) {
            add(i, c[i - index]);
        }
    }

    @Override
    public Type get(int index) {
        if (index < 0 || index >= size) {
            throw new MyIndexOutOfBoundsException();
        }
        if (index == 0) {
            return first.getElement();
        }
        if (index == size - 1) {
            return last.getElement();
        }

        Node<Type> temp = first;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        return temp.getElement();
    }

    @Override
    public Type remove(int index) {
        if (index < 0 || index >= size) {
            throw new MyIndexOutOfBoundsException();
        }
        if (size == 1) {
            Type result = first.getElement();
            first = null;
            last = null;
            size = 0;
            return result;
        }
        if (index == 0) {
            return removeFirst();
        }
        if (index == size - 1) {
            return removeLast();
        }
        Node<Type> temp = first;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        temp.getNext().setPrev(temp.getPrev());
        temp.getPrev().setNext(temp.getNext());
        size--;
        return temp.getElement();
    }

    @Override
    public void clear() {
        Iterator<Type> iter = this.iterator();
        while (iter.hasNext()) {
            iter.remove();
        }
        first = null;
        last = null;
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
        Node<Type> temp = first;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        temp.setElement(e);
    }

    @Override
    public int indexOf(Type o) {
        Node<Type> temp = first;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(temp.getElement(), o)) {
                return i;
            }
            temp = temp.getNext();
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Type[] toArray() {
        if (size == 0) {
            return null;
        }
        Node<Type> node = first;
        Type[] arr = (Type[]) Array.newInstance(node.getElement().getClass(),
                size);
        for (int i = 0; i < size; i++) {
            arr[i] = node.getElement();
            node = node.getNext();
        }
        return arr;
    }

    @Override
    public Iterator<Type> iterator() {
        return new MyIterator();
    }

    public Iterator<Type> descendingIterator() {
        return new MyDescendingIterator();
    }

    @Override
    public void offer(Type e) {
        addLast(e);
    }

    @Override
    public Type peek() {
        return getFirst();
    }

    @Override
    public Type poll() {
        return removeFirst();
    }

    @Override
    public void push(Type e) {
        addLast(e);

    }

    @Override
    public Type pop() {
        return removeLast();
    }

    private boolean addFirstElementInList(Node<Type> node) {
        if (size == 0) {
            first = node;
            last = node;
            size++;
            return true;
        } else {
            return false;
        }
    }
    
    private void removeNode(Node<Type> node) {
        if (node == first) {
            removeFirst();
            return;
        }
        if (node == last) {
            removeLast();
            return;
        }
        node.getNext().setPrev(node.getPrev());
        node.getPrev().setNext(node.getNext());
        size--;
    }

    private static class Node<Type> {
        private Node<Type> next;
        private Node<Type> prev;

        private Type element;

        public Node() {
            next = null;
            prev = null;
        }

        public Node(Type data) {
            this();
            element = data;
        }

        public Node<Type> getNext() {
            return next;
        }

        public void setNext(Node<Type> following) {
            this.next = following;
        }

        public Node<Type> getPrev() {
            return prev;
        }

        public void setPrev(Node<Type> earlier) {
            this.prev = earlier;
        }

        public Type getElement() {
            return element;
        }

        public void setElement(Type data) {
            this.element = data;
        }
    }

    private class MyIterator implements Iterator<Type> {
        private Node<Type> current = first;

        @Override
        public boolean hasNext() {
            if (current != null) {
                return true;
            }
            return false;
        }

        @Override
        public Type next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Type data = current.element;
            current = current.getNext();
            return data;
        }

        @Override
        public void remove() {
            Node<Type> temp = current.getNext();
            MyLinkedList.this.removeNode(current);
            current = temp;
        }
    }

    private class MyDescendingIterator implements Iterator<Type> {
        private Node<Type> current = last;

        @Override
        public boolean hasNext() {
            if (current != null) {
                return true;
            }
            return false;
        }

        @Override
        public Type next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Type data = current.element;
            current = current.getPrev();
            return data;
        }

        @Override
        public void remove() {
            Node<Type> temp = current.getPrev();
            MyLinkedList.this.removeNode(current);
            current = temp;
        }
    }

}
