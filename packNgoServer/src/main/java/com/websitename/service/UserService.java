package com.websitename.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.websitename.entities.User;
import com.websitename.objects.UserObj;
import com.websitename.utils.Utils;

@Service
public class UserService extends ServiceInf {
	
	public UserObj createAccount(UserObj userObj, Map<String, String> headerList) throws Exception {
		try {
			String decreptedPassword = Utils.getDecrytedPassword(userObj.getPassword(), headerList);
			
			User user = new User();
			user.setEmail(userObj.getEmail());
			user = userDao.authenticateUser(user);
			if(user!=null){
				throw new Exception("Email address already exists.");
			}
			user = new User();
			user.setId(Utils.generateGUID());
			user.setEmail(userObj.getEmail());
			user.setPassword(Utils.encrypt(decreptedPassword));
			user.setFirstName(userObj.getFirstName());
			user.setLastName(userObj.getLastName());

			String id = (String)userDao.save(user);
			userObj.setId(id);

			Map<String, Object> velocityModel = new HashMap<String, Object>();
			velocityModel.put("firstName", user.getFirstName());
			velocityModel.put("lastName", user.getLastName());
			emailService.sendEmail(velocityModel,"templates/email/accountActivation.vm","Welcome to packNgo",user.getEmail(),"pranayg25@gmail.com");
			
			return userObj;	

		} catch (Exception e) {
			throw e;
		}
	}

	public UserObj authenticateUser(UserObj userObj, Map<String, String> headerList) throws Exception {
		try {
			if(userObj!=null){
				String decreptedPassword = Utils.getDecrytedPassword(userObj.getPassword(), headerList);
				User user = new User();
				user.setEmail(userObj.getEmail());
				user.setPassword(Utils.encrypt(decreptedPassword));
				user = userDao.authenticateUser(user);
				if(user!=null){
					userObj.setId(user.getId());
					userObj.setFirstName(user.getFirstName());
					userObj.setLastName(user.getLastName());
					userObj.setEmail(user.getEmail());
					userObj.setPassword(null);
					return userObj;					
				}else{
					throw new Exception("User not found.");
				}
			}else{
				throw new Exception("Input parameters null or empty.");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
