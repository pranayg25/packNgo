package com.websitename.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.websitename.entities.Friend;
import com.websitename.entities.Plan;
import com.websitename.entities.PlanMembers;
import com.websitename.entities.User;
import com.websitename.objects.UserObj;
import com.websitename.utils.Utils;

@Service
public class FriendsService extends ServiceInf{

	public List<UserObj> getMyFriends(String userId) throws Exception {
		try {
			List<UserObj> friendsObj = new ArrayList<>();
			
			User user = userDao.getById(userId);
			Friend friend = new Friend();
			friend.setUser(user);
			List<Friend> friends = friendDao.getByCriteria(friend);
			if(friends!=null && friends.size()>0){
				for(Friend frnd:friends){
					User f = frnd.getFriendUser();
					UserObj userObj = new UserObj();
					userObj.setId(f.getId());
					userObj.setEmail(f.getEmail());
					userObj.setFirstName(f.getFirstName());
					userObj.setLastName(f.getLastName());
					friendsObj.add(userObj);
				}
			}
			
			return friendsObj;
		} catch (Exception e) {
			throw e;
		}
	}

	public UserObj findFriends(String userId, String emailId) throws Exception{
		try{
			UserObj userObj = new UserObj();
			
			User user = userDao.getById(userId);
			User friend = userDao.findFriend(user, emailId);
			boolean flag = true;
			if(friend!=null){
				for(Friend frnd:user.getFriends()){
					if(frnd.getFriendUser().getEmail().equals(friend.getEmail())){
						flag = false;
						break;
					}
				}
				if(friend!=null && flag){
					userObj.setId(friend.getId());
					userObj.setEmail(friend.getEmail());
					userObj.setFirstName(friend.getFirstName());
					userObj.setLastName(friend.getLastName());
					return userObj;				
				}				
			}else{
				throw new Exception("Sorry. Email does not exist in our database.");
			}
			
			return null;
		}catch (Exception e) {
			throw e;
		}
	}

	public List<UserObj> getItineraryFriends(String itineraryId) throws Exception{
		try {
			List<UserObj> friendsObjs = new ArrayList<>();
			Plan plan = planDao.getById(itineraryId);
			
			PlanMembers planMember = new PlanMembers();
			planMember.setPlan(plan);
			List<PlanMembers> planMembers = planMembersDao.getByCriteria(planMember);
			if(planMembers!=null && planMembers.size()>0){
				for(PlanMembers members:planMembers){
					User mem = members.getUser();
					UserObj userObj = new UserObj();
					userObj.setId(mem.getId());
					userObj.setFirstName(mem.getFirstName());
					userObj.setLastName(mem.getLastName());
					userObj.setEmail(mem.getEmail());
					userObj.setCreator(members.isIscreator());
					friendsObjs.add(userObj);
				}
			}
			return friendsObjs;
		} catch (Exception e) {
			throw e;
		}
	}

	public String addFriend(String userId,String friendId) throws Exception {
		try {
			User user = userDao.getById(userId);
			User friend = userDao.getById(friendId);
			
			Friend map = new Friend();
			map.setId(Utils.generateGUID());
			map.setUser(user);
			map.setFriendUser(friend);
			friendDao.save(map);
			
			map = new Friend();
			map.setId(Utils.generateGUID());
			map.setUser(friend);
			map.setFriendUser(user);
			friendDao.save(map);
			
			Map<String, Object> velocityModel = new HashMap<String, Object>();
			velocityModel.put("firstName", friend.getFirstName());
			velocityModel.put("lastName", friend.getLastName());
			velocityModel.put("friendName", user.getFirstName()+" "+user.getLastName());
			emailService.sendEmail(velocityModel,"templates/email/friendAdd.vm","You just made a new friend at packNgo",friend.getEmail(),"pranayg25@gmail.com");
			
			return "true";
		} catch (Exception e) {
			throw e;
		}
	}

	public String addFriendToPlan(String itineraryId, String friendId) throws Exception{
		try {
			Plan plan = planDao.getById(itineraryId);
			User user = userDao.getById(friendId);
			
			PlanMembers members = new PlanMembers();
			members.setId(Utils.generateGUID());
			members.setPlan(plan);
			members.setUser(user);
			members.setIscreator(false);
			planMembersDao.save(members);
			
			Map<String, Object> velocityModel = new HashMap<String, Object>();
			velocityModel.put("firstName", user.getFirstName());
			velocityModel.put("lastName", user.getLastName());
			emailService.sendEmail(velocityModel,"templates/email/friendAdd2Plan.vm","Someone added you to a plan at packNgo",user.getEmail(),"pranayg25@gmail.com");
			
			return "true";
		} catch (Exception e) {
			throw e;
		}
	}

	public String removeFriendFromPlan(String itineraryId, String friendId) throws Exception{

		try {
			Plan plan = planDao.getById(itineraryId);
			User user = userDao.getById(friendId);
			
			PlanMembers members = new PlanMembers();
			members.setPlan(plan);
			members.setUser(user);
			members =planMembersDao.getByCriteria(members).get(0);
			planMembersDao.delete(members);
			
			return "true";
		} catch (Exception e) {
			throw e;
		}
	
	}

	public String deleteFriend(String userId,String friendId) throws Exception {
		try {
			User user = userDao.getById(userId);
			User friend = userDao.getById(friendId);
			
			Friend map = new Friend();
			map.setUser(user);
			map.setFriendUser(friend);
			List<Friend> friends = friendDao.getByCriteria(map);
			if(friends!=null && friends.size()>0){
				for(Friend f:friends){
					friendDao.delete(f);
				}
			}
			
			map = new Friend();
			map.setUser(friend);
			map.setFriendUser(user);
			friends = friendDao.getByCriteria(map);
			if(friends!=null && friends.size()>0){
				for(Friend f:friends){
					friendDao.delete(f);
				}
			}
			
			return "true";
		} catch (Exception e) {
			throw e;
		}
	}
	
}
