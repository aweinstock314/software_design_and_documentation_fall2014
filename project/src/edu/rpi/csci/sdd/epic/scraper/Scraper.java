
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

	private Vector<BaseEventParser> eventParsers;

	//connects to the website, gets the html, and parses out the events.
	public Scraper() throws IOException, SQLException{
		eventParsers = new Vector<BaseEventParser>();

		UnionEventParser unionParser = new UnionEventParser("http://events.rpi.edu/union/main/showMain.rdo");
		TroyRecordEventParser troyParser = new TroyRecordEventParser("http://downtowntroy.org/special-events/events-calendar.html");
		RPIMainEventParser rpiParser = new RPIMainEventParser("http://events.rpi.edu/webcache/v1.0/rssDays/7/list-rss/no--filter.rss");
		eventParsers.add(troyParser);
		eventParsers.add(unionParser);
		eventParsers.add(rpiParser);
	}
	
	//run each parser and store results in the database.
	public void scrape() throws SQLException{
		for(BaseEventParser p : eventParsers){
			for(Event e : p.getEvents()){
				if(EventModel.checkForDuplicateEvent(e.getName().replace("\'", ""), e.getStart().getTime(), e.getEnd().getTime())){
					System.out.println("DUPLICATE: " + e.getName());
				}
				else{
					EventModel.createEvent(e.getName().replace("\'", ""), e.getHost(), e.getSource(), e.getCreator(),  e.getRecurring(), e.getStart().getTime(), e.getEnd().getTime(), e.getLocation(), true);
					System.out.println(e.getStart().toString());
					System.out.println(e.getName());
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException, ParseException, SQLException{
		Scraper scraper = new Scraper();
		scraper.scrape();
	}
}

