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

import com.app.kowalski.task.exception.TaskNotFoundException;
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
}
