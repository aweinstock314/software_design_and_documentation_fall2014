package edu.rpi.csci.sdd.epic.webserver;

import java.util.Map;

import edu.rpi.csci.sdd.epic.db.AccountModel;

// Creates an account in the database with information pulled from a post request
public class CreateAccount extends PostRequestProcessor
{
    // Generates brief html page displaying success.
    protected static final String successPage = "{success: true}";
    protected static final String failurePage = "{success: false}";

    protected String processPostRequest(Map<String, String> postPairs) throws Exception
    {
        // extract parameters from the provided map
        String id = postPairs.get("createaccount_id");
        boolean event_provider = parseCheckbox(postPairs.get("createaccount_event_provider"));
        String email_address = postPairs.get("createaccount_email_address");
        String username = postPairs.get("createaccount_username");
        String password = postPairs.get("createaccount_password");
        // create the account
        if(AccountModel.createAccount(id, event_provider, email_address, username, password))
        {
            return successPage;
        }
        else { return failurePage; }
    }
}
