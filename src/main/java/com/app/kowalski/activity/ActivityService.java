package com.app.kowalski.activity;

import java.util.List;

import com.app.kowalski.activity.exception.ActivityNotFoundException;
import com.app.kowalski.project.ProjectDTO;

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


	public List<ActivityDTO> getActivitiesForProject(int projectId);

	/**
	 * Get a single activity instance through given id.
	 * @param activityId Activity's id
	 * @return Selected activity
	 *
	 * @throws ActivityNotFoundException No activity instance was found in the system
	 */
	public ActivityDTO getActivityById(int activityId) throws ActivityNotFoundException;

	/**
	 * Edits a activity through given id
	 * @param activityDTO activityDTO instance with new values to save
	 * @return activityDTO instance with parameters already saved
	 *
	 * @throws ActivityNotFoundException No activity instance was found in the system
	 */
	public ActivityDTO editActivity(ActivityDTO activityDTO) throws ActivityNotFoundException;

	/**
	 * Returns the project associated to the given activity
	 * @param activityId activity reference
	 * @return
	 *
	 * @throws ActivityNotFoundException No activity instance was found in the system
	 */
	public ProjectDTO getProjectForActivity(int activityId) throws ActivityNotFoundException;
}
