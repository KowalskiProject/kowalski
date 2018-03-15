package com.app.kowalski.web.rest_controllers;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.kowalski.dto.ActivityDTO;
import com.app.kowalski.dto.KowalskiUserDTO;
import com.app.kowalski.dto.TaskDTO;
import com.app.kowalski.exception.ActivityNotFoundException;
import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.exception.TaskNotFoundException;
import com.app.kowalski.services.ActivityService;
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
	public ResponseEntity<List<TaskDTO>> getTasksForActivity(
			@PathVariable int activityId,
			@RequestParam(value = "userId", required = false) Integer kUserId) {
		List<TaskDTO> tasksDTO = null;

		try {
			tasksDTO = this.activityService.getTasksForActivity(activityId, kUserId);
		} catch (ActivityNotFoundException | KowalskiUserNotFoundException e) {
			return new ResponseEntity<List<TaskDTO>>(HttpStatus.NOT_FOUND);
		}

		for (TaskDTO taskDTO : tasksDTO)
			HateoasLinksBuilder.createHateoasForTask(taskDTO);

		return new ResponseEntity<List<TaskDTO>>(tasksDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{activityId}/tasks", method = RequestMethod.POST)
	public ResponseEntity<TaskDTO> addTaskForActivity(@PathVariable int activityId, @Valid @RequestBody TaskDTO taskDTO) {
		try {
			taskDTO = this.activityService.addTaskForActivity(activityId, taskDTO);
		} catch (ActivityNotFoundException | KowalskiUserNotFoundException e) {
			return new ResponseEntity<TaskDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForTask(taskDTO);

		return new ResponseEntity<TaskDTO>(taskDTO, HttpStatus.CREATED);
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

	@RequestMapping(value = "/{activityId}/accountable", method = RequestMethod.GET)
	public ResponseEntity<KowalskiUserDTO> getAccountableForActivity(@PathVariable Integer activityId) {
		KowalskiUserDTO kowalskiUserDTO = null;

		try {
			kowalskiUserDTO = this.activityService.getAccountableForActivity(activityId);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<KowalskiUserDTO>(HttpStatus.NOT_FOUND);
		}

		if (kowalskiUserDTO == null) {
			return new ResponseEntity<KowalskiUserDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForKowalskiUser(kowalskiUserDTO);
		return new ResponseEntity<KowalskiUserDTO>(kowalskiUserDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{activityId}/accountable", method = RequestMethod.POST)
	public ResponseEntity<ActivityDTO> setAccountableForActivity(
			@PathVariable Integer activityId, @RequestBody Integer kUserId) {
		ActivityDTO activityDTO = null;

		try {
			activityDTO = this.activityService.setAccountableForActivity(activityId, kUserId);
		} catch (ActivityNotFoundException | KowalskiUserNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForActivity(activityDTO);
		return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{activityId}/accountable", method = RequestMethod.DELETE)
	public ResponseEntity<ActivityDTO> removeAccountableForActivity(@PathVariable Integer activityId) {
		ActivityDTO activityDTO = null;

		try {
			activityDTO = this.activityService.removeAccountableForActivity(activityId);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForActivity(activityDTO);
		return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{activityId}/members", method = RequestMethod.GET)
	public ResponseEntity<Set<KowalskiUserDTO>> getActivityMembers(@PathVariable int activityId) {
		Set<KowalskiUserDTO> activityUsersDTO = null;

		try {
			activityUsersDTO = this.activityService.getActivityMembers(activityId);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<Set<KowalskiUserDTO>>(HttpStatus.NOT_FOUND);
		}

		for (KowalskiUserDTO kowalskiUserDTO : activityUsersDTO)
			HateoasLinksBuilder.createHateoasForKowalskiUser(kowalskiUserDTO);

		return new ResponseEntity<Set<KowalskiUserDTO>>(activityUsersDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{activityId}/members", method = RequestMethod.POST)
	public ResponseEntity<ActivityDTO> addMemberToActivity(@PathVariable Integer activityId, @RequestBody Integer kUserId) {
		ActivityDTO activityDTO = null;

		try {
			activityDTO = this.activityService.addMemberToActivity(activityId, kUserId);
		} catch (ActivityNotFoundException | KowalskiUserNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForActivity(activityDTO);
		return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{activityId}/members/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<ActivityDTO> removeMemberFromProject(@PathVariable Integer activityId, @PathVariable Integer userId) {
		ActivityDTO activityDTO = null;

		try {
			activityDTO = this.activityService.removeMemberFromActivity(activityId, userId);
		} catch (ActivityNotFoundException | KowalskiUserNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForActivity(activityDTO);
		return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
	}

}
