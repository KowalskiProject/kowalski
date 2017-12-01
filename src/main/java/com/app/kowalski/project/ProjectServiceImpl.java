package com.app.kowalski.project;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.kowalski.activity.Activity;
import com.app.kowalski.activity.ActivityDTO;
import com.app.kowalski.activity.ActivityRepository;
import com.app.kowalski.project.exception.ProjectNotFoundException;
import com.app.kowalski.user.KowalskiUser;
import com.app.kowalski.user.KowalskiUserDTO;
import com.app.kowalski.user.KowalskiUserRepository;
import com.app.kowalski.user.exception.KowalskiUserNotFoundException;

@Service
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;
	private final ActivityRepository activityRepository;
	private final KowalskiUserRepository userRepository;

	@Autowired
	public ProjectServiceImpl(ProjectRepository projectRepository, ActivityRepository activityRepository,
			KowalskiUserRepository userRepository) {
		this.projectRepository = projectRepository;
		this.activityRepository = activityRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<ProjectDTO> getProjects() {
		List<Project> projects = this.projectRepository.findAll();
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
	public ProjectDTO addProject(ProjectDTO projectDTO) {
		// check business rules here
		Project project = new Project().convertToProject(projectDTO);
		project = this.projectRepository.save(project);
		return new ProjectDTO(project);
	}

	@Override
	public ProjectDTO editProject(ProjectDTO projectDTO) throws ProjectNotFoundException {
		// check business rules here
		try {
			Project project = this.projectRepository.getOne(projectDTO.getProjectId());
			project = project.convertToProject(projectDTO);
			project = this.projectRepository.save(project);
			return new ProjectDTO(project);
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public boolean deleteProject(int id) throws ProjectNotFoundException {
		try {
			this.projectRepository.delete(id);
		} catch (Exception e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}
		return true;
	}

	@Override
	public List<ActivityDTO> getAllActivitiesForProject(int id) throws ProjectNotFoundException {
		Project project = null;
		try {
			project = this.projectRepository.getOne(id);
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}

		List<Activity> activities = project.getActivities();
		return activities.stream()
				.map(activity -> new ActivityDTO(activity))
				.collect(Collectors.toList());
	}

	@Override
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
		kowalskiUser.addAccountableProject(project);

		project = this.projectRepository.save(project);
		kowalskiUser = this.userRepository.save(kowalskiUser);

		return new ProjectDTO(project);
	}

	@Override
	public ProjectDTO removeAccountableForProject(Integer projectId) throws ProjectNotFoundException {
		Project project = null;

		try {
			project = this.projectRepository.getOne(projectId);
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}

		KowalskiUser accountable = project.getAccountable();
		project.setAccountable(null);
		accountable.removeAccountableProject(project);

		project = this.projectRepository.save(project);
		accountable = this.userRepository.save(accountable);

		return new ProjectDTO(project);
	}

	@Override
	public Set<KowalskiUserDTO> getProjectMembers(Integer projectId) throws ProjectNotFoundException {
		Project project = null;

		try {
			project = this.projectRepository.getOne(projectId);
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}

		return project.getMembers().stream()
				.map(user -> new KowalskiUserDTO(user))
				.collect(Collectors.toSet());
	}

	@Override
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
		project = this.projectRepository.save(project);

		return new ProjectDTO(project);
	}

	@Override
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

}
