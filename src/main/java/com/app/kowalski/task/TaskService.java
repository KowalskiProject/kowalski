package com.app.kowalski.task;

import com.app.kowalski.task.exception.TaskNotFoundException;

/**
 * Interface to expose allowed methods related to tasks.
 *
 * It receives the TaskDTO object in order to perform the
 * operations and does not expose the domain class.
 *
 * It could receive a JSON though, but the DTO is better to
 * handle the information.
 */
public interface TaskService {

	/**
	 * Get a single task instance through given id.
	 * @param taskId task reference
	 * @return task data
	 *
	 * @throws TaskNotFoundException No task instance was found in the system
	 */
	public TaskDTO getTaskById(int taskId) throws TaskNotFoundException;

	/**
	 * Editis a task with given data
	 * @param taskDTO data to save in given task
	 * @return task after edition
	 *
	 * @throws TaskNotFoundException No task instance was found in the system
	 */
	public TaskDTO editTask(TaskDTO taskDTO) throws TaskNotFoundException;
}
