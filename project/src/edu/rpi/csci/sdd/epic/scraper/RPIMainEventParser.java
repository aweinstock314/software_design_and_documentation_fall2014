package edu.rpi.csci.sdd.epic.scraper;
import java.io.*;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//scrapes events from the RPI main academic calendar.
public class RPIMainEventParser extends BaseEventParser{
	
	//The constructor for the parser that initializes the values in the superclass
	public RPIMainEventParser(String url) throws IOException{
		super(url);
	}

	//gets all the events from the website.
	@Override
	public Vector<Event> getEvents(){
		Document doc = getDoc();
		Vector<Event> events = new Vector<Event>();

		Elements items = doc.select("item");
		for(Element e : items){
			//safeguard so unparseable events are not added.
			if(getEventFromElement(e)!=null)
				events.add(getEventFromElement(e));
		}

		return events;
	}

	//parses out the event information and creates an Event object based on a given html element.
	public Event getEventFromElement(Element elem){

		//parsing out the name and location of the event.
		String name = elem.select("title").first().html();
		String location = elem.html().substring(elem.html().indexOf("Where:")+6);
		location = location.substring(0, location.indexOf("&lt"));

		//limiting the length of the name so it can be stored in the database.
		if(name.length()>80) name = name.substring(0,79);

		try{
			//Regex patterns to match the start dates and times
			Pattern datetimePattern = Pattern.compile("(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday), (January|February|March|April|May|June|July|August|September|October|November|December) ([0-9]|[0-2][0-9]|3[0-1]), (2[0-9][0-9][0-9]) ([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9] (AM|PM) - ([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9] (AM|PM)");
			Matcher datetimeMatcher = datetimePattern.matcher(elem.html());

			Pattern datePattern = Pattern.compile("(January|February|March|April|May|June|July|August|September|October|November|December) ([0-9]|[0-2][0-9]|3[0-1]), (2[0-9][0-9][0-9])");
			Pattern timePattern = Pattern.compile("([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9] (AM|PM)");

			SimpleDateFormat dateformat = new SimpleDateFormat("MMMMM dd, yyyy hh:mm a");


			String datetime = "";
			String date = "";
			String starttime = "";
			String endtime = "";
	
			//parsing out the start dates and times for the event from the website.
			if(datetimeMatcher.find()){
				//System.out.println("found a match");
				datetime = datetimeMatcher.group();
				Matcher dateMatcher = datePattern.matcher(datetime);
				if(dateMatcher.find()){
					date = dateMatcher.group();
				}
				Matcher timeMatcher = timePattern.matcher(datetime);
				if(timeMatcher.find()){
					starttime = timeMatcher.group();
				}
				if(timeMatcher.find()){
					endtime = timeMatcher.group();			
				}

				//System.out.println(date + " " + starttime);
				Date startdate = dateformat.parse(date + " " + starttime);
				Date enddate = dateformat.parse(date + " " + endtime);
				System.out.println(new Timestamp(startdate.getTime()).toString());
				Event event = new Event(name, new Timestamp(startdate.getTime()), new Timestamp(enddate.getTime()));
				event.setSource("http://events.rpi.edu/webcache/v1.0/rssDays/7/list-rss/no--filter.rss");
				event.setCreator("RPIMainEventParser");
				event.setHost("RPIMain");
				
				event.setLocation(location);
				return event;
			
			}
			else{
				//for currently unparseable events.
				return null;
			}

		}catch(ParseException e){
			System.err.println("There was an error parsing event: " + name);
		}

		return null;

	}

}
