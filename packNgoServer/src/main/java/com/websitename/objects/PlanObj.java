package com.websitename.objects;

import java.util.ArrayList;
import java.util.List;

public class PlanObj {
	
	private String date;
	private String city;
	private String startDate;
	private String endDate;
	private String id;
	
	private List<LocationObj> locations;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<LocationObj> getLocations() {
		return locations;
	}
	public void setLocations(List<LocationObj> locations) {
		this.locations = locations;
	}
	public void addLocation(LocationObj location) {
		if(locations==null){
			locations = new ArrayList<>();
		}
		this.locations.add(location);
	}	
	
}
