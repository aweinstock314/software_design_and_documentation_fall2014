package edu.rpi.csci.sdd.epic.db;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import java.sql.*;
import javax.sql.DataSource;

import edu.rpi.csci.sdd.epic.util.Util;

public class AccountModel
{
    // userExists(ds, username) checks to see if the user 
    //  named by username already exists in the database 
    //  represented by ds
    public static boolean userExists(DataSource ds, String username) throws SQLException
    {
        Connection db = ds.getConnection();
        try
        {
            PreparedStatement ps = db.prepareStatement("SELECT username FROM users WHERE username = ?");
            ps.setString(1, username);
            return ps.executeQuery().next();
        }
        finally { db.close(); }
    }
    // createAccount, given account parameters, calls 
    //  GenericModel.insert to add account to "users" table in 
    //  database.  Values extracted from javascript create account 
    //  form via CreateAccount HttpHandler, using POST Values. may 
    //  also be entered via command-line create account tool - used 
    //  primarily in testing.
    public static void createAccount(String id, boolean event_provider, String email, String username, String password) throws SQLException
    {
        DataSource ds = DBUtil.getCredentialedDataSource();
        if(!userExists(ds, username))
        {
            // set up argument for GenericModel.insert
            Map<String, Object> vals = new LinkedHashMap<String, Object>();
            vals.put("id", id);
            vals.put("event_provider", event_provider);
            vals.put("email_address", email);
            vals.put("username", username);
            vals.put("password", password);
            // insert the account data into the database
            GenericModel.insert(ds, "users", vals);
        }
        else
        {
            // TODO: more elegant return mechanism?
            throw new RuntimeException(String.format("User \"%s\" already exists in the database.", username));
        }
    }
    // getUsersTable() returns all values in the users table. Used primarily in testing.
    public static ArrayList<String> getUsersTable() throws SQLException
    {
        ArrayList<String> results = new ArrayList();
        // make a connection to the database
        Connection db = DBUtil.getCredentialedDataSource().getConnection();
        // get a list of data on the users
        String query = "SELECT username, event_provider, email_address FROM USERS";
        ResultSet rs = db.createStatement().executeQuery(query);
        // loop over the each user's data
        while(rs.next())
        {
            // extract the user's data from the database ResultSet object
            String username = rs.getString(1);
            Boolean isEventProvider = rs.getBoolean(2);
            String email = rs.getString(3);
            // append the user's data as a string to the results array
            results.add(String.format("(%s, %b, %s)", username, isEventProvider, email));
        }
        return results;
    }
}
