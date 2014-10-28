package edu.rpi.csci.sdd.epic.db;

import java.sql.*;
import javax.sql.DataSource;

import edu.rpi.csci.sdd.epic.util.Util;

public class AccountModel
{
    public static void createAccount(String id, boolean event_provider, String email, String username, String password) throws SQLException
    {
        Connection db = Util.getCredentialedDataSource().getConnection();
        try
        {
            PreparedStatement stmt = db.prepareStatement("INSERT INTO users (id, event_provider, email_address, username, password) VALUES ( ?, ?, ?, ?, ? )");
            stmt.setString(1, id);
            stmt.setBoolean(2, event_provider);
            stmt.setString(3, email);
            stmt.setString(4, username);
            stmt.setString(5, password);
            stmt.execute();
        }
        finally { db.close(); }
    }
}
