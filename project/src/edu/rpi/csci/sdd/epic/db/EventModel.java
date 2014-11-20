package edu.rpi.csci.sdd.epic.db;

import java.util.LinkedHashMap;
import java.util.Map;

import java.sql.*;
import javax.sql.DataSource;
import java.util.ArrayList;

import edu.rpi.csci.sdd.epic.util.Util;

public class EventModel
{
    public static void createEvent(String name, String host, String source, String creator, boolean recurring, long starttime, long endtime, String location, boolean on_campus) throws SQLException
    {
	System.out.println(new Date(starttime).toString());
        Map<String, Object> vals = new LinkedHashMap<String, Object>();
        vals.put("name", name);
        vals.put("host", host);
        vals.put("source", source);
        vals.put("creator", creator);
        vals.put("recurring", recurring);
        vals.put("starttime", new Timestamp(starttime));
        vals.put("endtime", new Timestamp(endtime));
        vals.put("location", location);
        vals.put("on_campus", on_campus);
        GenericModel.insert(DBUtil.getCredentialedDataSource(), "events", vals);
    }
	public static boolean checkForDuplicateEvent(String name, long starttime, long endtime) throws SQLException
	{
		Connection db = DBUtil.getCredentialedDataSource().getConnection();
		PreparedStatement stmt = null;
		String query = "SELECT * FROM events WHERE name = ? AND starttime = ? AND endtime = ?";
		stmt = db.prepareStatement(query);
		stmt.setString(1, name);
		stmt.setTimestamp(2, new Timestamp(starttime));
		stmt.setTimestamp(3, new Timestamp(endtime));
		//System.out.println(query);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) return true;
		else return false;
	} 
    public static String getEventsAsJSArray() throws SQLException
    {
        ArrayList<String> results = new ArrayList();
        Connection db = DBUtil.getCredentialedDataSource().getConnection();
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
                long starttime = rs.getTimestamp(7).getTime();
                long endtime = rs.getTimestamp(8).getTime();
                String location = rs.getString(9);
                boolean on_campus = rs.getBoolean(10);
                String tagsJSArray = getTagsArray(id);
                results.add(String.format("{name: \"%s\", id: %d, host: \"%s\", source: \"%s\", creator: \"%s\", recurring: %b, starttime: %d, endtime: %d, location: \"%s\", on_campus: %b, tags: %s}", name, id, host, source, creator, recurring, starttime, endtime, location, on_campus, tagsJSArray));
            }
        }
        finally { db.close(); }
        return Util.joinIterable(results, ", ", "[", "]");
    }
    public static String getTagsArray(int eventId) throws SQLException
    {/*var events = <!--INSERT-EVENTS-DB-AS-JS-ARRAY-->;*/
        ArrayList<String> results = new ArrayList();
        Connection db = DBUtil.getCredentialedDataSource().getConnection();
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
        return Util.joinIterable(results, ", ", "[", "]");
    }
}
