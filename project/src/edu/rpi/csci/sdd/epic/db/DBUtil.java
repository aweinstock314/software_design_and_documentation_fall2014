package edu.rpi.csci.sdd.epic.db;

import java.io.File;
import java.io.IOException;

import java.sql.*;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;

import edu.rpi.csci.sdd.epic.util.Util;

public class DBUtil
{
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
        try { lines = Util.slurpFile(new File("epic_database_creds.txt")).split("\n"); }
        catch(IOException e) { throw new RuntimeException(e); }
        String username = lines[0].trim();
        String password = lines[1].trim();
        return getJDBCDataSource(username, password);
    }
}
