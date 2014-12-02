package edu.rpi.csci.sdd.epic.webserver;

import java.util.Map;

import edu.rpi.csci.sdd.epic.db.EventModel;

// CreateEvent creates an event in the database with information pulled from a post request
public class CreateEvent extends PostRequestProcessor
{
    // Creates stringified success page with message indicating the event was created.
    protected static final String successPage = "{\"success\": true}";
    protected static final String failurePage = "{\"success\": false}";

    protected String processPostRequest(Map<String, String> postPairs) throws Exception
    {
        // extract parameters from the provided map
        String name = postPairs.get("name");
        String host = postPairs.get("host");
        String source = postPairs.get("source");
        String creator = "not yet implemented";
        boolean recurring = parseCheckbox(postPairs.get("recurring"));
        long starttime, endtime;
        try
        {
            starttime = Long.valueOf(postPairs.get("starttime"));
            endtime = Long.valueOf(postPairs.get("endtime"));
        }
        catch(NumberFormatException nfe)
        {
            return failurePage;
        }
        String location = postPairs.get("location");
        boolean oncampus = parseCheckbox(postPairs.get("oncampus"));
        // create the event
        EventModel.createEvent(name, host, source, creator, recurring, starttime, endtime, location, oncampus);
        // return the page to display
        return successPage;
    }
}
