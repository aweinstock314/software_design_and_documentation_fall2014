package edu.rpi.csci.sdd.epic.scraper;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;


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
	
	public Event(String name, Timestamp start, Timestamp end){
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public Timestamp getStart(){
		return start;
	}

	public Timestamp getEnd(){
		return end;
	}

	public String getName(){
		return name;
	}
	
	public void setHost(String host){
		 this.host = host;
	}

	public String getHost(){
		return host;
	}

	public void setRecurring(boolean b){
		this.recurring = b;
	}

	public boolean getRecurring(){
		return recurring;
	}
	
	public void setOnCampus(boolean b){
		this.on_campus = b;
	}

	public boolean getOnCampus(){
		return on_campus;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return source;
	}

	public void setCreator(String creator){
		this.creator = creator;
	}

	public String getCreator(){
		return creator;
	}
	
}

