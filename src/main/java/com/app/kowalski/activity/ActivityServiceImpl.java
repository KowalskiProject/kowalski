package com.app.kowalski.activity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.kowalski.activity.exception.ActivityNotFoundException;
import com.app.kowalski.project.Project;
import com.app.kowalski.project.ProjectDTO;
import com.app.kowalski.task.Task;
import com.app.kowalski.task.TaskDTO;
import com.app.kowalski.task.exception.TaskNotFoundException;
import com.app.kowalski.user.KowalskiUser;
import com.app.kowalski.user.KowalskiUserDTO;
import com.app.kowalski.user.KowalskiUserRepository;
import com.app.kowalski.user.exception.KowalskiUserNotFoundException;

@Service
public class ActivityServiceImpl implements ActivityService {

	private final ActivityRepository activityRepository;
	private final KowalskiUserRepository userRepository;

	@Autowired
	public ActivityServiceImpl(ActivityRepository activityRepository, KowalskiUserRepository userRepository) {
		this.activityRepository = activityRepository;
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
	public List<TaskDTO> getTasksForActivity(int activityId) throws ActivityNotFoundException {
		Activity activity = null;

		try {
			activity = this.activityRepository.getOne(activityId);
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}

		return activity.getTasks().stream()
				.map(task -> new TaskDTO(task))
				.collect(Collectors.toList());
	}

	@Override
	public TaskDTO addTaskForActivity(int activityId, TaskDTO taskDTO) throws ActivityNotFoundException {
		Activity activity = null;
		Task task = null;

		try {
			activity = this.activityRepository.getOne(activityId);
			task = new Task().convertToTask(taskDTO);
			task.setActivity(activity);
			activity.addTask(task);

			activity = this.activityRepository.saveAndFlush(activity);

			// update task id
			task = activity.getTasks().get(activity.getTasks().size() - 1);
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}

		return new TaskDTO(task);
	}

	@Override
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
		kowalskiUser.addAccountableActivity(activity);

		activity = this.activityRepository.save(activity);
		kowalskiUser = this.userRepository.save(kowalskiUser);

		return new ActivityDTO(activity);
	}

	@Override
	public ActivityDTO removeAccountableForActivity(Integer activityId) throws ActivityNotFoundException {
		Activity activity = null;

		try {
			activity = this.activityRepository.getOne(activityId);
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}

		KowalskiUser accountable = activity.getAccountable();
		activity.setAccountable(null);
		accountable.removeAccountableActivity(activity);

		activity = this.activityRepository.save(activity);
		accountable = this.userRepository.save(accountable);

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

}
