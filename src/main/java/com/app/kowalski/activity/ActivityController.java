package com.app.kowalski.activity;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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

import com.app.kowalski.activity.exception.ActivityNotFoundException;
import com.app.kowalski.project.ProjectController;
import com.app.kowalski.project.ProjectDTO;

@RestController
@RequestMapping("/activities")
public class ActivityController {

	@Autowired
	HttpServletRequest request;
	private ActivityService activityService;

	@Autowired
	ActivityController(ActivityService activityService) {
		this.activityService = activityService;
	}

	@RequestMapping(value = "/{activityId}", method = RequestMethod.GET)
	public ResponseEntity<ActivityDTO> getActivityById(@PathVariable int activityId) {
		ActivityDTO activityDTO = null;
		try {
			activityDTO = this.activityService.getActivityById(activityId);

			Integer activityDTOId = activityDTO.getActivityId();
			Link selfLink = linkTo(ActivityController.class).slash(activityDTOId).withSelfRel();
			activityDTO.add(selfLink);

			Link projectLink = linkTo(ActivityController.class).slash(activityDTOId).slash("project").withRel("project");
			activityDTO.add(projectLink);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{activityId}", method = RequestMethod.PUT)
	public ResponseEntity<ActivityDTO> editActivity(@PathVariable int activityId, @RequestBody ActivityDTO activityDTO) {
		activityDTO.setActivityId(activityId);
		try {
			activityDTO = this.activityService.editActivity(activityDTO);

			Integer activityDTOId = activityDTO.getActivityId();
			Link selfLink = linkTo(ActivityController.class).slash(activityDTO.getActivityId()).withSelfRel();
			activityDTO.add(selfLink);

			Link projectLink = linkTo(ActivityController.class).slash(activityDTOId).slash("project").withRel("project");
			activityDTO.add(projectLink);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/{activityId}/project", method = RequestMethod.GET)
	public ResponseEntity<ProjectDTO> getProjectForActivity(@PathVariable int activityId) {
		ProjectDTO projectDTO = null;
		try {
			projectDTO = this.activityService.getProjectForActivity(activityId);

			// hateoas links
			Link selfLink = linkTo(ProjectController.class).slash(projectDTO.getProjectId()).withSelfRel();
			projectDTO.add(selfLink);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<ProjectDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ProjectDTO>(projectDTO, HttpStatus.OK);
	}

}
