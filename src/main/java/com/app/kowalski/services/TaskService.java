package com.app.kowalski.services;

import java.util.List;

import com.app.kowalski.dto.TaskDTO;
import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.exception.TaskNotFoundException;
import com.app.kowalski.dto.KowalskiUserDTO;

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
	 * Edits a task with given data
	 * @param taskDTO data to save in given task
	 * @return task after edition
	 *
	 * @throws TaskNotFoundException No task instance was found in the system
	 */
	public TaskDTO editTask(TaskDTO taskDTO) throws TaskNotFoundException;

	/**
	 * Returns the accountable user for given task
	 * @param taskId task reference
	 * @return user data
	 *
	 * @throws TaskNotFoundException No task instance was found in the system
	 */
	public KowalskiUserDTO getAccountableForTask(Integer taskId) throws TaskNotFoundException;

	/**
	 * Associates given user as accountable for given task
	 * @param taskId task reference
	 * @param kUserId kowalski reference
	 * @return task data after association
	 *
	 * @throws TaskNotFoundException No task instance was found in the system
	 * @throws KowalskiUserNotFoundException No kowalski user was found in the system
	 */
	public TaskDTO setAccountableForTask(Integer taskId, Integer kUserId)
			throws TaskNotFoundException, KowalskiUserNotFoundException;

	/**
	 * Removes the association of the task and the accountable user
	 * @param taskId task reference
	 * @return task data after removal
	 *
	 * @throws TaskNotFoundException No task instance was found in the system
	 */
	public TaskDTO removeAccountableForTask(Integer taskId) throws TaskNotFoundException;

	/**
	 * Returns all tasks where given user is responsible
	 * @param kUserId user reference
	 * @return List of tasks
	 *
	 * @throws KowalskiUserNotFoundException No KowalskiUser instance found in the system
	 */
	public List<TaskDTO> getAccountableTasksForUser(Integer kUserId) throws KowalskiUserNotFoundException;
}
