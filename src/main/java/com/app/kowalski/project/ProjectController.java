package com.app.kowalski.project;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	HttpServletRequest request;
	private ProjectService projectService;

	@Autowired
	ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@RequestMapping(method = RequestMethod.GET)
	List<ProjectSummaryDTO> getProjects() {
		return this.projectService.getProjects();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	ProjectDTO getProjectById(@PathVariable int id) {
		return this.projectService.getProjectById(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	ProjectDTO addProject(@RequestBody ProjectDTO projectDTO) {
		return this.projectService.addProject(projectDTO);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	ProjectDTO editProject(@PathVariable("id") int id, @RequestBody ProjectDTO projectDTO) {
		projectDTO.setProjectId(id);
		return this.projectService.editProject(projectDTO);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	boolean deleteProject(@PathVariable("id") int id) {
		return this.projectService.deleteProject(id);
	}


}
