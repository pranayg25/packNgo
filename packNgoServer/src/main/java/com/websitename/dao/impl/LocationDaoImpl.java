package com.websitename.dao.impl;

import org.springframework.stereotype.Repository;

import com.websitename.dao.LocationDao;
import com.websitename.entities.Location;
 
 
/**
 * DAO for table: Location.
 * @author autogenerated
 */
@Repository
public class LocationDaoImpl extends GenericHibernateDaoImpl<Location, String> implements LocationDao {
	
	/** Constructor method. */
		public LocationDaoImpl() {
			super(Location.class);
		}
	}
