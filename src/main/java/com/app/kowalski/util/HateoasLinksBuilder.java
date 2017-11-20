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
import com.app.kowalski.task.TaskController;
import com.app.kowalski.task.TaskDTO;

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

	public static void createHateoasForTask(TaskDTO taskDTO) {
		Integer taskDTOId = taskDTO.getTaskId();

		Link selfLink = linkTo(TaskController.class).slash(taskDTOId).withSelfRel();
		taskDTO.add(selfLink);

		Link activityLink = linkTo(ActivityController.class).slash(taskDTO.getActivityId()).withRel("activity");
		taskDTO.add(activityLink);
	}
}
