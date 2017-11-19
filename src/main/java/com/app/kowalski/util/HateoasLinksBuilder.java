package com.app.kowalski.util;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;

import com.app.kowalski.activity.ActivityController;
import com.app.kowalski.activity.ActivityDTO;
import com.app.kowalski.project.ProjectController;
import com.app.kowalski.project.ProjectDTO;

public class HateoasLinksBuilder {

	public static void createHateoasForProject(ProjectDTO projectDTO) {
		Link selfLink = linkTo(ProjectController.class).slash(projectDTO.getProjectId()).withSelfRel();
		projectDTO.add(selfLink);

		ResponseEntity<List<ActivityDTO>> methodLinkBuilder = methodOn(ProjectController.class)
				.getActivitiesForProject(projectDTO.getProjectId());

		Link activitiesLink = linkTo(methodLinkBuilder).withRel("activities");
		projectDTO.add(activitiesLink);
	}

	public static void createHateoasForActivity(ActivityDTO activityDTO) {
		Integer activityDTOId = activityDTO.getActivityId();

		Link selfLink = linkTo(ActivityController.class).slash(activityDTOId).withSelfRel();
		activityDTO.add(selfLink);

		Link projectLink = linkTo(ProjectController.class).slash(activityDTO.getProjectId()).withRel("project");
		activityDTO.add(projectLink);
	}
}
