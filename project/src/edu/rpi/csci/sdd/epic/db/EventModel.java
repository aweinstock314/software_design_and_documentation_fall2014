package edu.rpi.csci.sdd.epic.db;

import java.sql.*;
import javax.sql.DataSource;
import java.util.ArrayList;

import edu.rpi.csci.sdd.epic.util.Util;

public class EventModel
{
    public static String unixTimestamp(long t)
    {
        //http://stackoverflow.com/questions/16609722/postgres-how-to-convert-from-unix-epoch-to-date
        return String.format("(to_timestamp( cast(%d AS bigint) / 1000))", t);
    }
    public static void createEvent(int id, String host, String source, String creator, boolean recurring, long starttime, long endtime, String location, boolean on_campus) throws SQLException
    {
        Connection db = Util.getCredentialedDataSource().getConnection();
        Statement stmt = db.createStatement();
        stmt.execute(String.format("INSERT INTO events (id, host, source, creator, recurring, starttime, endtime, location, on_campus) VALUES ( '%d', '%s', '%s', '%s', '%b', %s, %s, '%s', '%b')", id, host, source, creator, recurring, unixTimestamp(starttime), unixTimestamp(endtime), location, on_campus));
    }
    public static String getEventsAsJSArray() throws SQLException
    {
        ArrayList<String> results = new ArrayList();
        Connection db = Util.getCredentialedDataSource().getConnection();
        ResultSet rs = db.createStatement().executeQuery("SELECT id, host, source, creator, recurring, starttime, endtime, location, on_campus FROM events");
        while(rs.next())
        {
            int id = rs.getInt(1);
            String host = rs.getString(2);
            String source = rs.getString(3);
            String creator = rs.getString(4);
            boolean recurring = rs.getBoolean(5);
            long starttime = rs.getDate(6).getTime();
            long endtime = rs.getDate(7).getTime();
            boolean location = rs.getBoolean(8);
            boolean on_campus = rs.getBoolean(9);
            String tagsJSArray = "[]"; // TODO: get tags from database
            results.add(String.format("{id: %d, host: \"%s\", source: \"%s\", creator: \"%s\", recurring: %b, starttime: %d, endtime: %d, location: \"%s\", on_campus: %b, tags: %s}", id, host, source, creator, recurring, starttime, endtime, location, on_campus, tagsJSArray));
        }
        return "[" + Util.joinList(results, ", ") + "]";
    }
}
