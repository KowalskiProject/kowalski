package com.app.kowalski.task;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.kowalski.task.exception.TaskNotFoundException;

@Service
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;

	@Autowired
	public TaskServiceImpl(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
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

}
