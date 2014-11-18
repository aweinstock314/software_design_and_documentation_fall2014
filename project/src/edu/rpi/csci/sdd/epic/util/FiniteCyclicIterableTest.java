package edu.rpi.csci.sdd.epic.util;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.Iterable;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;

public class FiniteCyclicIterableTest
{
    public static <E> Collection<E> collectionOfIterable(Iterable<E> iterable)
    {
        Collection<E> col = new ArrayList<E>();
        for(E e : iterable) { col.add(e); }
        return col;
    }
    public static <E> Collection<E> collectionLiteral(E... list)
    {
        Collection<E> col = new ArrayList<E>();
        for(E e : list) { col.add(e); }
        return col;
    }

    @Test
    public void testEmptyFiniteCyclicIterableIsEmpty()
    {
        assertEquals(collectionLiteral(), collectionOfIterable(new FiniteCyclicIterable("", 0)));
    }
}
