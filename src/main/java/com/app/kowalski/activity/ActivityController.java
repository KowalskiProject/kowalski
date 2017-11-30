package com.app.kowalski.activity;

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

import com.app.kowalski.activity.exception.ActivityNotFoundException;
import com.app.kowalski.task.TaskDTO;
import com.app.kowalski.task.exception.TaskNotFoundException;
import com.app.kowalski.util.HateoasLinksBuilder;

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
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForActivity(activityDTO);

		return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{activityId}", method = RequestMethod.PUT)
	public ResponseEntity<ActivityDTO> editActivity(@PathVariable int activityId, @RequestBody ActivityDTO activityDTO) {
		activityDTO.setActivityId(activityId);
		try {
			activityDTO = this.activityService.editActivity(activityDTO);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForActivity(activityDTO);

		return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/{activityId}/tasks", method = RequestMethod.GET)
	public ResponseEntity<List<TaskDTO>> getTasksForActivity(@PathVariable int activityId) {
		List<TaskDTO> tasksDTO = null;

		try {
			tasksDTO = this.activityService.getTasksForActivity(activityId);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<List<TaskDTO>>(HttpStatus.NOT_FOUND);
		}

		for (TaskDTO taskDTO : tasksDTO)
			HateoasLinksBuilder.createHateoasForTask(taskDTO);

		return new ResponseEntity<List<TaskDTO>>(tasksDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{activityId}/tasks", method = RequestMethod.POST)
	public ResponseEntity<TaskDTO> addTaskForActivity(@PathVariable int activityId, @RequestBody TaskDTO taskDTO) {
		try {
			taskDTO = this.activityService.addTaskForActivity(activityId, taskDTO);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<TaskDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForTask(taskDTO);

		return new ResponseEntity<TaskDTO>(taskDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{activityId}/tasks/{taskId}", method = RequestMethod.POST)
	public ResponseEntity<TaskDTO> deleteTask(@PathVariable int activityId, @PathVariable int taskId) {
		try {
			boolean ret = this.activityService.deleteTaskFromActivity(activityId, taskId);
		} catch (ActivityNotFoundException | TaskNotFoundException e) {
			return new ResponseEntity<TaskDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<TaskDTO>(HttpStatus.OK);
	}

}
