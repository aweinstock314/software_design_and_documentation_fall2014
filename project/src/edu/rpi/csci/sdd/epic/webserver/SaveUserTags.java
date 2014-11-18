package edu.rpi.csci.sdd.epic.webserver;

import java.net.URLDecoder;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import edu.rpi.csci.sdd.epic.db.AccountModel;

public class SaveUserTags extends PostRequestProcessor
{
    protected static final String successPage =
        "<html><head><title>Account created</title></head><body>Account created successfully.</body></html>";

    protected String processPostRequest(Map<String, String> postPairs) throws Exception
    {
        JSONArray filters = (JSONArray)JSONValue.parse(URLDecoder.decode(postPairs.get("filters")));
        System.out.println(filters);
        return "a string";
    }
}
