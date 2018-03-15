package com.app.kowalski.services.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.kowalski.da.entities.Activity;
import com.app.kowalski.da.entities.KowalskiUser;
import com.app.kowalski.da.entities.Project;
import com.app.kowalski.da.entities.Task;
import com.app.kowalski.da.repositories.ActivityRepository;
import com.app.kowalski.da.repositories.KowalskiUserRepository;
import com.app.kowalski.da.repositories.TaskRepository;
import com.app.kowalski.dto.ActivityDTO;
import com.app.kowalski.dto.KowalskiUserDTO;
import com.app.kowalski.dto.ProjectDTO;
import com.app.kowalski.dto.TaskDTO;
import com.app.kowalski.exception.ActivityNotFoundException;
import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.exception.TaskNotFoundException;
import com.app.kowalski.services.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService {

	private final ActivityRepository activityRepository;
	private final TaskRepository taskRepository;
	private final KowalskiUserRepository userRepository;

	@Autowired
	public ActivityServiceImpl(ActivityRepository activityRepository, TaskRepository taskRepository,
			KowalskiUserRepository userRepository) {
		this.activityRepository = activityRepository;
		this.taskRepository = taskRepository;
		this.userRepository = userRepository;
	}

	@Override
	public ActivityDTO getActivityById(int id) throws ActivityNotFoundException {
		try {
			return new ActivityDTO(this.activityRepository.getOne(id));
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}
	}

	@Override
	@Transactional
	public ActivityDTO editActivity(ActivityDTO activityDTO) throws ActivityNotFoundException {
		// check business rules here
		try {
			Activity activity = this.activityRepository.getOne(activityDTO.getActivityId());
			activity = activity.convertToActivity(activityDTO);
			activity = this.activityRepository.save(activity);
			return new ActivityDTO(activity);
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public ProjectDTO getProjectForActivity(int activityId) throws ActivityNotFoundException {
		Project project = null;
		try {
			Activity activity = this.activityRepository.getOne(activityId);
			project = activity.getProject();
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}

		return new ProjectDTO(project);
	}

	@Override
	public List<TaskDTO> getTasksForActivity(int activityId, Integer kUserId)
			throws ActivityNotFoundException, KowalskiUserNotFoundException {
		Activity activity = null;
		KowalskiUser user = null;
		List<Task> tasks = null;

		if (kUserId != null) {
			try {
				user = this.userRepository.getOne(kUserId);
			} catch (EntityNotFoundException e) {
				throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
			}
		}

		try {
			activity = this.activityRepository.getOne(activityId);
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}

		if (user != null)
			tasks = this.taskRepository.findByActivityAndAccountable(activity, user);
		else
			tasks = activity.getTasks();

		return tasks.stream()
				.map(task -> new TaskDTO(task))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public TaskDTO addTaskForActivity(int activityId, TaskDTO taskDTO)
			throws ActivityNotFoundException, KowalskiUserNotFoundException {
		Activity activity = null;
		Task task = null;
		KowalskiUser user = null;

		// accountable is not required during task creation
		if (taskDTO.getAccountableId() != null) {
			try {
				user = this.userRepository.getOne(taskDTO.getAccountableId());
			} catch (EntityNotFoundException e) {
				throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
			}
		}

		try {
			activity = this.activityRepository.getOne(activityId);
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}

		task = new Task().convertToTask(taskDTO);
		task.setActivity(activity);
		activity.addTask(task);

		if (user != null)
			task.setAccountable(user);

		activity = this.activityRepository.saveAndFlush(activity);

		// update task id
		task = activity.getTasks().get(activity.getTasks().size() - 1);

		return new TaskDTO(task);
	}

	@Override
	@Transactional
	public boolean deleteTaskFromActivity(int activityId, int taskId)
			throws ActivityNotFoundException, TaskNotFoundException {
		Activity activity = null;

		try {
			activity = this.activityRepository.getOne(activityId);
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}

		List<Task> tasks = activity.getTasks();
		for (Task task : tasks) {
			if (task.getTaskId() == taskId) {
				tasks.remove(task);
				break;
			}
		}

		this.activityRepository.save(activity);

		return true;
	}

	@Override
	public KowalskiUserDTO getAccountableForActivity(Integer activityId) throws ActivityNotFoundException {
		Activity activity = null;

		try {
			activity = this.activityRepository.getOne(activityId);
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}

		if (activity.getAccountable() != null)
			return new KowalskiUserDTO(activity.getAccountable());
		else
			return null;
	}

	@Override
	@Transactional
	public ActivityDTO setAccountableForActivity(Integer activityId, Integer kUserId)
			throws ActivityNotFoundException, KowalskiUserNotFoundException {
		Activity activity = null;
		KowalskiUser kowalskiUser = null;

		try {
			activity = this.activityRepository.getOne(activityId);
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}

		try {
			kowalskiUser = this.userRepository.getOne(kUserId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		activity.setAccountable(kowalskiUser);
		activity = this.activityRepository.save(activity);

		return new ActivityDTO(activity);
	}

	@Override
	@Transactional
	public ActivityDTO removeAccountableForActivity(Integer activityId) throws ActivityNotFoundException {
		Activity activity = null;

		try {
			activity = this.activityRepository.getOne(activityId);
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}

		KowalskiUser accountable = activity.getAccountable();
		activity.setAccountable(null);

		activity = this.activityRepository.save(activity);

		return new ActivityDTO(activity);
	}

	@Override
	public Set<KowalskiUserDTO> getActivityMembers(Integer activityId) throws ActivityNotFoundException {
		Activity activity = null;

		try {
			activity = this.activityRepository.getOne(activityId);
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}

		return activity.getMembers().stream()
				.map(user -> new KowalskiUserDTO(user))
				.collect(Collectors.toSet());
	}

	@Override
	public ActivityDTO addMemberToActivity(Integer activityId, Integer kUserId)
			throws ActivityNotFoundException, KowalskiUserNotFoundException {
		Activity activity = null;
		KowalskiUser kowalskiUser = null;

		try {
			activity = this.activityRepository.getOne(activityId);
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}

		try {
			kowalskiUser = this.userRepository.getOne(kUserId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		activity.addMember(kowalskiUser);
		activity = this.activityRepository.save(activity);

		return new ActivityDTO(activity);
	}

	@Override
	@Transactional
	public ActivityDTO removeMemberFromActivity(Integer activityId, Integer kUserId)
			throws ActivityNotFoundException, KowalskiUserNotFoundException {
		Activity activity = null;
		KowalskiUser kowalskiUser = null;

		try {
			activity = this.activityRepository.getOne(activityId);
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}

		try {
			kowalskiUser = this.userRepository.getOne(kUserId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		activity.removeMember(kowalskiUser);
		activity = this.activityRepository.save(activity);

		return new ActivityDTO(activity);
	}

	@Override
	public List<ActivityDTO> getAccountableActivitiesForUser(Integer kUserId) throws KowalskiUserNotFoundException {
		KowalskiUser kowalskiUser = null;

		try {
			kowalskiUser = this.userRepository.getOne(kUserId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		return this.activityRepository.findByAccountable(kowalskiUser).stream()
				.map(activity -> new ActivityDTO(activity))
				.collect(Collectors.toList());
	}
}
