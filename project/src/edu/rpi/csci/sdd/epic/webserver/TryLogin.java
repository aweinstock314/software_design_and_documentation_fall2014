package edu.rpi.csci.sdd.epic.webserver;

import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;

import edu.rpi.csci.sdd.epic.db.AccountModel;

// TryLogin tries to login with information pulled from a post request
public class TryLogin extends PostRequestProcessor
{
    // constants to be served on success/failure
    protected static final String successPage = "SUCCESS";
    protected static final String failurePage = "FAILURE";

    protected String processPostRequest(Map<String, String> postPairs) throws Exception
    {
        // extract parameters from the provided map
        String username = postPairs.get("username");
        String password = postPairs.get("password");
        // conditionally serve success/failure constants based on a login attempt
        return (checkCredentials(username, password) ? successPage : failurePage);
    }

    // Attempt a login
    public boolean checkCredentials(String username, String password)
    {
        System.out.printf("Handling a login: (\"%s\", \"%s\")\n", username, password);
        try
        {
            // prepare the token for Shiro to use
            // TODO: hashing
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            Subject currentUser = SecurityUtils.getSubject();
            // execute the login
            currentUser.login(token);
            currentUser.logout();
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
