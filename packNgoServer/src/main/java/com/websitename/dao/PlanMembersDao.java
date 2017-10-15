package com.websitename.dao;

import java.util.List;

import com.websitename.entities.PlanMembers;
 
/**
 * DAO interface for table: PlanMembers.
 * @author autogenerated
 */
public interface PlanMembersDao extends GenericDAO<PlanMembers,  String>  {

	List<PlanMembers> getByCriteria(PlanMembers members) throws Exception;
	// constructor only
}
