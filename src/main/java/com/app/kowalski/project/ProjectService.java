package com.app.kowalski.project;

import java.util.List;

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
	 */
	public ProjectDTO getProjectById(int id);

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
	 */
	public ProjectDTO editProject(ProjectDTO projectDTO);

	/**
	 * Removes a project from the system
	 * @param id Project id
	 * @return true if the project was removed successfully, false otherwise
	 */
	public boolean deleteProject(int id);

}
