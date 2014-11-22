package edu.rpi.csci.sdd.epic.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.Iterable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

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
    // http://stackoverflow.com/questions/93655/stripping-invalid-xml-characters-in-java
    //static Pattern INVALID_XML_CHARS = Pattern.compile("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\uD800\uDC00-\uDBFF\uDFFF]");
    static Pattern INVALID_XML_CHARS = Pattern.compile("[^\\u0009\\u000A\\u000D\\u0020-\\u007F]"); // printable ASCII-ish
    public static String sanitizeStringForXml(String s)
    {
        return INVALID_XML_CHARS.matcher(s).replaceAll("");
    }

    public static void asynchStreamCopy(final InputStream is, final OutputStream os) { asynchStreamCopy(is,os,0x1000); }
    public static void asynchStreamCopy(final InputStream is, final OutputStream os, final int BUF_SIZE)
    {
        Thread t = new Thread(new Runnable(){
            @Override public void run()
            {
                try
                {
                    int rc;
                    byte[] buf = new byte[BUF_SIZE];
                    while((rc = is.read(buf,0,BUF_SIZE)) != -1)
                    {
                        os.write(buf,0,rc);
                        os.flush();
                    }
                    os.close();
                }
                catch(IOException ioe) {}
            }
        });
        t.setDaemon(true);
        t.start();
    }
}
