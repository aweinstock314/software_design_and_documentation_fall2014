package edu.rpi.csci.sdd.epic.util;

import java.lang.Iterable;
import java.util.Iterator;

// Python-style range iterator over a half-open interval of integers from [lo, hi)
public class Range implements Iterable<Integer>
{
    final protected int lo;
    final protected int hi;
    public Range(int l, int h)
    {
        lo = l;
        hi = h;
    }
    public Iterator<Integer> iterator()
    {
        return new Iterator<Integer>()
        {
            int index = lo;
            public boolean hasNext() { return index < hi; }
            public Integer next() { return index++; }
            public void remove() { throw new UnsupportedOperationException(); }
        };
    }
}
