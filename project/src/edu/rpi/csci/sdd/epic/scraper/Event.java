package edu.rpi.csci.sdd.epic.scraper;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

//provides a representation of the Event object.
public class Event {

	private String host;
	private String name;
	private Timestamp start;
	private Timestamp end;
	private String location;
	private Boolean recurring;
	private Boolean on_campus;
	private String source;
	private String creator;
	
	//create the event with the given values for required variables, and the default values for the optional variables.
	public Event(String name, Timestamp start, Timestamp end){
		//required variables
		this.name = name;
		this.start = start;
		this.end = end;

		//default values
		this.host = "unknown";
		this.location = "unknown";
		this.recurring = false;
		this.on_campus = true;
		this.source = "unknown";
		this.creator = "unknown";
	}

	//getters and setters for each variable:
	
	//Location of the event
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	//start and end times and dates for the event.
	public Timestamp getStart(){
		return start;
	}

	public Timestamp getEnd(){
		return end;
	}

	//title of the event.
	public String getName(){
		return name;
	}
	
	//the organization that is hosting the event.
	public void setHost(String host){
		 this.host = host;
	}

	public String getHost(){
		return host;
	}

	//whether the event is recurring
	public void setRecurring(boolean b){
		this.recurring = b;
	}

	public boolean getRecurring(){
		return recurring;
	}
	
	//whether the event is on or off RPI campus
	public void setOnCampus(boolean b){
		this.on_campus = b;
	}

	public boolean getOnCampus(){
		return on_campus;
	}

	//the source website of the event that the scraper used.
	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return source;
	}

	//the creator of the event if a user created it.
	public void setCreator(String creator){
		this.creator = creator;
	}

	public String getCreator(){
		return creator;
	}
	
}

