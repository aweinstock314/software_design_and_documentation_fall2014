package edu.rpi.csci.sdd.epic.db;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import java.sql.*;
import javax.sql.DataSource;

import edu.rpi.csci.sdd.epic.util.Util;

public class AccountModel
{
    public static void createAccount(String id, boolean event_provider, String email, String username, String password) throws SQLException
    {
        Map<String, Object> vals = new LinkedHashMap<String, Object>();
        vals.put("id", id);
        vals.put("event_provider", event_provider);
        vals.put("email_address", email);
        vals.put("username", username);
        vals.put("password", password);
        GenericModel.insert(DBUtil.getCredentialedDataSource(), "users", vals);
    }
    public static ArrayList<String> getUsersTable() throws SQLException
    {
        ArrayList<String> results = new ArrayList();
        Connection db = DBUtil.getCredentialedDataSource().getConnection();
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
}
