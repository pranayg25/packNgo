package com.websitename.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.websitename.objects.LocationObj;

public class GoogleService {

	//	private static String GOOGLE_KEY = "AIzaSyCjWANZar38sH8jy_yShuBYfhZbWBxHAsU";
	private static String GOOGLE_KEY ="AIzaSyBxlNWdjpL68zx09t9BCOmFlEL_Ai114X4";

	public static List<LocationObj> getActivities(String lat, String lon,String type){
		List<LocationObj> locationObjs = new ArrayList<>();
		try {



			String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?key="+GOOGLE_KEY+"&location="+lat+","+lon+"&radius=50000&type="+type;

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatusLine().getStatusCode());
			}

			//BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			JSONObject jsonObject = (JSONObject)JSONValue.parse(EntityUtils.toString(response.getEntity()));
			JSONArray jsonArray= (JSONArray)jsonObject.get("results");
			for(int i =0;i<jsonArray.size();i++){
				JSONObject obj = (JSONObject)jsonArray.get(i);
				locationObjs.add(getPlaceDetails((String)obj.get("place_id")));
			}

			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {

		}
		return locationObjs;
	}

	public static LocationObj getPlaceDetails(String placeId){
		LocationObj locationObj = new LocationObj();
		try {


			String url = "https://maps.googleapis.com/maps/api/place/details/json?key="+GOOGLE_KEY+"&placeid="+placeId;

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatusLine().getStatusCode());
			}

			//BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			JSONObject jsonObject = (JSONObject)JSONValue.parse(EntityUtils.toString(response.getEntity()));
			JSONObject resultsObj= (JSONObject)jsonObject.get("result");

			locationObj.setName((String) resultsObj.get("name"));
			locationObj.setAddress((String)resultsObj.get("formatted_address"));
			locationObj.setPhoneNo((String)resultsObj.get("international_phone_number"));
			locationObj.setRating(resultsObj.get("rating").toString());
			locationObj.setWebsite((String) resultsObj.get("website"));
			locationObj.setMaps(((String) resultsObj.get("url")));
			locationObj.setLat(((JSONObject)((JSONObject)resultsObj.get("geometry")).get("location")).get("lat").toString());
			locationObj.setLon(((JSONObject)((JSONObject)resultsObj.get("geometry")).get("location")).get("lng").toString());
			locationObj.setImage(getImage((String)((JSONObject)((JSONArray)resultsObj.get("photos")).get(0)).get("photo_reference")));
			locationObj.setPlaceId(placeId);
			
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locationObj;
	}


	public static Map<String,Map<String,Integer>> getDistanceMatrix(List<LocationObj> locations){
		Map<String,Map<String,Integer>> matrix = new HashMap<>();

		try {
			String locationStr = "";
			for(LocationObj location:locations){
				locationStr += location.getLat()+","+location.getLon()+"%7C";
			}

			Map<String,String> locationMap = new TreeMap<>();
			for(LocationObj location:locations){
				locationMap.put(location.getName(), "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+location.getLat()+","+location.getLon()+"&destinations="+locationStr+"&key="+GOOGLE_KEY);
				//				String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+locationStr+"&destinations="+locationStr+"&key="+GOOGLE_KEY;				
			}

			for(Map.Entry<String, String> location:locationMap.entrySet()){
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet getRequest = new HttpGet(location.getValue());
				getRequest.addHeader("accept", "application/json");

				HttpResponse response = httpClient.execute(getRequest);

				if (response.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "+ response.getStatusLine().getStatusCode());
				}

				//BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

				JSONObject jsonObject = (JSONObject)JSONValue.parse(EntityUtils.toString(response.getEntity()));
				JSONArray resultsObjs= (JSONArray)jsonObject.get("rows");
				for(int i=0;i<resultsObjs.size();i++){
					JSONObject object = (JSONObject)resultsObjs.get(i);
					JSONArray distances = (JSONArray) object.get("elements");
					Map<String, Integer> matrixValue = new HashMap<>();
					for(int j = 0;j<distances.size();j++){
						JSONObject distance=(JSONObject) distances.get(j);
						Long dist = (Long)(((JSONObject)distance.get("duration")).get("value"));
						if(dist!=0){
							matrixValue.put(locations.get(j).getName(),dist.intValue());
						}
					}
					matrix.put(location.getKey(), matrixValue);
				}


				httpClient.getConnectionManager().shutdown();				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return matrix;
	}
	
	public static String getImage(String imageRef){
		String image = "";
		try {
		
			if(Utils.isNullOrBlank(imageRef)){
				return null;
			}
			String url = "https://maps.googleapis.com/maps/api/place/photo?key="+GOOGLE_KEY+"&photoreference="+imageRef+"&maxheight=1000";
			return url;
/*			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatusLine().getStatusCode());
			}

			//BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			JSONObject jsonObject = (JSONObject)JSONValue.parse(EntityUtils.toString(response.getEntity()));
			JSONObject resultsObj= (JSONObject)jsonObject.get("result");*/
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return image;
	}
}
