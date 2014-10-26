package edu.rpi.csci.sdd.epic.webserver;

import java.util.Map;

import edu.rpi.csci.sdd.epic.db.EventModel;

public class CreateEvent extends PostRequestProcessor
{
    protected String processPostRequest(Map<String, String> postPairs) throws Exception
    {
		String name = postPairs.get("name");
        String host = postPairs.get("host");
        String source = postPairs.get("source");
        String creator = "not yet implemented";
        boolean recurring = parseCheckbox(postPairs.get("recurring"));
        long starttime = Long.valueOf(postPairs.get("starttime"));
        long endtime = Long.valueOf(postPairs.get("endtime"));
        String location = postPairs.get("location");
        boolean oncampus = parseCheckbox(postPairs.get("oncampus"));
        EventModel.createEvent(name, host, source, creator, recurring, starttime, endtime, location, oncampus);
        return "<html><head><title>Event created</title></head><body>Event created successfully.</body></html>";
    }
}
