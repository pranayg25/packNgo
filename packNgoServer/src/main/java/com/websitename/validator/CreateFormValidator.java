package com.websitename.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.websitename.objects.UserObj;

public class CreateFormValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return UserObj.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserObj userObj = (UserObj) target;
		
		if(!userObj.getFirstName().matches("[a-zA-Z]*"))
			errors.rejectValue("firstName", "First name cannot be empty");
	}

}
