/**
 * 
 */
package com.websitename.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author KARTIK
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		  try {
			  HttpClient client = new DefaultHttpClient();
			  HttpGet get = new HttpGet("http://localhost:8088/Server/rest/test");
			  get.addHeader("Content-Type","Application/json");
			  HttpResponse response = client.execute(get);
			  BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			  String line = "";
			  
			  while ((line = rd.readLine()) != null) {
			     System.out.println(line);
			    }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		   
	  }

	}


