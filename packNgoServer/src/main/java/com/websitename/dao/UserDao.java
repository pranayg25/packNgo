package com.websitename.dao;

import java.util.List;

import com.websitename.entities.User;
 
/**
 * DAO interface for table: User.
 * @author autogenerated
 */
public interface UserDao extends GenericDAO<User,  String>  {

	User authenticateUser(User user) throws Exception;
	// constructor only

	User findFriend(User user, String email) throws Exception;
}

