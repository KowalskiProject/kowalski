package com.app.kowalski.project;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.kowalski.project.dto.ProjectDTO;
import com.app.kowalski.project.dto.ProjectSummaryDTO;
import com.app.kowalski.project.exception.ProjectNotFoundException;

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
	public ResponseEntity<List<ProjectSummaryDTO>> getProjects() {
		return new ResponseEntity<List<ProjectSummaryDTO>>(this.projectService.getProjects(), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProjectDTO> getProjectById(@PathVariable int id) {
		try {
			ProjectDTO projectDTO = this.projectService.getProjectById(id);
			return new ResponseEntity<ProjectDTO>(projectDTO, HttpStatus.OK);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<ProjectDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ProjectDTO> addProject(@RequestBody ProjectDTO projectDTO) {
		projectDTO = this.projectService.addProject(projectDTO);
		return new ResponseEntity<ProjectDTO>(projectDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ProjectDTO> editProject(@PathVariable("id") int id, @RequestBody ProjectDTO projectDTO) {
		projectDTO.setProjectId(id);
		try {
			projectDTO = this.projectService.editProject(projectDTO);
			return new ResponseEntity<ProjectDTO>(projectDTO, HttpStatus.OK);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<ProjectDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteProject(@PathVariable("id") int id) {
		try {
			boolean ret = this.projectService.deleteProject(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}

}
