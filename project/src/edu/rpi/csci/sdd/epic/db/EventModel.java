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
        try
        {
            PreparedStatement stmt = db.prepareStatement("INSERT INTO events (name, host, source, creator, recurring, starttime, endtime, location, on_campus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setObject(1, name);
            stmt.setObject(2, host);
            stmt.setObject(3, source);
            stmt.setObject(4, creator);
            stmt.setObject(5, recurring);
            stmt.setObject(6, new Date(starttime));
            stmt.setObject(7, new Date(endtime));
            stmt.setObject(8, location);
            stmt.setObject(9, on_campus);
            stmt.execute();
        }
        finally { db.close(); }
    }
    public static String getEventsAsJSArray() throws SQLException
    {
        ArrayList<String> results = new ArrayList();
        Connection db = Util.getCredentialedDataSource().getConnection();
        try
        {
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
                String tagsJSArray = getTagsArray(id);
                results.add(String.format("{name: \"%s\", id: %d, host: \"%s\", source: \"%s\", creator: \"%s\", recurring: %b, starttime: %d, endtime: %d, location: \"%s\", on_campus: %b, tags: %s}", name, id, host, source, creator, recurring, starttime, endtime, location, on_campus, tagsJSArray));
            }
        }
        finally { db.close(); }
        return "[" + Util.joinList(results, ", ") + "]";
    }
    public static String getTagsArray(int eventId) throws SQLException
    {
        ArrayList<String> results = new ArrayList();
        Connection db = Util.getCredentialedDataSource().getConnection();
        try
        {
            ResultSet rs = db.createStatement().executeQuery(String.format("SELECT tag FROM event_tags WHERE id='%d'", eventId));
            while(rs.next())
            {
                String tag = "\"" + rs.getString(1) + "\"";
                results.add(tag);
            }
        }
        finally { db.close(); }
        return "[" + Util.joinList(results, ", ") + "]";
    }
}
