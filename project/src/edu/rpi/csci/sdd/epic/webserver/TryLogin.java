package edu.rpi.csci.sdd.epic.webserver;

import java.util.ArrayList;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import edu.rpi.csci.sdd.epic.db.AccountModel;
import edu.rpi.csci.sdd.epic.db.DBUtil;

// TryLogin tries to login with information pulled from a post request
public class TryLogin extends PostRequestProcessor
{
    // constants to be served on success/failure
    //protected static final String successPage = "SUCCESS";
    //protected static final String failurePage = "FAILURE";

    protected String processPostRequest(Map<String, String> postPairs) throws Exception
    {
        // extract parameters from the provided map
        String username = postPairs.get("username");
        String password = postPairs.get("password");
        // conditionally serve success/failure constants based on a login attempt
        if(checkCredentials(username, password))
        {
            JSONObject response = new JSONObject();
            // TODO: return and store unique token, use for validation of set requests
            response.put("unique_user_token", "0xdeadbeef");
            response.put("username", username);
            ArrayList<String> filters = AccountModel.getTagsForUser(DBUtil.getCredentialedDataSource(), username);
            response.put("stored_tags", filters);
            System.out.println(response.toJSONString());
            return response.toJSONString();
        }
        else
        {
            return JSONValue.toJSONString(false);
        }
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

        // As usual, print stack trace on exception.
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
