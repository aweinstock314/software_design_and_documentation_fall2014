package edu.rpi.csci.sdd.epic.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;

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
    public static DataSource getJDBCDataSource()
    {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setDatabaseName("epic_db");
        return ds;
    }
    public static DataSource getJDBCDataSource(String username, String password)
    {
        PGSimpleDataSource ds = (PGSimpleDataSource)getJDBCDataSource();
        ds.setUser(username);
        ds.setPassword(password);
        return ds;
    }
    public static DataSource getCredentialedDataSource()
    {
        String[] lines;
        try { lines = slurpFile(new File("epic_database_creds.txt")).split("\n"); }
        catch(IOException e) { throw new RuntimeException(e); }
        String username = lines[0].trim();
        String password = lines[1].trim();
        return getJDBCDataSource(username, password);
    }
    public static ArrayList<String> getUsersTable() throws SQLException
    {
        // TODO: move to AccountModel
        ArrayList<String> results = new ArrayList();
        Connection db = getCredentialedDataSource().getConnection();
        ResultSet rs = db.createStatement().executeQuery("SELECT * FROM USERS");
        while(rs.next())
        {
            String username = rs.getString(1);
            Boolean isEventProvider = rs.getBoolean(2);
            String email = rs.getString(3);
            results.add(String.format("(%s, %b, %s)", username, isEventProvider, email));
        }
        return results;
    }
    public static String joinList(List<String> lst, String with)
    {
        StringBuilder sb = new StringBuilder();
        final int size = lst.size();
        int idx = 0;
        for(String s : lst)
        {
            sb.append(s);
            if(++idx != size) { sb.append(with); }
        }
        return sb.toString();
    }
}
