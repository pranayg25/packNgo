/**
 * 
 */
package com.websitename.rest;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.websitename.objects.ActivityObj;
import com.websitename.objects.LocationObj;
import com.websitename.objects.PlanObj;
import com.websitename.objects.PlanQuestionsObj;
import com.websitename.objects.UserObj;
import com.websitename.service.FriendsService;
import com.websitename.service.PlanService;
import com.websitename.service.UserService;
import com.websitename.service.UtilService;
import com.websitename.utils.Utils;
import com.websitename.validator.CreateFormValidator;
import com.websitename.validator.NewPlanValidator;

/**
 * @author Pranay
 *
 */
@RestController
@RequestMapping("/rest")
public class RestWrapper {

	@Autowired
	private UserService userService;

	@Autowired
	private PlanService planService;

	@Autowired
	private UtilService utilService;

	@Autowired 
	FriendsService friendsService;
	
	@Autowired
	private HttpServletRequest request;

	private Gson gson = new Gson();

	private void checkAuthorization() throws Exception{
		String authorization = request.getHeader("Authorization");
		if(authorization==null){
			throw new Exception("You are not authorised to use this API.");
		}
	}
	
	private void validateHeaders(Map<String, String> headerMap) throws Exception{
		if(Utils.isNullOrBlank(headerMap.get("size")) 
			|| Utils.isNullOrBlank(headerMap.get("iteration"))
			|| Utils.isNullOrBlank(headerMap.get("token2"))
			|| Utils.isNullOrBlank(headerMap.get("token1"))
			|| Utils.isNullOrBlank(headerMap.get("token3"))){
			throw new Exception("Incorrect header values.");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.definitics.irest.IRestWrapper#test()
	 */
	@RequestMapping(value = "/test")
	public @ResponseBody Response test(@Valid @RequestBody PlanQuestionsObj planQuestionsObj,Errors errors) {
		//planValidator.validate(new PlanQuestionsObj(), errors);

		if (errors.hasErrors()) {

		}
		Response<String> resp = new Response<String>();
		resp.setStatus("200");
		resp.setMessage("Success");

		return resp;
	}

	@RequestMapping(value = "/user/signup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response createAccount(@Valid @RequestBody UserObj userObj,BindingResult result) {
		try {
			new CreateFormValidator().validate(userObj,result);
			if(result.hasErrors()){
				/*result.getAllErrors().forEach(error->{
					String message = error.getDefaultMessage();
					String variableName = ((DefaultMessageSourceResolvable) error.getArguments()[0]).getDefaultMessage();
				});*/
				throw new Exception(result.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
			}
			Map<String,String> headerList = new HashMap<String,String>();
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String key = (String) headerNames.nextElement();
				String value = request.getHeader(key);
				headerList.put(key, value);
			}
			validateHeaders(headerList);
			
			userObj = userService.createAccount(userObj,headerList);
			
			Response<UserObj> resp = new Response<UserObj>();
			resp.setStatus("200");
			resp.getObjectList().add(userObj);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}

	}

	@RequestMapping(value = "/user/authenticate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response authenticateUser(@RequestBody String userStr) {
		try {
			
			UserObj userObj = gson.fromJson(userStr, UserObj.class);
			
			Map<String,String> headerList = new HashMap<String,String>();
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String key = (String) headerNames.nextElement();
				String value = request.getHeader(key);
				headerList.put(key, value);
			}
			validateHeaders(headerList);
						 			
			userObj = userService.authenticateUser(userObj,headerList);

			Response<UserObj> resp = new Response<UserObj>();
			resp.setStatus("200");
			resp.getObjectList().add(userObj);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}

	}

	@RequestMapping(value = "/plan/make", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response makeMyPlan(@Valid @RequestBody PlanQuestionsObj planQuestionsObj,Errors errors) {
		try {
			checkAuthorization();
			//PlanQuestionsObj planQuestionsObj = gson.fromJson(planStr, PlanQuestionsObj.class);
			new NewPlanValidator().validate(planQuestionsObj, errors);
			if (errors.hasErrors()) {
				throw new Exception(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
			}
			String planId = planService.makeMyPlan(planQuestionsObj);

			Response<String> resp = new Response<String>();
			resp.setStatus("200");
			resp.getObjectList().add(planId);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}
	}

	@RequestMapping(value = "/activities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response getAllActivities() {
		try {
			List<ActivityObj> activities = utilService.getAllActivities();
			Response<ActivityObj> resp = new Response<ActivityObj>();
			resp.setStatus("200");
			resp.setObjectList(activities);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}

	}

	@RequestMapping(value = "/itinerary/{itineraryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response getItinerary(@PathVariable("itineraryId") String itineraryId) {
		try {
			checkAuthorization();
			List<PlanObj> plan = planService.getItinerary(itineraryId);
			Response<PlanObj> resp = new Response<PlanObj>();
			resp.setStatus("200");
			resp.setObjectList(plan);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}

	}

	@RequestMapping(value = "/plans/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response getMyPlans(@PathVariable("userId") String userId) {
		try {
			checkAuthorization();
			List<PlanObj> planObjs = planService.getMyPlans(userId);

			Response<PlanObj> resp = new Response<PlanObj>();
			resp.setStatus("200");
			resp.setObjectList(planObjs);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}
	}

	@RequestMapping(value = "/plan/{planId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response deletePlan(@PathVariable("planId") String planId) {
		try {
			checkAuthorization();
			String delete = planService.deletePlan(planId);

			Response<String> resp = new Response<String>();
			resp.setStatus("200");
			resp.getObjectList().add(delete);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}
	}

	@RequestMapping(value = "/friends/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response getMyFriends(@PathVariable("userId") String userId) {
		try {
			checkAuthorization();
			List<UserObj> friends = friendsService.getMyFriends(userId);

			Response<UserObj> resp = new Response<>();
			resp.setStatus("200");
			resp.setObjectList(friends);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}
	}

	@RequestMapping(value = "/friend/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response findFriend(@RequestParam String userId,@RequestParam String email) {
		try {
			checkAuthorization();
			UserObj friend = friendsService.findFriends(userId,email);
			Response<UserObj> resp = new Response<>();
			resp.setStatus("200");
			resp.getObjectList().add(friend);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}
	}

	@RequestMapping(value = "/location/{locationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response getLocation(@PathVariable("locationId") String locationId) {
		try {
			checkAuthorization();
			LocationObj locationObj = planService.getLocation(locationId);
			Response<LocationObj> resp = new Response<>();
			resp.setStatus("200");
			resp.getObjectList().add(locationObj);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}
	}
	
	@RequestMapping(value = "/itinerary/{itineraryId}/friends", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response getItineraryFriends(@PathVariable("itineraryId") String itineraryId) {
		try {
			checkAuthorization();
			List<UserObj> userObjs = friendsService.getItineraryFriends(itineraryId);
			Response<UserObj> resp = new Response<>();
			resp.setStatus("200");
			resp.setObjectList(userObjs);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}

	}
	
	@RequestMapping(value = "/friend/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response addFriend(@RequestBody JSONObject object) {
		try {
			checkAuthorization();
			String msg = friendsService.addFriend((String)object.get("userId"),(String)object.get("friendId"));
			Response<String> resp = new Response<>();
			resp.setStatus("200");
			resp.getObjectList().add(msg);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}
	}
	
	
	@RequestMapping(value = "/itinerary/{itineraryId}/friend/{friendId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response addFriendToPlan(@PathVariable String itineraryId,@PathVariable String friendId) {
		try {
			checkAuthorization();
			String msg = friendsService.addFriendToPlan(itineraryId,friendId);
			Response<String> resp = new Response<>();
			resp.setStatus("200");
			resp.getObjectList().add(msg);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}
	}
	
	@RequestMapping(value = "/itinerary/{itineraryId}/friend/{friendId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response removeFriendFromPlan(@PathVariable String itineraryId,@PathVariable String friendId) {
		try {
			checkAuthorization();
			String msg = friendsService.removeFriendFromPlan(itineraryId,friendId);
			Response<String> resp = new Response<>();
			resp.setStatus("200");
			resp.getObjectList().add(msg);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}
	}
	
	@RequestMapping(value = "/itinerary/{itineraryId}/location/{locationId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response removeLocationFromItinerary(@PathVariable String itineraryId,@PathVariable String locationId) {
		try {
			checkAuthorization();
			String msg = planService.removeLocationFromItinerary(itineraryId,locationId);
			Response<String> resp = new Response<>();
			resp.setStatus("200");
			resp.getObjectList().add(msg);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}
	}
	
	@RequestMapping(value = "/itinerary/{itineraryId}/location/{locationId}/time", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response increaseTimeForLocation(@PathVariable String itineraryId,@PathVariable String locationId) {
		try {
			checkAuthorization();
			String msg = planService.alterTimeForLocation(itineraryId,locationId,true);
			Response<String> resp = new Response<>();
			resp.setStatus("200");
			resp.getObjectList().add(msg);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}
	}
	
	@RequestMapping(value = "/itinerary/{itineraryId}/location/{locationId}/time", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response decreaseTimeForLocation(@PathVariable String itineraryId,@PathVariable String locationId) {
		try {
			checkAuthorization();
			String msg = planService.alterTimeForLocation(itineraryId,locationId,false);
			Response<String> resp = new Response<>();
			resp.setStatus("200");
			resp.getObjectList().add(msg);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}
	}
	
	@RequestMapping(value = "/itinerary/{itineraryId}/locations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response searchLocation(@PathVariable String itineraryId,@RequestParam("activities") String activitiesStr) {
		try {
			checkAuthorization();
			if(!Utils.isNullOrBlank(activitiesStr)){
				List<String> activities = new ArrayList<String>(Arrays.asList(activitiesStr.split(",")));
				
				List<LocationObj> locations = planService.searchLocation(itineraryId,activities);
				Response<LocationObj> resp = new Response<>();
				resp.setStatus("200");
				resp.setObjectList(locations);
				return resp;				
			}else{
				throw new Exception("No activity provided");
			}

		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}
	}
	
	@RequestMapping(value = "/itinerary/{itineraryId}/location", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response addLocationToItinerary(@PathVariable String itineraryId,@RequestBody JSONObject object) {
		try {
			checkAuthorization();
			String msg = planService.addLocationToItinerary(itineraryId,(String)object.get("duration"),(String)object.get("placeId"));
			Response<String> resp = new Response<>();
			resp.setStatus("200");
			resp.getObjectList().add(msg);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}
	}
	
	@RequestMapping(value = "/friend/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Response deleteFriend(@RequestBody JSONObject object) {
		try {
			checkAuthorization();
			String msg = friendsService.deleteFriend((String)object.get("userId"),(String)object.get("friendId"));
			Response<String> resp = new Response<>();
			resp.setStatus("200");
			resp.getObjectList().add(msg);
			return resp;
		} catch (Exception e) {
			Response<Object> resp = new Response<Object>();
			resp.setStatus("400");
			resp.setMessage(e.getMessage());
			return resp;
		}
	}
}
