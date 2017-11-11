package com.app.kowalski.project;

import java.util.List;

import com.app.kowalski.activity.dto.ActivityDTO;
import com.app.kowalski.project.dto.ProjectDTO;
import com.app.kowalski.project.dto.ProjectSummaryDTO;
import com.app.kowalski.project.exception.ProjectNotFoundException;

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
	public List<ProjectSummaryDTO> getProjects();

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
}
