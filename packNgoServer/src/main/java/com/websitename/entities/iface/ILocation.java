package com.websitename.entities.iface;
import java.util.Set;

import com.websitename.entities.Itinerary;


/** 
 * Object interface mapping for hibernate-handled table: location.
 * @author autogenerated
 */

public interface ILocation {



    /**
     * Return the value associated with the column: placeId.
	 * @return A String object (this.placeId)
	 */
	String getPlaceId();
	

  
    /**  
     * Set the value related to the column: placeId.
	 * @param placeId the placeId value you wish to set
	 */
	void setPlaceId(final String placeId);
    /**
     * Return the value associated with the column: details.
	 * @return A String object (this.details)
	 */
	String getDetails();
	

  
    /**  
     * Set the value related to the column: details.
	 * @param details the details value you wish to set
	 */
	void setDetails(final String details);

    /**
     * Return the value associated with the column: duration.
	 * @return A String object (this.duration)
	 */
	Double getDuration();
	

  
    /**  
     * Set the value related to the column: duration.
	 * @param duration the duration value you wish to set
	 */
	void setDuration(final Double duration);

    /**
     * Return the value associated with the column: id.
	 * @return A String object (this.id)
	 */
	String getId();
	

  
    /**  
     * Set the value related to the column: id.
	 * @param id the id value you wish to set
	 */
	void setId(final String id);

    /**
     * Return the value associated with the column: image.
	 * @return A String object (this.image)
	 */
	String getImage();
	

  
    /**  
     * Set the value related to the column: image.
	 * @param image the image value you wish to set
	 */
	void setImage(final String image);

    /**
     * Return the value associated with the column: itinerary.
	 * @return A Set&lt;Itinerary&gt; object (this.itinerary)
	 */
	Set<Itinerary> getItineraries();
	
	/**
	 * Adds a bi-directional link of type Itinerary to the itineraries set.
	 * @param itinerary item to add
	 */
	void addItinerary(Itinerary itinerary);

  
    /**  
     * Set the value related to the column: itinerary.
	 * @param itinerary the itinerary value you wish to set
	 */
	void setItineraries(final Set<Itinerary> itinerary);

    /**
     * Return the value associated with the column: name.
	 * @return A String object (this.name)
	 */
	String getName();
	

  
    /**  
     * Set the value related to the column: name.
	 * @param name the name value you wish to set
	 */
	void setName(final String name);

    /**
     * Return the value associated with the column: time.
	 * @return A String object (this.time)
	 */
	String getTime();
	

  
    /**  
     * Set the value related to the column: time.
	 * @param time the time value you wish to set
	 */
	void setTime(final String time);

	// end of interface
}