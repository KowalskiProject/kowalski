package com.app.kowalski.project;

import java.util.List;

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
	 */
	public ProjectDTO editProject(ProjectDTO projectDTO) throws ProjectNotFoundException;

	/**
	 * Removes a project from the system
	 * @param id Project id
	 * @return true if the project was removed successfully, false otherwise
	 */
	public boolean deleteProject(int id) throws ProjectNotFoundException;

}
