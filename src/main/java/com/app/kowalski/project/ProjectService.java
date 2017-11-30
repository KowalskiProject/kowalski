package com.app.kowalski.project;

import java.util.List;
import java.util.Set;

import com.app.kowalski.activity.ActivityDTO;
import com.app.kowalski.activity.exception.ActivityNotFoundException;
import com.app.kowalski.project.exception.ProjectNotFoundException;
import com.app.kowalski.user.KowalskiUserDTO;
import com.app.kowalski.user.exception.KowalskiUserNotFoundException;

/**
 * Interface to expose allowed methods related to projects.
 *
 * It receives the ProjectDTO object in order to perform the
 * operations and does not expose the domain class.
 *
 * It could receive a JSON though, but the DTO is better to
 * handle the information.
 */
public interface ProjectService {

	/**
	 * Get all projects available in the system.
	 * @return List of projects
	 */
	public List<ProjectDTO> getProjects();

	/**
	 * Get a single project instance through given id.
	 * @param id Project's id
	 * @return Selected project
	 *
	 * @throws ProjectNotFoundException No project instance was found in the system
	 */
	public ProjectDTO getProjectById(int id) throws ProjectNotFoundException;

	/**
	 * Includes a new project in the system.
	 * @param projectDTO Project to be included into system.
	 * @return Included project
	 */
	public ProjectDTO addProject(ProjectDTO projectDTO);

	/**
	 * Edits a project through given id
	 * @param projectDTO ProjectDTO instance with new values to save
	 * @return ProjectDTO instance with parameters already saved
	 *
	 * @throws ProjectNotFoundException No project instance was found in the system
	 */
	public ProjectDTO editProject(ProjectDTO projectDTO) throws ProjectNotFoundException;

	/**
	 * Removes a project from the system
	 * @param id Project id
	 * @return true if the project was removed successfully, false otherwise
	 *
	 * @throws ProjectNotFoundException No project instance was found in the system
	 */
	public boolean deleteProject(int id) throws ProjectNotFoundException;

	/**
	 * Adds an activity to the given project
	 * @param projectId Project reference
	 * @param activityId activity reference to be included into the project
	 * @return Activity data
	 *
	 * @throws ProjectNotFoundException No project instance was found in the system
	 */
	public ActivityDTO addActivityForProject(int projectId, ActivityDTO activityDTO) throws ProjectNotFoundException;

	/**
	 * Returns all activities associated with given project id
	 * @param id Project id
	 * @return list of activities associated with given project id
	 *
	 * @throws ProjectNotFoundException No project instance was found in the system
	 */
	public List<ActivityDTO> getAllActivitiesForProject(int id) throws ProjectNotFoundException;

	/**
	 * Remove an activity from project and delete it.
	 * @param projectId Project reference
	 * @param activityId Activity reference
	 * @return true if activity was removed successfully, false otherwise
	 *
	 * @throws ProjectNotFoundException No project instance was found in the system
	 */
	public boolean deleteActivityFromProject(int projectId, int activityId) throws ProjectNotFoundException;

	/**
	 * Returns the accountable user for given project
	 * @param projectId project reference
	 * @return user data
	 *
	 * @throws ProjectNotFoundException No project instance was found in the system
	 */
	public KowalskiUserDTO getAccountableForProject(int projectId) throws ProjectNotFoundException;

	/**
	 * Associates given user as accountable for given project
	 * @param projectId project reference
	 * @param kUserId kowalski reference
	 * @return project data after association
	 *
	 * @throws ProjectNotFoundException No project instance was found in the system
	 * @throws KowalskiUserNotFoundException No kowalski user was found in the system
	 */
	public ProjectDTO setAccountableForProject(Integer projectId, Integer kUserId)
			throws ProjectNotFoundException, KowalskiUserNotFoundException;

	/**
	 * Removes the association of the project and the accountable user.
	 * @param projectId project reference
	 * @return
	 * @throws ProjectNotFoundException
	 */
	public ProjectDTO removeAccountableForProject(Integer projectId) throws ProjectNotFoundException;

	/**
	 * Returns all users that belong to the given project
	 * @param projectId Project reference
	 * @return List of users associated to the project
	 *
	 * @throws ProjectNotFoundException No project instance was found in the system
	 */
	public Set<KowalskiUserDTO> getProjectMembers(Integer projectId) throws ProjectNotFoundException;

	/**
	 * Adds a new user to the project
	 * @param projectId Project reference
	 * @param kUserId User reference
	 * @return Project data after user addition
	 *
	 * @throws ProjectNotFoundException No project instance was found in the system
	 * @throws KowalskiUserNotFoundException No kowalski user was found in the system
	 */
	public ProjectDTO addMemberToProject(Integer projectId, Integer kUserId)
			throws ProjectNotFoundException, KowalskiUserNotFoundException;

	/**
	 * Removes a user from the project
	 * @param projectId Project reference
	 * @param kUserId User reference
	 * @return Project data after user addition
	 *
	 * @throws ProjectNotFoundException No project instance was found in the system
	 * @throws KowalskiUserNotFoundException No kowalski user was found in the system
	 */
	public ProjectDTO removeMemberFromProject(Integer projectId, Integer kUserId)
			throws ProjectNotFoundException, KowalskiUserNotFoundException;

	/**
	 * Returns the accountable user for given activity
	 * @param projectId project reference
	 * @param activityId activity reference
	 * @return user data
	 *
	 * @throws ProjectNotFoundException No project instance was found in the system
	 * @throws ActivityNotFoundException No activity instance was found in the system
	 */
	public KowalskiUserDTO getAccountableForActivity(Integer projectId, Integer activityId)
			throws ProjectNotFoundException, ActivityNotFoundException;

	/**
	 * Associates given user as accountable for given activity
	 * @param projectId project reference
	 * @param activityId activity reference
	 * @param kUserId kowalski reference
	 * @return activity data after association
	 *
	 * @throws ProjectNotFoundException No project instance was found in the system
	 * @throws ActivityNotFoundException No activity instance was found in the system
	 * @throws KowalskiUserNotFoundException No kowalski user was found in the system
	 */
	public ActivityDTO setAccountableForActivity(Integer projectId, Integer activityId, Integer kUserId)
			throws ProjectNotFoundException, ActivityNotFoundException, KowalskiUserNotFoundException;

	/**
	 * Removes the association of the activity and the accountable user.
	 * @param projectId project reference
	 * @param activityId activity reference
	 * @return activity data after removal
	 *
	 * @throws ProjectNotFoundException No project instance was found in the system
	 * @throws ActivityNotFoundException No activity instance was found in the system
	 */
	public ActivityDTO removeAccountableForActivity(Integer projectId, Integer activityId)
			throws ProjectNotFoundException, ActivityNotFoundException;
}
