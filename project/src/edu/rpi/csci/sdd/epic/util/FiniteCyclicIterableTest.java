package edu.rpi.csci.sdd.epic.util;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.Iterable;
import java.util.Iterator;

public class FiniteCyclicIterableTest
{

    @Test
    public void testEmptyFiniteCyclicIterableIsEmpty()
    {
        assertEquals(Util.collectionLiteral(), Util.collectionOfIterable(new FiniteCyclicIterable("", 0)));
    }

    @Test
    public void testSizeOfFiniteCyclicIterableIsAsSpecified()
    {
        for(int i : new Range(1, 100))
        {
            assertEquals(i, Util.collectionOfIterable(new FiniteCyclicIterable("", i)).size());
        }
    }

    @Test
    public void testGeneratesQuestionMarkString()
    {
        assertEquals(Util.joinIterable(new FiniteCyclicIterable("?", 40), ", ", "(", ")"),
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }
}
