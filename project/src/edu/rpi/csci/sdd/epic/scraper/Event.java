package edu.rpi.csci.sdd.epic.scraper;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;


public class Event {

	private String name;
	private Timestamp start;
	private Timestamp end;
	private String location;
	private Boolean recurring;
	
	public Event(String name, Timestamp start, Timestamp end){
		this.name = name;
		this.start = start;
		this.end = end;
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
	
}

