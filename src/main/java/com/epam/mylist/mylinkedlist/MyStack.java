package com.epam.mylist.mylinkedlist;

public interface MyStack<Type> {
    /**
     * Adds element to the stack(in the end)
     * @param e - element which will be added
     */
    void push(Type e);
    
    /**
     * Gets last element of the stack(with deleting)
     * @return last element of the stack
     */
    Type pop();
}
