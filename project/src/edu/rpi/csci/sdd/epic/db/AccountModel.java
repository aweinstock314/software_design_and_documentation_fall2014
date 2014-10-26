package edu.rpi.csci.sdd.epic.db;

import java.sql.*;
import javax.sql.DataSource;

import edu.rpi.csci.sdd.epic.util.Util;

public class AccountModel
{
    public static void createAccount(String id, boolean event_provider, String email, String username, String password) throws SQLException
    {
        Connection db = Util.getCredentialedDataSource().getConnection();
        Statement stmt = db.createStatement();
        stmt.execute(String.format("INSERT INTO users (id, event_provider, email_address, username, password) VALUES ( '%s', '%b', '%s', '%s', '%s' )", id, event_provider, email, username, password));
    }
}
