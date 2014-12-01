package edu.rpi.csci.sdd.epic.webserver;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import edu.rpi.csci.sdd.epic.db.AccountModel;
import edu.rpi.csci.sdd.epic.db.DBUtil;

public class SaveUserTags extends PostRequestProcessor
{
    protected static final String successPage = "{success: true}";
    protected static final String failurePage = "{success: false}";

    protected TryLogin tl;
    public SaveUserTags(TryLogin x) { tl = x; }

    // Post request processor used for persisting tags across logins
    protected String processPostRequest(Map<String, String> postPairs) throws Exception
    {
        // Receive a username/token pair (token prevents/mitigates CSRF vulnerabilities)
        // https://www.owasp.org/index.php/Cross-Site_Request_Forgery_%28CSRF%29_Prevention_Cheat_Sheet
        String username = URLDecoder.decode(postPairs.get("username"));
        String token = URLDecoder.decode(postPairs.get("token"));
        String expectedToken = tl.getCSRFToken(username);
        if(token.equals(expectedToken))
        {
            JSONArray filters = (JSONArray)JSONValue.parse(URLDecoder.decode(postPairs.get("filters")));

            // Print username + filters for debug purposes.
            System.out.printf("Username: \"%s\"\nFilters: \"%s\"\n\n", username, filters);

            // Set up tags and output success page.
            AccountModel.setTagsForUser(DBUtil.getCredentialedDataSource(), username, new ArrayList<String>(filters));
            return successPage;
        }
        else
        {
            System.out.printf("Received a potentially forged SaveUserTags request"+
                "(received token \"%s\", expected \"%s\").\n", token, expectedToken);
            return failurePage;
        }
    }
}
