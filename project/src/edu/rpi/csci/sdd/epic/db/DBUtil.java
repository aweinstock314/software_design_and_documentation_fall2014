package edu.rpi.csci.sdd.epic.db;

import java.io.File;
import java.io.IOException;

import java.sql.*;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;

import edu.rpi.csci.sdd.epic.util.Util;

public class DBUtil
{
    // get a DataSource object that can connect to the DB, but still needs a username/password at the callsite
    protected static DataSource getJDBCDataSource()
    {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setDatabaseName("epic_db");
        return ds;
    }
    // get a DataSource object that can connect to the DB, and 
    //  apply a username/password at creation time (so anything can 
    //  use it directly).
    protected static DataSource getJDBCDataSource(String username, String password)
    {
        PGSimpleDataSource ds = (PGSimpleDataSource)getJDBCDataSource();
        ds.setUser(username);
        ds.setPassword(password);
        return ds;
    }
    // get a DataSource object using the credentials stored in 
    //  a file. This is the only method here intended to be called 
    //  externally.
    public static DataSource getCredentialedDataSource()
    {
        String[] lines;
        try { lines = Util.slurpFile(new File("epic_database_creds.txt")).split("\n"); }
        catch(IOException e) { throw new RuntimeException(e); }
        String username = lines[0].trim();
        String password = lines[1].trim();
        return getJDBCDataSource(username, password);
    }
}
