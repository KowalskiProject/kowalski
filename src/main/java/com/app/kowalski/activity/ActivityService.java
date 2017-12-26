package com.app.kowalski.activity;

import java.util.List;
import java.util.Set;

import com.app.kowalski.exception.ActivityNotFoundException;
import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.exception.TaskNotFoundException;
import com.app.kowalski.project.ProjectDTO;
import com.app.kowalski.task.TaskDTO;
import com.app.kowalski.user.KowalskiUserDTO;

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

	/**
	 * Returns the list of tasks associated to the given activity
	 * @param activityId activity reference
	 * @return List of tasks associated to the given activity
	 *
	 * @throws ActivityNotFoundException No activity instance was found in the system
	 */
	public List<TaskDTO> getTasksForActivity(int activityId) throws ActivityNotFoundException;

	/**
	 * Creates a new task and associates it to the given activity
	 * @param activityId activity reference
	 * @param taskDTO task to be created
	 * @return created task
	 *
	 * @throws ActivityNotFoundException No activity instance was found in the system
	 */
	public TaskDTO addTaskForActivity(int activityId, TaskDTO taskDTO) throws ActivityNotFoundException;

	/**
	 * Deletes a task
	 * @param activityId activity reference
	 * @param taskId task reference
	 * @return true if task was deleted successfully, false otherwise
	 *
	 * @throws ActivityNotFoundException No activity instance was found in the system
	 * @throws TaskNotFoundException No task instance was found in the system
	 */
	public boolean deleteTaskFromActivity(int activityId, int taskId)
			throws ActivityNotFoundException, TaskNotFoundException;

	/**
	 * Returns the accountable user for given activity
	 * @param activityId activity reference
	 * @return user data
	 *
	 * @throws ActivityNotFoundException No activity instance was found in the system
	 */
	public KowalskiUserDTO getAccountableForActivity(Integer activityId) throws ActivityNotFoundException;

	/**
	 * Associates given user as accountable for given activity
	 * @param activityId activity reference
	 * @param kUserId kowalski reference
	 * @return activity data after association
	 *
	 * @throws ActivityNotFoundException No activity instance was found in the system
	 * @throws KowalskiUserNotFoundException No kowalski user was found in the system
	 */
	public ActivityDTO setAccountableForActivity(Integer activityId, Integer kUserId)
			throws ActivityNotFoundException, KowalskiUserNotFoundException;

	/**
	 * Removes the association of the activity and the accountable user
	 * @param activityId activity reference
	 * @return activity data after removal
	 *
	 * @throws ActivityNotFoundException No activity instance was found in the system
	 */
	public ActivityDTO removeAccountableForActivity(Integer activityId) throws ActivityNotFoundException;

	/**
	 * Returns all users that belong to the given activity
	 * @param activityId Activity reference
	 * @return List of users associated to the activity
	 *
	 * @throws ActivityNotFoundException No activity instance was found in the system
	 */
	public Set<KowalskiUserDTO> getActivityMembers(Integer activityId) throws ActivityNotFoundException;

	/**
	 * Adds a new user to the activity
	 * @param activityId Activity reference
	 * @param kUserId User reference
	 * @return Activity data after user addition
	 *
	 * @throws ActivityNotFoundException No activity instance was found in the system
	 * @throws KowalskiUserNotFoundException No kowalski user was found in the system
	 */
	public ActivityDTO addMemberToActivity(Integer activityId, Integer kUserId)
			throws ActivityNotFoundException, KowalskiUserNotFoundException;

	/**
	 * Removes a user from the activity
	 * @param activityId Activity reference
	 * @param kUserId User reference
	 * @return Activity data after user addition
	 *
	 * @throws ActivityNotFoundException No activity instance was found in the system
	 * @throws KowalskiUserNotFoundException No kowalski user was found in the system
	 */
	public ActivityDTO removeMemberFromActivity(Integer activityId, Integer kUserId)
			throws ActivityNotFoundException, KowalskiUserNotFoundException;

	/**
	 * Returns all activities where given user is responsible
	 * @param kUserId user reference
	 * @return List of activities
	 *
	 * @throws KowalskiUserNotFoundException No KowalskiUser instance found in the system
	 */
	public List<ActivityDTO> getAccountableActivitiesForUser(Integer kUserId) throws KowalskiUserNotFoundException;
}
