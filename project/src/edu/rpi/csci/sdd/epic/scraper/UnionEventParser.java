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

public class UnionEventParser extends BaseEventParser{

	public UnionEventParser(String url) throws IOException{
		super(url);
	}

	@Override
	public Vector<Event> getEvents(){
		Document doc = getDoc();

		Vector<Event> events = new Vector<Event>();
		Elements dayOfEvents = doc.select("a.dayLink");

		for(Element day : dayOfEvents){
			//get the date (in a different location than the rest of the event info.
			String date = day.attr("href");
			date = date.substring(date.indexOf("date=")+5);

			for(Element event : day.parent().select("a.eventLinkA")){
				events.add(getEventfromString(event.select("span").first().html(), date));
			}
			for(Element event : day.parent().select("a.eventLinkB")){
				events.add(getEventfromString(event.select("span").first().html(), date));
			}
		}
		return events;
	}

	private Event getEventfromString(String eventString, String date){
		String time = eventString.substring(eventString.indexOf("Time:"));
		time = time.substring(6, time.indexOf("<br>"));
		
		//creating the start and end timestamps of the event (there are several formats we need to handle)
		Pattern MultiDayTimePattern = Pattern.compile("([0-9]|0[0-9]|1[0-2])/([0-9]|[0-2][0-9]|3[0-1]) ([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9] (AM|PM)");
		Pattern SingleDayTimePattern = Pattern.compile("([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9] (AM|PM)");
		
		SimpleDateFormat multidayformat = new SimpleDateFormat("yyyy MM/dd hh:mm a");
		SimpleDateFormat singledayformat = new SimpleDateFormat("yyyyMMdd hh:mm a");

		boolean multiday = false;
		Date startdate = new Date(0);
		Date enddate = new Date(0);

		try{

			Matcher multiMatcher = MultiDayTimePattern.matcher(time);
			if(multiMatcher.find()){
				multiday = true;
				startdate = multidayformat.parse(date.substring(0, 4) + " " + multiMatcher.group());
			}
			if(multiMatcher.find()){
				enddate = multidayformat.parse(date.substring(0, 4) + " " + multiMatcher.group());
			}
		
			//this will match if it is not a multi-day event, and the time is added to the date to create a timestamp.
			Matcher singleMatcher = SingleDayTimePattern.matcher(time);
			if(!multiday){
				if(singleMatcher.find()){
					startdate = singledayformat.parse(date + " " + singleMatcher.group());
				}
				if(singleMatcher.find()){
					enddate = singledayformat.parse(date + " " + singleMatcher.group());
				}
			}
	
		}catch(ParseException e){
			System.err.println("Unable to parse out timestamp for event.");
		}
		
		String eventName = eventString.substring(eventString.indexOf("<strong>")+ 8, eventString.indexOf("</strong>"));

		String location = eventString.substring(eventString.indexOf("Location: "));
		location = location.substring(10, location.indexOf("<br>"));
		
		Event e = new Event(eventName, new Timestamp(startdate.getTime()), new Timestamp(enddate.getTime()));
		e.setLocation(location);
		return e;

	}
}
