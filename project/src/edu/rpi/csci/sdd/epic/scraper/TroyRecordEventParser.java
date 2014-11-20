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

public class TroyRecordEventParser extends BaseEventParser{

	public TroyRecordEventParser(String url) throws IOException{
		super(url);
	}

	@Override
	public Vector<Event> getEvents(){
		Document doc = getDoc();

		Vector<Event> events = new Vector<Event>();
		//System.out.println(doc.html());

		Elements eventMiddles = doc.select("div.eventmiddle");
		for(Element e : eventMiddles){
			//System.out.println(e.html());	
			
			Event event = getEventFromElement(e);
			events.add(event);
		}

		return events;
	}

	public Event getEventFromElement(Element e){
		//System.out.println("\nEVENTMIDDLE:\n"+eventStr);
		
		//doc.select(meta[itemprop="url"]).first().attr(content);
		String url = e.select("meta[itemprop=\"url\"]").first().attr("content");
		String name = e.select("meta[itemprop=\"name\"]").first().attr("content");
		//System.out.println(name);

		try{
			Document inner_url_doc = Jsoup.connect(url).timeout(0).get();
			String event_info = inner_url_doc.select("div.jcal_categories").first().html();

			//Match the string to a start and end timestamp.
			Pattern time_pattern = Pattern.compile( "([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9] (am|pm)");
			//String = Wed. 19 Nov, 2014 5:00 pm - 6:00 pm
			Pattern date_pattern = Pattern.compile( "([0-9]|[0-2][0-9]|3[0-1]) (Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec), (2[0-9][0-9][0-9])");
			
			String date = "";
			String start_time = "";
			String end_time = "";

			//try to match date_pattern
			Matcher date_matcher = date_pattern.matcher(event_info);
			Matcher time_matcher = time_pattern.matcher(event_info);
			if(date_matcher.find()){
				date = date_matcher.group();
			}
			if(time_matcher.find()){
				start_time = time_matcher.group();
			}
			if(time_matcher.find()){
				end_time = time_matcher.group();
			}

			SimpleDateFormat date_format = new SimpleDateFormat("dd MMM, yyyy hh:mm a");
			Date start_date = date_format.parse(date + " " + start_time);
			Date end_date = date_format.parse(date + " " + end_time);
			//System.out.println("Start time: " + date + " " + start_time);
			//System.out.println("End time: " + date + " " + end_time);

			Event event = new Event(name, new Timestamp(start_date.getTime()), new Timestamp(end_date.getTime()));
			return event;

		}catch(IOException exc){
			System.err.println("error connecting and getting information from: " + url);
		}catch(ParseException p){
			System.err.println("error trying to parse out the date and time from: " + url);
		}

		return null;

	}

	
}
