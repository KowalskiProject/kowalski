package com.app.kowalski.task;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.exception.TaskNotFoundException;
import com.app.kowalski.user.KowalskiUser;
import com.app.kowalski.user.KowalskiUserDTO;
import com.app.kowalski.user.KowalskiUserRepository;

@Service
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;
	private final KowalskiUserRepository userRepository;

	@Autowired
	public TaskServiceImpl(TaskRepository taskRepository, KowalskiUserRepository userRepository) {
		this.taskRepository = taskRepository;
		this.userRepository = userRepository;
	}

	@Override
	public TaskDTO getTaskById(int taskId) throws TaskNotFoundException {
		Task task = null;

		try {
			task = this.taskRepository.getOne(taskId);
		} catch (EntityNotFoundException e) {
			throw new TaskNotFoundException(e.getMessage(), e.getCause());
		}

		return new TaskDTO(task);
	}

	@Override
	@Transactional
	public TaskDTO editTask(TaskDTO taskDTO) throws TaskNotFoundException {
		Task task = null;

		try {
			task = this.taskRepository.getOne(taskDTO.getTaskId());
			task = task.convertToTask(taskDTO);
			task = this.taskRepository.save(task);
		} catch (EntityNotFoundException e) {
			throw new TaskNotFoundException(e.getMessage(), e.getCause());
		}

		return new TaskDTO(task);
	}

	@Override
	public KowalskiUserDTO getAccountableForTask(Integer taskId) throws TaskNotFoundException {
		Task task = null;

		try {
			task = this.taskRepository.getOne(taskId);
		} catch (EntityNotFoundException e) {
			throw new TaskNotFoundException(e.getMessage(), e.getCause());
		}

		if (task.getAccountable() != null)
			return new KowalskiUserDTO(task.getAccountable());
		else
			return null;
	}

	@Override
	@Transactional
	public TaskDTO setAccountableForTask(Integer taskId, Integer kUserId)
			throws TaskNotFoundException, KowalskiUserNotFoundException {
		Task task = null;
		KowalskiUser kowalskiUser = null;

		try {
			task = this.taskRepository.getOne(taskId);
		} catch (EntityNotFoundException e) {
			throw new TaskNotFoundException(e.getMessage(), e.getCause());
		}

		try {
			kowalskiUser = this.userRepository.getOne(kUserId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		task.setAccountable(kowalskiUser);
		kowalskiUser.addAccountableTask(task);

		task = this.taskRepository.save(task);
		kowalskiUser = this.userRepository.save(kowalskiUser);

		return new TaskDTO(task);
	}

	@Override
	@Transactional
	public TaskDTO removeAccountableForTask(Integer taskId) throws TaskNotFoundException {
		Task task = null;

		try {
			task = this.taskRepository.getOne(taskId);
		} catch (EntityNotFoundException e) {
			throw new TaskNotFoundException(e.getMessage(), e.getCause());
		}

		KowalskiUser accountable = task.getAccountable();
		task.setAccountable(null);
		accountable.removeAccountableTask(task);

		task = this.taskRepository.save(task);
		accountable = this.userRepository.save(accountable);

		return new TaskDTO(task);
	}

}
