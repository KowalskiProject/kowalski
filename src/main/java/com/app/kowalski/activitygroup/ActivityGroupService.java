package com.app.kowalski.activitygroup;

import java.util.List;

import com.app.kowalski.exception.ActivityGroupNotFoundException;

public interface ActivityGroupService {

	/**
	 * Returns all activity groups registered in the system
	 * @return activity groups
	 */
	public List<ActivityGroupDTO> getActivityGroups();

	/**
	 * Creates a new activity group
	 * @param activityGroupDTO activity group parameters
	 * @return activity group data
	 */
	public ActivityGroupDTO addActivityGroup(ActivityGroupDTO activityGroupDTO);

	/**
	 * Returns the activity group by given id
	 * @param activityGroupId activity group reference
	 * @return activity group data
	 *
	 * @throws ActivityGroupNotFoundException
	 */
	public ActivityGroupDTO getActivityGroupById(Integer activityGroupId) throws ActivityGroupNotFoundException;

	/**
	 * Edits a given activity group with given parameters
	 * @param activityGroupId activity group reference
	 * @param activityGroupDTO activity group data to be inserted
	 * @return activity group data after edition
	 *
	 * @throws ActivityGroupNotFoundException No activity group was found in the system
	 */
	public ActivityGroupDTO editActivityGroup(Integer activityGroupId, ActivityGroupDTO activityGroupDTO)
			throws ActivityGroupNotFoundException;

	/**
	 * Removes an activity group
	 * @param activityGroupId activity group reference
	 * @return true if the activity group was removed successfully, false otherwise
	 *
	 * @throws ActivityGroupNotFoundException No activity group was found in the system
	 */
	public boolean removeActivityGroup(Integer activityGroupId) throws ActivityGroupNotFoundException;

}
