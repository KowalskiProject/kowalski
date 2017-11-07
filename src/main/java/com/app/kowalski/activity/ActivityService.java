package com.app.kowalski.activity;

import java.util.List;

import com.app.kowalski.activity.dto.ActivityDTO;
import com.app.kowalski.activity.exception.ActivityNotFoundException;

/**
 * Interface to expose allowed methods related to activities.
 *
 * It receives the ActivityDTO object in order to perform the
 * operations and does not expose the domain class.
 *
 * It could receive a JSON though, but the DTO is better to
 * handle the information.
 */
public interface ActivityService {

	/**
	 * Get all activities available in the system.
	 * @return List of activities
	 */
	public List<ActivityDTO> getActivities();

	/**
	 * Get a single activity instance through given id.
	 * @param id Activity's id
	 * @return Selected activity
	 */
	public ActivityDTO getActivityById(int id) throws ActivityNotFoundException;

	/**
	 * Includes a new activity in the system.
	 * @param activityDTO Activity to be included into system.
	 * @return Included activity
	 */
	public ActivityDTO addActivity(ActivityDTO activityDTO);

	/**
	 * Edits a activity through given id
	 * @param activityDTO activityDTO instance with new values to save
	 * @return activityDTO instance with parameters already saved
	 */
	public ActivityDTO editActivity(ActivityDTO activityDTO) throws ActivityNotFoundException;

	/**
	 * Removes a activity from the system
	 * @param id activity id
	 * @return true if the activity was removed successfully, false otherwise
	 */
	public boolean deleteActivity(int id) throws ActivityNotFoundException;

}
