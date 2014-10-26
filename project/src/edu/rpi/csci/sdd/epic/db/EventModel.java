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
    public static void createEvent(String name, String host, String source, String creator, boolean recurring, long starttime, long endtime, String location, boolean on_campus) throws SQLException
    {
        Connection db = Util.getCredentialedDataSource().getConnection();
        Statement stmt = db.createStatement();
		location = location.replace("\'","");
        String sql = String.format("INSERT INTO events (name, host, source, creator, recurring, starttime, endtime, location, on_campus) VALUES ('%s', '%s', '%s', '%s', '%b', %s, %s, '%s', '%b')", name, host, source, creator, recurring, unixTimestamp(starttime), unixTimestamp(endtime), location, on_campus);
		//sql = sql.replace("\'", "");
		System.out.println(sql);
        stmt.execute(sql);
    }
    public static String getEventsAsJSArray() throws SQLException
    {
        ArrayList<String> results = new ArrayList();
        Connection db = Util.getCredentialedDataSource().getConnection();
        ResultSet rs = db.createStatement().executeQuery("SELECT name, id, host, source, creator, recurring, starttime, endtime, location, on_campus FROM events");
        while(rs.next())
        {
            String name = rs.getString(1);
            int id = rs.getInt(2);
            String host = rs.getString(3);
            String source = rs.getString(4);
            String creator = rs.getString(5);
            boolean recurring = rs.getBoolean(6);
            long starttime = rs.getDate(7).getTime();
            long endtime = rs.getDate(8).getTime();
            String location = rs.getString(9);
            boolean on_campus = rs.getBoolean(10);
            String tagsJSArray = "[]"; // TODO: get tags from database
            results.add(String.format("{name: \"%s\", id: %d, host: \"%s\", source: \"%s\", creator: \"%s\", recurring: %b, starttime: %d, endtime: %d, location: \"%s\", on_campus: %b, tags: %s}", name, id, host, source, creator, recurring, starttime, endtime, location, on_campus, tagsJSArray));
        }
        return "[" + Util.joinList(results, ", ") + "]";
    }
}
