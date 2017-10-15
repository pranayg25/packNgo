package com.websitename.rest;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {
	private String status;
	private String message;
	private List<T> objectList;
	
	
	public Response() {
		super();
		objectList = new ArrayList<T>();
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<T> getObjectList() {
		return objectList;
	}
	public void setObjectList(List<T> objectList) {
		this.objectList = objectList;
	}
	
	
}