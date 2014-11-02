package edu.rpi.csci.sdd.epic.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.sql.*;
import javax.sql.DataSource;

import edu.rpi.csci.sdd.epic.util.FiniteCyclicIterable;
import edu.rpi.csci.sdd.epic.util.Util;

public class GenericModel
{
    // These methods depend on the "vals" maps having 
    //  consistant iteration orderings. This could be enforced by 
    //  using SortedMap in the signatures, but this is a stronger 
    //  condition than necessary (e.g. would exclude LinkedHashMap, 
    //  which works properly). Don't use them with regular HashMap.
    protected static void setPreparedStatementValues(PreparedStatement stmt, Map<String, Object> vals) throws SQLException
    {
        int i = 0;
        for(Object o : vals.values())
        {
            ++i;
            stmt.setObject(i, o);
        }
    }
    // Insert some data into the database. ds is the DataSource 
    //  used to connect, tablename is the name of the table 
    //  to insert into, and vals is a map of (columname, 
    //  value-to-insert) pairs.
    public static void insert(DataSource ds, String tablename, Map<String, Object> vals) throws SQLException
    {
        // connect to the database
        Connection db = ds.getConnection();
        try
        {
            // construct the SQL for the prepared statement
            String colnames = Util.joinIterable(vals.keySet(), ", ", "(", ")");
            String questionmarks = Util.joinIterable(new FiniteCyclicIterable("?", vals.keySet().size()), ", ", "(", ")");
            PreparedStatement stmt = db.prepareStatement(String.format("INSERT INTO %s %s VALUES %s", tablename, colnames, questionmarks));
            // add the values to the prepared statement
            setPreparedStatementValues(stmt, vals);
            // execute the insertion
            stmt.execute();
        }
        // close the database connection to avoid a leak
        finally { db.close(); }
    }
    // Read data from the database. ds is the DataSource used 
    //  to connect, tablename is the name of the table to read 
    //  from, and columns are the names of the columns to read. 
    public static Map<String, List<Object>> select(DataSource ds, String tablename, String... columns) throws SQLException
    {
        // allocate/initialize the return value
        Map<String, List<Object>> ret = new LinkedHashMap<String, List<Object>>();
        for(String col : columns) { ret.put(col, new ArrayList<Object>()); }
        // connect to the database
        Connection db = ds.getConnection();
        try
        {
            String colnames = Util.joinIterable(Arrays.asList(columns), ", ");
            // execute the query
            ResultSet rs = db.createStatement().executeQuery(String.format("SELECT %s FROM %s", colnames, tablename));
            // loop over the rows returned
            while(rs.next())
            {
                for(int i=0; i<columns.length; i++)
                {
                    // add each row of data to the result object
                    ret.get(columns[i]).add(rs.getObject(i+1));
                }
            }
        }
        // close the database connection to avoid a leak
        finally { db.close(); }
        return ret;
    }
}
