package com.app.kowalski.activity;

import java.util.List;

import com.app.kowalski.activity.exception.ActivityNotFoundException;
import com.app.kowalski.project.ProjectDTO;
import com.app.kowalski.task.TaskDTO;
import com.app.kowalski.task.exception.TaskNotFoundException;

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
	public boolean deleteTaskFromActivity(int activityId, int taskId) throws ActivityNotFoundException, TaskNotFoundException;

}
