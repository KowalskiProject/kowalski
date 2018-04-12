package com.app.kowalski.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.kowalski.da.entities.Activity;
import com.app.kowalski.da.entities.KowalskiUser;
import com.app.kowalski.da.entities.Project;
import com.app.kowalski.da.repositories.ActivityRepository;
import com.app.kowalski.da.repositories.KowalskiUserRepository;
import com.app.kowalski.da.repositories.ProjectRepository;
import com.app.kowalski.da.repositories.TaskRepository;
import com.app.kowalski.dto.ActivityDTO;
import com.app.kowalski.dto.KowalskiUserDTO;
import com.app.kowalski.dto.ProjectDTO;
import com.app.kowalski.dto.TaskDTO;
import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.exception.ProjectNotFoundException;
import com.app.kowalski.services.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;
	private final ActivityRepository activityRepository;
	private final TaskRepository taskRepository;
	private final KowalskiUserRepository userRepository;

	@Autowired
	public ProjectServiceImpl(ProjectRepository projectRepository, ActivityRepository activityRepository,
			TaskRepository taskRepository, KowalskiUserRepository userRepository) {
		this.projectRepository = projectRepository;
		this.activityRepository = activityRepository;
		this.taskRepository = taskRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<ProjectDTO> getProjects(Integer userId) {
		List<Project> projects = null;

		if (userId != null) {
			KowalskiUser user = this.userRepository.getOne(userId);
			if (user != null)
				projects = this.projectRepository.findWithMember(user);
			else
				projects = this.projectRepository.findAll();
		}
		else {
			projects = this.projectRepository.findAll();
		}

		return projects.stream()
				.map(project -> new ProjectDTO(project))
				.collect(Collectors.toList());
	}

	@Override
	public ProjectDTO getProjectById(int id) throws ProjectNotFoundException {
		try {
			return new ProjectDTO(this.projectRepository.getOne(id));
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}
	}

	@Override
	@Transactional
	public ProjectDTO addProject(ProjectDTO projectDTO) throws DataIntegrityViolationException {
		// already exists a project with this code: abort
		if (this.projectRepository.findByCode(projectDTO.getCode()).size() > 0) {
			throw new DataIntegrityViolationException(projectDTO.getCode());
		}
		Project project = new Project(projectDTO);
		project.setAccountable(this.userRepository.getOne(projectDTO.getAccountableId()));
		project = this.projectRepository.save(project);
		return new ProjectDTO(project);
	}

	@Override
	@Transactional
	public ProjectDTO editProject(ProjectDTO projectDTO) throws ProjectNotFoundException {
		// check business rules here
		try {
			Project project = this.projectRepository.getOne(projectDTO.getProjectId());
			project = new Project(projectDTO);
			project.setProjectId(projectDTO.getProjectId());
			project = this.projectRepository.save(project);
			return new ProjectDTO(project);
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}
	}

	@Override
	@Transactional
	public boolean deleteProject(int id) throws ProjectNotFoundException {
		try {
			this.projectRepository.delete(id);
		} catch (Exception e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}
		return true;
	}

	@Override
	public List<ActivityDTO> getAllActivitiesForProject(Integer id, Boolean includeTasks, Integer tasksAccountableId)
			throws ProjectNotFoundException, KowalskiUserNotFoundException {
		Project project = null;
		KowalskiUser accountable = null;
		List<ActivityDTO> results = new ArrayList<ActivityDTO>();

		try {
			project = this.projectRepository.getOne(id);
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}

		if (tasksAccountableId != null) {
			try {
				accountable = this.userRepository.getOne(tasksAccountableId);
			} catch (EntityNotFoundException e) {
				throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
			}
		}

		List<Activity> activities = project.getActivities();

		// no flags included: return only activities
		if (!includeTasks) {
			results = activities.stream().map(activity -> new ActivityDTO(activity)).collect(Collectors.toList());
		}
		else {
			// include tasks but without a specific user
			if (tasksAccountableId == null) {
				results = activities.stream().map(activity -> {
					ActivityDTO activityDTO = new ActivityDTO(activity);
					activityDTO.setTasks(activity.getTasks().stream().map(task -> new TaskDTO(task)).collect(Collectors.toList()));
					return activityDTO;
				}).collect(Collectors.toList());
			}
			else {
				// include tasks of a specific user
				for (Activity activity : activities) {
					ActivityDTO activityDTO = new ActivityDTO(activity);
					activityDTO.setTasks(this.taskRepository.findByActivityAndAccountable(activity, accountable).stream()
							.map(task -> new TaskDTO(task)).collect(Collectors.toList()));
					results.add(activityDTO);
				}
			}
		}

		return results;
	}

	@Override
	@Transactional
	public ActivityDTO addActivityForProject(int projectId, ActivityDTO activityDTO) throws ProjectNotFoundException {
		Project project = null;
		Activity activity = null;

		try {
			project = this.projectRepository.getOne(projectId);
			activity = new Activity().convertToActivity(activityDTO);
			activity.setProject(project);
			project.addActivity(activity);

			project = this.projectRepository.saveAndFlush(project);

			// update activity id
			activity = project.getActivities().get(project.getActivities().size() - 1);
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}

		return new ActivityDTO(activity);
	}

	@Override
	@Transactional
	public boolean deleteActivityFromProject(int projectId, int activityId) throws ProjectNotFoundException {
		Project project = null;
		try {
			project = this.projectRepository.getOne(projectId);
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}

		List<Activity> activities = project.getActivities();
		for (Activity activity : activities) {
			if (activity.getActivityId() == activityId) {
				activities.remove(activity);
				break;
			}
		}

		this.projectRepository.save(project);

		return true;
	}

	@Override
	public KowalskiUserDTO getAccountableForProject(int projectId) throws ProjectNotFoundException {
		Project project = null;

		try {
			project = this.projectRepository.getOne(projectId);
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}

		if (project.getAccountable() != null)
			return new KowalskiUserDTO(project.getAccountable());
		else
			return null;
	}

	@Override
	@Transactional
	public ProjectDTO setAccountableForProject(Integer projectId, Integer kUserId)
			throws ProjectNotFoundException, KowalskiUserNotFoundException {
		Project project = null;
		KowalskiUser kowalskiUser = null;

		try {
			project = this.projectRepository.getOne(projectId);
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}

		try {
			kowalskiUser = this.userRepository.getOne(kUserId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		project.setAccountable(kowalskiUser);
		project = this.projectRepository.save(project);

		//kowalskiUser.addAccountableProject(project);
		//kowalskiUser = this.userRepository.save(kowalskiUser);

		return new ProjectDTO(project);
	}

	@Override
	@Transactional
	public ProjectDTO removeAccountableForProject(Integer projectId) throws ProjectNotFoundException {
		Project project = null;

		try {
			project = this.projectRepository.getOne(projectId);
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}

		KowalskiUser accountable = project.getAccountable();
		project.setAccountable(null);
		//accountable.removeAccountableProject(project);

		project = this.projectRepository.save(project);
		//accountable = this.userRepository.save(accountable);

		return new ProjectDTO(project);
	}

	@Override
	@Transactional
	public List<KowalskiUserDTO> getProjectMembers(Integer projectId) throws ProjectNotFoundException {
		Project project = null;

		try {
			project = this.projectRepository.getOne(projectId);
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}

		return project.getMembers().stream()
				.map(user -> new KowalskiUserDTO(user))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public ProjectDTO addMemberToProject(Integer projectId, Integer kUserId)
			throws ProjectNotFoundException, KowalskiUserNotFoundException {
		Project project = null;
		KowalskiUser kowalskiUser = null;

		try {
			project = this.projectRepository.getOne(projectId);
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}

		try {
			kowalskiUser = this.userRepository.getOne(kUserId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		project.addMember(kowalskiUser);
		project = this.projectRepository.saveAndFlush(project);

		return new ProjectDTO(project);
	}

	@Override
	@Transactional
	public ProjectDTO removeMemberFromProject(Integer projectId, Integer kUserId)
			throws ProjectNotFoundException, KowalskiUserNotFoundException {
		Project project = null;
		KowalskiUser kowalskiUser = null;

		try {
			project = this.projectRepository.getOne(projectId);
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}

		try {
			kowalskiUser = this.userRepository.getOne(kUserId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		project.removeMember(kowalskiUser);
		project = this.projectRepository.save(project);

		return new ProjectDTO(project);
	}

	@Override
	public List<ProjectDTO> getAccountableProjectsForUser(Integer kUserId) throws KowalskiUserNotFoundException {
		KowalskiUser kowalskiUser = null;

		try {
			kowalskiUser = this.userRepository.getOne(kUserId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		return this.projectRepository.findByAccountable(kowalskiUser).stream()
				.map(project -> new ProjectDTO(project))
				.collect(Collectors.toList());
	}

}
