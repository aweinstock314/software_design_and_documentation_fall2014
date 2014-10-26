package edu.rpi.csci.sdd.epic.db;

import java.sql.*;
import javax.sql.DataSource;

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
}
