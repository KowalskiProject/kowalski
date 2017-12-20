package com.app.kowalski.task;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.exception.TaskNotFoundException;
import com.app.kowalski.user.KowalskiUserDTO;
import com.app.kowalski.util.HateoasLinksBuilder;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	HttpServletRequest request;
	private TaskService taskService;

	@Autowired
	TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
	public ResponseEntity<TaskDTO> getTaskById(@PathVariable int taskId) {
		TaskDTO taskDTO = null;
		try {
			taskDTO = this.taskService.getTaskById(taskId);
		} catch (TaskNotFoundException e) {
			return new ResponseEntity<TaskDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForTask(taskDTO);

		return new ResponseEntity<TaskDTO>(taskDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{taskId}", method = RequestMethod.PUT)
	public ResponseEntity<TaskDTO> editTask(@PathVariable int taskId, @RequestBody TaskDTO taskDTO) {
		taskDTO.setTaskId(taskId);
		try {
			taskDTO = this.taskService.editTask(taskDTO);
		} catch (TaskNotFoundException e) {
			return new ResponseEntity<TaskDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForTask(taskDTO);

		return new ResponseEntity<TaskDTO>(taskDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{taskId}/accountable", method = RequestMethod.GET)
	public ResponseEntity<KowalskiUserDTO> getAccountableForTask(@PathVariable Integer taskId) {
		KowalskiUserDTO kowalskiUserDTO = null;

		try {
			kowalskiUserDTO = this.taskService.getAccountableForTask(taskId);
		} catch (TaskNotFoundException e) {
			return new ResponseEntity<KowalskiUserDTO>(HttpStatus.NOT_FOUND);
		}

		if (kowalskiUserDTO == null) {
			return new ResponseEntity<KowalskiUserDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForKowalskiUser(kowalskiUserDTO);
		return new ResponseEntity<KowalskiUserDTO>(kowalskiUserDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{taskId}/accountable", method = RequestMethod.POST)
	public ResponseEntity<TaskDTO> setAccountableForTask(
			@PathVariable Integer taskId, @RequestBody Integer kUserId) {
		TaskDTO taskDTO = null;

		try {
			taskDTO = this.taskService.setAccountableForTask(taskId, kUserId);
		} catch (TaskNotFoundException | KowalskiUserNotFoundException e) {
			return new ResponseEntity<TaskDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForTask(taskDTO);
		return new ResponseEntity<TaskDTO>(taskDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{taskId}/accountable", method = RequestMethod.DELETE)
	public ResponseEntity<TaskDTO> removeAccountableForTask(@PathVariable Integer taskId) {
		TaskDTO taskDTO = null;

		try {
			taskDTO = this.taskService.removeAccountableForTask(taskId);
		} catch (TaskNotFoundException e) {
			return new ResponseEntity<TaskDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForTask(taskDTO);
		return new ResponseEntity<TaskDTO>(taskDTO, HttpStatus.OK);
	}
}
