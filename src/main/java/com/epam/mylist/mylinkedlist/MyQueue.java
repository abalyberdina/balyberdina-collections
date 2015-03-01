package com.epam.mylist.mylinkedlist;

public interface MyQueue<Type> {
    /**
     * Adds element in the end of queue
     * @param e - element which will be added
     */
    void offer(Type e);

    /**
     * Get first element of the queue (without deleting)
     * @return first element of the queue
     */
    Type peek();

    /**
     * Get first element of the queue (with deleting)
     * @return first element of the queue
     */
    Type poll();
}
