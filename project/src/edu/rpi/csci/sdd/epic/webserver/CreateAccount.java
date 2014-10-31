package edu.rpi.csci.sdd.epic.webserver;

import java.util.Map;

import edu.rpi.csci.sdd.epic.db.AccountModel;

// CreateAccount creates an account in the database with information pulled from a post request
public class CreateAccount extends PostRequestProcessor
{
    protected static final String successPage =
        "<html><head><title>Account created</title></head><body>Account created successfully.</body></html>";

    protected String processPostRequest(Map<String, String> postPairs) throws Exception
    {
        // extract parameters from the provided map
        String id = postPairs.get("createaccount_id");
        boolean event_provider = parseCheckbox(postPairs.get("createaccount_event_provider"));
        String email_address = postPairs.get("createaccount_email_address");
        String username = postPairs.get("createaccount_username");
        String password = postPairs.get("createaccount_password");
        // create the account
        AccountModel.createAccount(id, event_provider, email_address, username, password);
        // return the page to display
        return successPage;
    }
}
