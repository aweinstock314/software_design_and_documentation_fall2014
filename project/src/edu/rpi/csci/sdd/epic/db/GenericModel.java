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
    public static void insert(DataSource ds, String tablename, Map<String, Object> vals) throws SQLException
    {
        Connection db = ds.getConnection();
        try
        {
            String colnames = Util.joinIterable(vals.keySet(), ", ", "(", ")");
            String questionmarks = Util.joinIterable(new FiniteCyclicIterable("?", vals.keySet().size()), ", ", "(", ")");
            PreparedStatement stmt = db.prepareStatement(String.format("INSERT INTO %s %s VALUES %s", tablename, colnames, questionmarks));
            setPreparedStatementValues(stmt, vals);
            stmt.execute();
        }
        finally { db.close(); }
    }
    public static Map<String, List<Object>> select(DataSource ds, String tablename, String... columns) throws SQLException
    {
        Map<String, List<Object>> ret = new LinkedHashMap<String, List<Object>>();
        for(String col : columns) { ret.put(col, new ArrayList<Object>()); }
        Connection db = ds.getConnection();
        try
        {
            String colnames = Util.joinIterable(Arrays.asList(columns), ", ");
            ResultSet rs = db.createStatement().executeQuery(String.format("SELECT %s FROM %s", colnames, tablename));
            while(rs.next())
            {
                for(int i=0; i<columns.length; i++)
                {
                    ret.get(columns[i]).add(rs.getObject(i+1));
                }
            }
        }
        finally { db.close(); }
        return ret;
    }
}
