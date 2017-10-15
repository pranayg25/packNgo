package com.websitename.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websitename.dao.ItineraryDao;
import com.websitename.entities.Itinerary;
import com.websitename.entities.Location;
import com.websitename.entities.Plan;
import com.websitename.entities.PlanMembers;
import com.websitename.entities.User;
import com.websitename.objects.LocationObj;
import com.websitename.objects.PlanObj;
import com.websitename.objects.PlanQuestionsObj;
import com.websitename.utils.GoogleService;
import com.websitename.utils.Utils;

@Service
public class PlanService extends ServiceInf {

	public String makeMyPlan(PlanQuestionsObj planQuestionsObj) throws Exception{
		try {
			List<PlanObj> planObjs = new ArrayList<>();
			int totalDays = Utils.daysDifference(planQuestionsObj.getDateStart(),planQuestionsObj.getDateEnd())+1;
			int totalTime = planQuestionsObj.getHours();

			if(planQuestionsObj.getActivities().size()>0){
				List<LocationObj> locationObjs = new ArrayList<>();

				planQuestionsObj.getActivities().forEach(activity->{
					List<LocationObj> locationObjsTemp = GoogleService.getActivities(planQuestionsObj.getLat(),planQuestionsObj.getLon(),activity);
					if(locationObjsTemp!=null && locationObjsTemp.size()>3){
						locationObjs.add(locationObjsTemp.get(0));
						locationObjs.add(locationObjsTemp.get(1));
						locationObjs.add(locationObjsTemp.get(2));
					}else{
						locationObjs.addAll(locationObjsTemp);
					}
				});	
				//TODO: PLAN!!!!!!!!!!!!!!
				Map<String,Map<String,Integer>> matrix = GoogleService.getDistanceMatrix(locationObjs);

				List<String> visitedNodes = new ArrayList<>();

				for(Map.Entry<String, Map<String,Integer>> map:matrix.entrySet()){
					visitedNodes.add(map.getKey());
					break;
				}
				for(int i=0;i<matrix.size()-1;i++){
					Map<String,Integer> matrixVal = matrix.get(visitedNodes.get(i));
					for(String node:visitedNodes){
						if(matrixVal.get(node)!=null){
							matrixVal.remove(node);
						}
					}
					Entry<String, Integer> min = null;
					while(true){
						if(min!=null && !visitedNodes.contains(min.getKey())){
							visitedNodes.add(min.getKey());
							//matrixVal.remove(min.getKey());
							break;
						}else{
							min = null;
							for (Entry<String, Integer> entry : matrixVal.entrySet()) {
								if (min == null || min.getValue() > entry.getValue()) {
									min = entry;
								}
							}
						}	
					}
				}

				String startDate = planQuestionsObj.getDateStart();
				for(int i=0;i<totalDays;i++){
					PlanObj planObj=new PlanObj();
					planObj.setDate(startDate);

					int timeRemaining = totalTime;
					for (Iterator<String> iter = visitedNodes.listIterator(); iter.hasNext(); ) {
						String node = iter.next();
						LocationObj locationObj = findLocation(node, locationObjs);
						if(timeRemaining>3){
							timeRemaining -=3;
							planObj.addLocation(locationObj);
							iter.remove();//TODO: CAN CAUSE PROBLEM. VERIFY !!!!!!1
						}else{
							break;
						}
					}
					for(String node:visitedNodes){
						LocationObj locationObj = findLocation(node, locationObjs);
						if(timeRemaining>=3){
							timeRemaining -=3;
							planObj.addLocation(locationObj);
						}else{
							break;
						}
					}
					planObjs.add(planObj);

					//increment start date
					Calendar c = Calendar.getInstance();
					c.setTime(Utils.string2Date(startDate));
					c.add(Calendar.DATE, 1);
					startDate = Utils.date2String(c.getTime());
				}
				return saveMyPlan(planObjs, planQuestionsObj);
			}
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	private String saveMyPlan(List<PlanObj> planObjs,PlanQuestionsObj planQuestionsObj) throws Exception{
		try{
			Plan plan = new Plan();
			plan.setId(Utils.generateGUID());
			plan.setLocation(planQuestionsObj.getLocation());
			plan.setStartDate(planQuestionsObj.getDateStart());
			plan.setEndDate(planQuestionsObj.getDateEnd());
			//plan.setDayStart(planQuestionsObj.getDayStart());
			//plan.setDayEnd(planQuestionsObj.getDayEnd());
			plan.setHours(planQuestionsObj.getHours());
			plan.setLatitude(Double.parseDouble(planQuestionsObj.getLat()));
			plan.setLongitude(Double.parseDouble(planQuestionsObj.getLon()));
			planDao.save(plan);

			User user = userDao.getById(planQuestionsObj.getUserId());

			PlanMembers planMembers = new PlanMembers();

			planMembers.setId(Utils.generateGUID());
			planMembers.setPlan(plan);
			planMembers.setUser(user);
			planMembers.setIscreator(true);
			planMembersDao.save(planMembers);

			for(PlanObj planObj:planObjs){
				if(planObj.getLocations()!=null && planObj.getLocations().size()>0){
					for(LocationObj locationObj:planObj.getLocations()){
						Location location = new Location();
						location.setId(Utils.generateGUID());
						//TODO: location.setTime();
						location.setDuration(3.0);
						location.setName(locationObj.getName());
						location.setPlaceId(locationObj.getPlaceId());
						location.setImage(locationObj.getImage());
						locationDao.save(location);

						Itinerary itinerary = new Itinerary();
						itinerary.setId(Utils.generateGUID());
						itinerary.setPlan(plan);
						itinerary.setDate(planObj.getDate());
						itinerary.setSequence(1);
						itinerary.setLocation(location);
						itineraryDao.save(itinerary);
					}					
				}

			}
			return plan.getId();
		}catch(Exception e){
			throw e;
		}

	}

	private LocationObj findLocation(String locationName,List<LocationObj> locationObjs){
		for(LocationObj locationObj:locationObjs){
			if(locationObj.getName().equals(locationName)){
				return locationObj;
			}
		}
		return null;
	}

	public List<PlanObj> getItinerary(String itineraryId) throws Exception{
		try{
			List<PlanObj> planObjs = new ArrayList<>();

			Plan plan = planDao.getById(itineraryId);

			if(plan!=null){
				Calendar c = Calendar.getInstance();
				c.setTime(Utils.string2Date(plan.getStartDate()));

				int days=Utils.daysDifference(plan.getStartDate(), plan.getEndDate())+1;

				for(int i=0;i<days;i++){
					String dateStr = Utils.formatDateV3(c.getTime().toString());
					PlanObj planObj = new PlanObj();
					planObj.setDate(dateStr);//TODO: DATE PARSING ERROR
					planObj.setCity(plan.getLocation());
					List<Itinerary> itineraries = itineraryDao.getItineraryForDate(plan.getId(),planObj.getDate());//TODO: get itinenary by date
					itineraries.forEach(itinerary->{
						LocationObj locationObj = new LocationObj();
						locationObj.setId(itinerary.getLocation().getId());
						locationObj.setName(itinerary.getLocation().getName());
						locationObj.setDuration(itinerary.getLocation().getDuration());
						//TODO: set location parameters
						planObj.addLocation(locationObj);

					});
					c.add(Calendar.DATE, 1);
					planObjs.add(planObj);
				}

			}else{
				//TODO: handle itinerary not found
			}
			return planObjs;
		}catch(Exception e){
			throw e;
		}
	}

	public List<PlanObj> getMyPlans(String userId) throws Exception{
		try {
			List<PlanObj> planObjs = new ArrayList<>();

			User user = userDao.getById(userId);

			PlanMembers planMember = new PlanMembers();
			planMember.setUser(user);
			List<PlanMembers> planMembers = planMembersDao.getByCriteria(planMember);
			if(planMembers!=null && planMembers.size()>0){
				for(PlanMembers pm:planMembers){
					PlanObj planObj = new PlanObj();
					planObj.setCity(pm.getPlan().getLocation());
					planObj.setStartDate(pm.getPlan().getStartDate());
					planObj.setEndDate(pm.getPlan().getEndDate());
					planObj.setId(pm.getPlan().getId());
					planObjs.add(planObj);
				}
			}

			return planObjs;

		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public String deletePlan(String planId) throws Exception{
		try{
			Plan plan = planDao.getById(planId);
			PlanMembers members = new PlanMembers();
			members.setPlan(plan);
			List<PlanMembers> planMembers = planMembersDao.getByCriteria(members);
			if(planMembers!=null && planMembers.size()>0){
				for(PlanMembers mem:planMembers){
					planMembersDao.delete(mem);
				}
			}
			Itinerary it = new Itinerary();
			it.setPlan(plan);
			List<Itinerary> itineraries = itineraryDao.getByCriteria(it);
			for(Itinerary itinerary:itineraries){
				itineraryDao.delete(itinerary);

				Location location = locationDao.getById(itinerary.getLocation().getId());
				locationDao.delete(location);
			}

			planDao.delete(plan);
			return "true";
		}catch (Exception e) {
			throw e;
		}
	}

	public LocationObj getLocation(String locationId) throws Exception{
		try {
			LocationObj locationObj = new LocationObj();

			Location location = locationDao.getById(locationId);
			if(location==null){
				locationObj = GoogleService.getPlaceDetails(locationId);
			}else{
				locationObj = GoogleService.getPlaceDetails(location.getPlaceId());				
			}

			
			/*locationObj.setId(location.getId());
			locationObj.setName(location.getName());
			locationObj.setPlaceId(location.getPlaceId());
			locationObj.setImage(location.getImage());*/

			
			return locationObj;
		} catch (Exception e) {
			throw e;
		}
	}

	public String removeLocationFromItinerary(String planId, String locationId) throws Exception{
		try {

			Location location=locationDao.getById(locationId);

			Itinerary itinerary=new Itinerary();
			itinerary.setLocation(location);
			List<Itinerary> itineraries = itineraryDao.getByCriteria(itinerary);
			for(Itinerary iti:itineraries){
				itineraryDao.delete(iti);
			}
			locationDao.delete(location);

			return "true";

		} catch (Exception e) {
			throw e;
		}
	}

	public String alterTimeForLocation(String planId, String locationId, boolean increase) throws Exception {
		try {
			Plan plan = planDao.getById(planId);
			Location location = locationDao.getById(locationId);

			int totalTime = plan.getHours();
			double totalTimeUsed = 0.0;
			String date = "";

			Itinerary itinerary = new Itinerary();
			itinerary.setLocation(location);
			List<Itinerary> itineraries = itineraryDao.getByCriteria(itinerary);
			if(itineraries!=null && itineraries.size()>0){
				date = itineraries.get(0).getDate();
			}

			itinerary = new Itinerary();
			itinerary.setDate(date);
			itinerary.setPlan(plan);
			itineraries = itineraryDao.getByCriteria(itinerary);
			if(itineraries!=null && itineraries.size()>0){	
				for(Itinerary it:itineraries){
					totalTimeUsed += it.getLocation().getDuration();
				}
			}

			if(increase){
				if(totalTimeUsed==totalTime){
					throw new Exception("You have used up all the time for the day");
				}else if(totalTimeUsed<totalTime){
					location.setDuration(location.getDuration()+0.5);
					locationDao.update(location);
				}
			}else{
				if(location.getDuration()>1){
					location.setDuration(location.getDuration()-0.5);
					locationDao.update(location);
				}else{
					throw new Exception("You may need atleast 1 hr at this location");
				}
			}

			return "true";
		} catch (Exception e) {
			throw e;
		}
	}

	public List<LocationObj> searchLocation(String planId, List<String> activitiesList) throws Exception{
		try {
			List<LocationObj> locationObjs = new ArrayList<>();

			Plan plan = planDao.getById(planId);

			List<Location> locations = new ArrayList<>();
			Iterator<Itinerary> iterator = plan.getItineraries().iterator();
			while(iterator.hasNext()){
				Itinerary itinerary =iterator.next();
				locations.add(itinerary.getLocation());
			}
			
			activitiesList.forEach(activity->{
				List<LocationObj> locationObjsTemp = GoogleService.getActivities(plan.getLatitude()+"",plan.getLongitude()+"",activity);
				locationObjs.addAll(locationObjsTemp);
			});	
			
			for(Location location:locations){
				for(Iterator<LocationObj> it=locationObjs.iterator();it.hasNext();){
					LocationObj locationObj = it.next();
					if(location.getPlaceId().equals(locationObj.getPlaceId())){
						it.remove();
					}
				}
			}
			
			return locationObjs;
		} catch (Exception e) {
			throw e;
		}
	}

	public String addLocationToItinerary(String itineraryId, String duration, String placeId) throws Exception{
		try {
			Plan plan = planDao.getById(itineraryId);
			
			int totalDays = Utils.daysDifference(plan.getStartDate(),plan.getEndDate())+1;
			int totalTime = plan.getHours();
			
			Calendar c = Calendar.getInstance();
			c.setTime(Utils.string2Date(plan.getStartDate()));
			
			boolean flag=false;
			for(int i=0;i<totalDays;i++){
				String dateStr = Utils.formatDateV3(c.getTime().toString());
				Itinerary itinerary= new Itinerary();
				itinerary.setPlan(plan);
				itinerary.setDate(dateStr);
				List<Itinerary> itineraries = itineraryDao.getByCriteria(itinerary);
				double totalTimeUsed = 0.0;
				for(Itinerary it:itineraries){
					totalTimeUsed += it.getLocation().getDuration();
				}
				if((totalTime - totalTimeUsed) >= Double.parseDouble(duration)){
					LocationObj locationObj = GoogleService.getPlaceDetails(placeId);
					Location location = new Location();
					location.setId(Utils.generateGUID());
					location.setDuration(Double.parseDouble(duration));
					location.setName(locationObj.getName());
					location.setPlaceId(locationObj.getPlaceId());
					location.setImage(locationObj.getImage());
					locationDao.save(location);
					
					Itinerary newIt = new Itinerary();
					newIt.setId(Utils.generateGUID());
					newIt.setDate(dateStr);
					newIt.setLocation(location);
					newIt.setPlan(plan);
					newIt.setSequence(1);
					itineraryDao.save(newIt);
					flag = true;
					break;
				}
				c.add(Calendar.DATE, 1);
			}
			if(!flag){
				throw new Exception("Could not fit the location in the Plan. Try reducing the time and try again.");
			}
			return "true";
		} catch (Exception e) {
			throw e;
		}
	}


}
