package edu.rpi.csci.sdd.epic.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.Iterable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Util
{
    // throwableToString prints the stack trace of a Throwable to a String
    public static String throwableToString(Throwable t)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        t.printStackTrace(new PrintStream(baos));
        return baos.toString();
    }
    // slurpReader puts all the content from an Reader into a String
    public static String slurpReader(Reader reader) throws IOException
    {
        BufferedReader r = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        String tmp;
        // append every line from the reader to the output string
        while((tmp = r.readLine()) != null) { sb.append(tmp); sb.append("\n"); }
        r.close();
        return sb.toString();
    }
    // slurpFile puts all the content from an File into a String
    public static String slurpFile(File f) throws IOException
    {
        if(!f.exists()) { return null; }
        return slurpReader(new FileReader(f));
    }
    // slurpStream puts all the content from an InputStream into a String
    public static String slurpStream(InputStream s) throws IOException
    {
        return slurpReader(new InputStreamReader(s));
    }

    // joinIterable converts the elements of an Iterable to a String, seperated by a specified delimiter.
    public static String joinIterable(Iterable<String> iterable, String infix)
    {
        return joinIterable(iterable, infix, "", "");
    }
    public static String joinIterable(Iterable<String> iterable, String infix, String prefix, String suffix)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        Iterator<String> iter = iterable.iterator();
        // appends the elements from the iterable to the output string
        while(iter.hasNext())
        {
            sb.append(iter.next());
            // only add the infix delimiter if we're not at the end of the iterable
            if(iter.hasNext()) { sb.append(infix); }
        }
        sb.append(suffix);
        return sb.toString();
    }
    // collectionOfIterable returns a Collection object from the elements of an Iterable object
    public static <E> Collection<E> collectionOfIterable(Iterable<E> iterable)
    {
        Collection<E> col = new ArrayList<E>();
        for(E e : iterable) { col.add(e); }
        return col;
    }
    // collectionLiteral makes a Collection object from its 
    //  arguments, allowing for "literals" to compare against in tests
    public static <E> Collection<E> collectionLiteral(E... list)
    {
        Collection<E> col = new ArrayList<E>();
        for(E e : list) { col.add(e); }
        return col;
    }
}
