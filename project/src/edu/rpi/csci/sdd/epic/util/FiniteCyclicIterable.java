package edu.rpi.csci.sdd.epic.util;

import java.lang.Iterable;
import java.util.Iterator;

// Simulates array of element 'e' of specified size 's'
// Currently used to generate query string for prepared statements
public class FiniteCyclicIterable<T> implements Iterable<T>
{
    final protected T elem;
    final protected int size;
    public FiniteCyclicIterable(T e, int s)
    {
        size = s;
        elem = e;
    }
    public Iterator<T> iterator()
    {
        return new Iterator<T>()
        {
            int index = 0;
            public boolean hasNext() { return index < size; }
            public T next() { ++index; return elem; }
            public void remove() { throw new UnsupportedOperationException(); }
        };
    }
}
