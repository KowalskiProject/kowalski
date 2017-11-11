package com.app.kowalski.project;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.kowalski.activity.ActivityController;
import com.app.kowalski.activity.ActivityDTO;
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
	public ResponseEntity<List<ProjectDTO>> getProjects() {
		List<ProjectDTO> projectsDTO = this.projectService.getProjects();

		for (ProjectDTO projectDTO : projectsDTO) {
			Link selfLink = linkTo(ProjectController.class).slash(projectDTO.getProjectId()).withSelfRel();
			projectDTO.add(selfLink);

			ResponseEntity<List<ActivityDTO>> methodLinkBuilder = methodOn(ProjectController.class)
					.getActivitiesForProject(projectDTO.getProjectId());

			Link ordersLink = linkTo(methodLinkBuilder).withRel("activities");
			projectDTO.add(ordersLink);
		}
		return new ResponseEntity<List<ProjectDTO>>(projectsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
	public ResponseEntity<ProjectDTO> getProjectById(@PathVariable int projectId) {
		ProjectDTO projectDTO = new ProjectDTO();
		try {
			projectDTO = this.projectService.getProjectById(projectId);
			Link selfLink = linkTo(ProjectController.class).slash(projectDTO.getProjectId()).withSelfRel();
			projectDTO.add(selfLink);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<ProjectDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ProjectDTO>(projectDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ProjectDTO> addProject(@RequestBody ProjectDTO projectDTO) {
		projectDTO = this.projectService.addProject(projectDTO);
		Link selfLink = linkTo(ProjectController.class).slash(projectDTO.getProjectId()).withSelfRel();
		projectDTO.add(selfLink);

		return new ResponseEntity<ProjectDTO>(projectDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}", method = RequestMethod.PUT)
	public ResponseEntity<ProjectDTO> editProject(@PathVariable("id") int id, @RequestBody ProjectDTO projectDTO) {
		projectDTO.setProjectId(id);
		try {
			projectDTO = this.projectService.editProject(projectDTO);
			Link selfLink = linkTo(ProjectController.class).slash(projectDTO.getProjectId()).withSelfRel();
			projectDTO.add(selfLink);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<ProjectDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ProjectDTO>(projectDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteProject(@PathVariable("id") int projectId) {
		try {
			boolean ret = this.projectService.deleteProject(projectId);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}/activities", method = RequestMethod.GET)
	public ResponseEntity<List<ActivityDTO>> getActivitiesForProject(@PathVariable int projectId) {
		List<ActivityDTO> activitiesDTO = null;
		try {
			activitiesDTO = projectService.getAllActivitiesForProject(projectId);

			for (ActivityDTO activityDTO : activitiesDTO) {
				Integer activityDTOId = activityDTO.getActivityId();
				Link selfLink = linkTo(ActivityController.class).slash(activityDTOId).withSelfRel();
				activityDTO.add(selfLink);

				Link projectLink = linkTo(ActivityController.class).slash(activityDTOId).slash("project").withRel("project");
				activityDTO.add(projectLink);
			}
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<List<ActivityDTO>>(HttpStatus.NOT_FOUND);
		}
	    return new ResponseEntity<List<ActivityDTO>>(activitiesDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}/activities", method = RequestMethod.POST)
	public ResponseEntity<ActivityDTO> addActivityForProject(@PathVariable int projectId, @RequestBody ActivityDTO activityDTO) {
		try {
			activityDTO = this.projectService.addActivityForProject(projectId, activityDTO);

			Integer activityDTOId = activityDTO.getActivityId();
			Link selfLink = linkTo(ActivityController.class).slash(activityDTOId).withSelfRel();
			activityDTO.add(selfLink);

			Link projectLink = linkTo(ActivityController.class).slash(activityDTOId).slash("project").withRel("project");
			activityDTO.add(projectLink);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}/activities/{activityId}", method = RequestMethod.DELETE)
	public ResponseEntity<ActivityDTO> deleteActivityForProject(@PathVariable int projectId, @PathVariable int activityId) {
		try {
			boolean ret = this.projectService.deleteActivityFromProject(projectId, activityId);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ActivityDTO>(HttpStatus.OK);
	}

}
