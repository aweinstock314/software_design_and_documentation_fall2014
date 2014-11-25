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

public class ENIEParser extends BaseEventParser{

	
	public ENIEParser(String url) throws IOException{
		super(url);
	}

	//from the jsoup document, parses the events out and creates a vector of events.
	public Vector<Event> getEvents(){
	
		Document jsoupDoc = getDoc();	

		try{
			String ENIEresults = ENIEInterface.runIETagger(jsoupDoc.html());
			Document enieResultDoc = Jsoup.parse(ENIEresults);
			System.out.println(enieResultDoc.html());
			if(!enieResultDoc.select("event").isEmpty()){
				System.out.println("+++++++++++++++++++++++++++");
				for(Element e : enieResultDoc.select("event")){
					System.out.println("EVENT FROM ENIE: " + e.html());
				}
				System.out.println("+++++++++++++++++++++++++++");
			}
		}catch(Exception e){
			System.err.println("error running the enie tagger");
		}

		Vector<Event> events = new Vector<Event>();
		return events;
	}	
}
