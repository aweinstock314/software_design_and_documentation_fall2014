package edu.rpi.csci.sdd.epic.webserver;

import java.util.*;
import java.net.InetSocketAddress;

// imports from http://shiro.apache.org/tutorial.html
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.BasicAuthenticator;

import edu.rpi.csci.sdd.epic.db.DBUtil;
import edu.rpi.csci.sdd.epic.util.Util;

// possibly rename this class? try and find/write a 
//  DigestAuthenticator impl, since Basic is just 
//   base64("username:password")
public class UserSessions extends BasicAuthenticator
{
    // Constructor
    public UserSessions(String realm)
    {
        super(realm);
    }

    // sets the security manager for a secure connection to the database
    public static void setSecurityManager()
    {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("sample_accounts.ini");
        SecurityManager securityManager = factory.getInstance();
        JdbcRealm databaseRealm = new JdbcRealm();
        databaseRealm.setDataSource(DBUtil.getCredentialedDataSource());
        ((RealmSecurityManager)securityManager).setRealm(databaseRealm);
        SecurityUtils.setSecurityManager(securityManager);
    }

    // Handle the user's secure login to the database.
    protected Map<InetSocketAddress, Subject> addressSubjectMapping = new HashMap<InetSocketAddress, Subject>();
    public void doLogin(HttpExchange e)
    {
        InetSocketAddress address = e.getRemoteAddress();
        String username = "username";
        String password = "password";
        System.out.printf("Handling a login for %s: (\"%s\", \"%s\")\n", address, username, password);

        // Checks credentials.
        System.out.printf("Login was %s.\n",
            (checkCredentials(username, password) ? "successful" : "unsuccessful"));
    }

    // Verifies the user's credentials (proper password for username)
    public boolean checkCredentials(String username, String password)
    {
        System.out.printf("Handling a login: (\"%s\", \"%s\")\n", username, password);
        try
        {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);
            return true;
        }

        // Print stack trace on exception.
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
