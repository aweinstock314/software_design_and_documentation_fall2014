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
import edu.rpi.csci.sdd.epic.db.EventModel;

public class Scraper {
	
	//get the date from the date format used by the union website
	public Date getDate(String date) throws ParseException{
		date = date.substring(date.indexOf("date=")+5);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date d = format.parse(date);
//		SimpleDateFormat format2 = new SimpleDateFormat("h:mm a z");
//		Date d2 = format2.parse("6:30 PM ");
//		System.out.println(d.toString());
//		System.out.println(d2.toString());
//		Date d3 = new Date(d.getTime()+d2.getTime());
//		System.out.println(d3.toString());
		return d;
	}
	
	//get the event from the string used in the union website
	public Event getEvent(String eventString, String date) throws ParseException{
		System.out.println(eventString);
		
		String time = eventString.substring(eventString.indexOf("Time:"));
		time = time.substring(6, time.indexOf("<br>"));
		
		//Day time used for multi-day events
		Pattern DayTimePattern = Pattern.compile("([0-9]|0[0-9]|1[0-2])/([0-9]|[0-2][0-9]|3[0-1]) ([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9] (AM|PM)");
		
		//time pattern used for single-day events
		Pattern TimePattern = Pattern.compile("([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9] (AM|PM)");
		
		
		boolean multiday = false;
		
		SimpleDateFormat multidayformat = new SimpleDateFormat("yyyy MM/dd hh:mm a");
		SimpleDateFormat singledayformat = new SimpleDateFormat("yyyyMMdd hh:mm a");
		//this will match if it is a multi-day event.
		
		Date startdate = new Date(0);
		Date enddate = new Date(0);
		
		Matcher matcher = DayTimePattern.matcher(time);
		if(matcher.find()){
			multiday = true;
			startdate = multidayformat.parse(date.substring(0, 4) + " " + matcher.group());
			System.out.println(startdate.toString());
		}
		if(matcher.find()){
			enddate = multidayformat.parse(date.substring(0, 4) + " " + matcher.group());
			System.out.println(enddate.toString());
		}
		
		//this will match if it is not a multi-day event, and the time is added to the date to create a timestamp.
		Matcher matcher2 = TimePattern.matcher(time);
		if(!multiday){
			if(matcher2.find()){
				startdate = singledayformat.parse(date + " " + matcher2.group());
				System.out.println(startdate.toString());
			}
			if(matcher2.find()){
				enddate = singledayformat.parse(date + " " + matcher2.group());
				System.out.println(enddate.toString());
			}
		}
		
		String name = eventString.substring(eventString.indexOf("<strong>")+ 8, eventString.indexOf("</strong>"));
		System.out.println(name);
		
		String location = eventString.substring(eventString.indexOf("Location: "));
		location = location.substring(10, location.indexOf("<br>"));
		System.out.println(location);
		
		Event e = new Event(name, new Timestamp(startdate.getTime()), new Timestamp(enddate.getTime()));
		e.setLocation(location);
		return e;
	}

	//connects to the website, gets the html, and parses out the events.
	public Scraper() throws IOException, ParseException, SQLException{
		Document doc = Jsoup.connect("http://events.rpi.edu/union/main/showMain.rdo").timeout(0).get();
		String  htmltext = doc.html();
		System.out.println(htmltext);
		
		Vector<Event> events = new Vector<Event>();
		
		Elements days = doc.select("a.dayLink");
		for(Element day : days){
			//Date d = getDate(day.attr("href"));
			
			String date = day.attr("href");
			System.out.println(date);
			date = date.substring(date.indexOf("date=")+5);
			//System.out.println(d.toString());
			//System.out.println(day.parent().html());
			//System.out.println(day.parent().select("a.eventLinkA").first().html());
			for(Element eventA: day.parent().select("a.eventLinkA")){
				//System.out.println(eventA.select("span").first().html());
				events.add(getEvent(eventA.select("span").first().html(), date));
			}
			for(Element eventB: day.parent().select("a.eventLinkB")){
				//System.out.println(eventB.select("span").first().html());
				events.add(getEvent(eventB.select("span").first().html(), date));
			}
			//System.out.println(event.select("span.eventTip").html());
		}


		for(Event e : events){
			EventModel.createEvent(e.getName(), "Union", "http://events.rpi.edu/union/main/showMainEnd.rdo", "UnionScraper", false, e.getStart().getTime(), e.getEnd().getTime(), e.getLocation(), true);
		}
	}
	
	public static void main(String[] args) throws IOException, ParseException, SQLException{
		Scraper scraper = new Scraper();
	}
}
