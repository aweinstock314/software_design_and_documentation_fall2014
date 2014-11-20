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
//import edu.rpi.csci.sdd.epic.db.EventModel;


//the base class for any event parser
public class BaseEventParser{

	private String srcUrl;
	private Document doc;
	private String scraperName;

	//set up the connection, grab the html, parse to jsoup document
	public BaseEventParser(String url) throws IOException{
		srcUrl = url;
		doc = Jsoup.connect(url).timeout(0).get();
	}

	//from the jsoup document, parses the events out and creates a vector of events.
	public Vector<Event> getEvents(){
		Vector<Event> events = new Vector<Event>();
		return events;
	}

	//get the Jsoup Document
	protected Document getDoc(){
		return doc;
	}	

}
