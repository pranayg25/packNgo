package com.websitename.validator;

import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.websitename.objects.PlanQuestionsObj;
import com.websitename.utils.Utils;

public class NewPlanValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return PlanQuestionsObj.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PlanQuestionsObj obj = (PlanQuestionsObj)target;
		if(obj.getLocation()==null){
			errors.rejectValue("location", "Location not provided");
		}
		Date currentDate = new Date();
		Date startDate = Utils.string2Date(obj.getDateStart());
		Date endDate = Utils.string2Date(obj.getDateEnd());
		if(startDate.before(currentDate)){
			errors.reject("startDate", "Start date cannot be before current date");
		}
		if(endDate.before(startDate)){
			errors.reject("endDate", "End date cannot be before start date");
		}
		if(obj.getActivities()==null || (obj.getActivities()!=null && obj.getActivities().size()<=0)){
			errors.reject("activity", "Select atleast one activity to do");
		}
	}

}
