package edu.rpi.csci.sdd.epic.webserver;

import java.util.Map;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import edu.rpi.csci.sdd.epic.db.AccountModel;

public class CreateAccount extends PostRequestProcessor
{
    protected String processPostRequest(Map<String, String> postPairs) throws Exception
    {
        String id = postPairs.get("createaccount_id");
        boolean event_provider = Boolean.valueOf(postPairs.get("createaccount_eventprovider"));
        String email_address = postPairs.get("createaccount_email_address");
        String username = postPairs.get("createaccount_username");
        String password = postPairs.get("createaccount_password");
        AccountModel.createAccount(id, event_provider, email_address, username, password);
        return "<html><head><title>Account created</title></head><body>Account created successfully.</body></html>";
    }
}
