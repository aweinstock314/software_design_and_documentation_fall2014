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
    // Print success page for creating account.
    protected static final String successPage =
        "<html><head><title>Account created</title></head><body>Account created successfully.</body></html>";

    // Post request processor used for signin/signout.
    protected String processPostRequest(Map<String, String> postPairs) throws Exception
    {
        // blindly trust the user on their username (TODO: authentication, possibly send a session token instead of "SUCCESS"/"FAILURE")
        String username = URLDecoder.decode(postPairs.get("username"));
        JSONArray filters = (JSONArray)JSONValue.parse(URLDecoder.decode(postPairs.get("filters")));

        // Print username + filters for debug purposes.
        System.out.printf("Username: \"%s\"\nFilters: \"%s\"\n\n", username, filters);

        // Set up tags and output success page.
        AccountModel.setTagsForUser(DBUtil.getCredentialedDataSource(), username, new ArrayList<String>(filters));
        return successPage;
    }
}
