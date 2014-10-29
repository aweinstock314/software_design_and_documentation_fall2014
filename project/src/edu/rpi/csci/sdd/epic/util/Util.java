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
import java.util.Iterator;
import java.util.List;

public class Util
{
    public static String throwableToString(Throwable t)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        t.printStackTrace(new PrintStream(baos));
        return baos.toString();
    }
    public static String slurpReader(Reader reader) throws IOException
    {
        BufferedReader r = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        String tmp;
        while((tmp = r.readLine()) != null) { sb.append(tmp); sb.append("\n"); }
        r.close();
        return sb.toString();
    }
    public static String slurpFile(File f) throws IOException
    {
        if(!f.exists()) { return null; }
        return slurpReader(new FileReader(f));
    }
    public static String slurpStream(InputStream s) throws IOException
    {
        return slurpReader(new InputStreamReader(s));
    }
    public static String joinIterable(Iterable<String> iterable, String infix)
    {
        return joinIterable(iterable, infix, "", "");
    }
    public static String joinIterable(Iterable<String> iterable, String infix, String prefix, String suffix)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        Iterator<String> iter = iterable.iterator();
        while(iter.hasNext())
        {
            sb.append(iter.next());
            if(iter.hasNext()) { sb.append(infix); }
        }
        sb.append(suffix);
        return sb.toString();
    }
}
