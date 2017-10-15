package com.websitename.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.websitename.dao.ActivitiesDao;
import com.websitename.dao.FriendDao;
import com.websitename.dao.ItineraryDao;
import com.websitename.dao.LocationDao;
import com.websitename.dao.PlanDao;
import com.websitename.dao.PlanMembersDao;
import com.websitename.dao.UserDao;

@Service
public class ServiceInf {

	@Autowired UserDao userDao;
	
	@Autowired ActivitiesDao activitiesDao;
	
	@Autowired ItineraryDao itineraryDao;
	
	@Autowired PlanDao planDao;
	
	@Autowired LocationDao locationDao;
	
	@Autowired PlanMembersDao planMembersDao;
	
	@Autowired FriendDao friendDao;
	
	@Autowired EmailService emailService;
}
