package com.websitename.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.websitename.entities.Activities;
import com.websitename.objects.ActivityObj;

@Service
public class UtilService extends ServiceInf {

	public List<ActivityObj> getAllActivities() throws Exception {
		try {
			List<ActivityObj> activityObjs = new ArrayList<>();
			List<Activities> activities = activitiesDao.getAll();
			if(activities!=null && activities.size()>0){
				for(Activities activity:activities){
					ActivityObj activityObj = new ActivityObj();
					activityObj.setId(activity.getId());
					activityObj.setName(activity.getName());
					activityObj.setValue(activity.getValue());
					
					activityObjs.add(activityObj);
				}
			}

			return activityObjs;
		} catch (Exception e) {
			throw e;
		}
		
	}

	
}
