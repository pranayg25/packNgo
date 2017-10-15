package com.websitename.objects;

import java.util.List;

import com.websitename.utils.Utils;

public class PlanQuestionsObj {

	private String location;

	private String lat;

	private String lon;

	private String dateStart;

	private String dateEnd;

	private int hours;

	private String userId;
	
	
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	private List<String> activities;
	
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {		
		this.dateStart = Utils.formatDate(dateStart); 
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = Utils.formatDate(dateEnd);
	}
	public List<String> getActivities() {
		return activities;
	}
	public void setActivities(List<String> activities) {
		this.activities = activities;
	}

}
