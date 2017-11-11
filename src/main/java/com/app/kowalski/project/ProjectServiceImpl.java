package com.app.kowalski.project;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.kowalski.activity.Activity;
import com.app.kowalski.activity.dto.ActivityDTO;
import com.app.kowalski.project.dto.ProjectDTO;
import com.app.kowalski.project.dto.ProjectSummaryDTO;
import com.app.kowalski.project.exception.ProjectNotFoundException;

@Service
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository repository;

	@Autowired
	public ProjectServiceImpl(ProjectRepository projectRepository) {
		this.repository = projectRepository;
	}

	@Override
	public List<ProjectSummaryDTO> getProjects() {
		List<Project> projects = this.repository.findAll();
		return projects.stream()
				.map(project -> new ProjectSummaryDTO(project))
				.collect(Collectors.toList());
	}

	@Override
	public ProjectDTO getProjectById(int id) throws ProjectNotFoundException {
		try {
			return new ProjectDTO(this.repository.getOne(id));
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public ProjectDTO addProject(ProjectDTO projectDTO) {
		// check business rules here
		Project project = new Project().convertToProject(projectDTO);
		project = this.repository.save(project);
		return new ProjectDTO(project);
	}

	@Override
	public ProjectDTO editProject(ProjectDTO projectDTO) throws ProjectNotFoundException {
		// check business rules here
		try {
			Project project = this.repository.getOne(projectDTO.getProjectId());
			project = project.convertToProject(projectDTO);
			project = this.repository.save(project);
			return new ProjectDTO(project);
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public boolean deleteProject(int id) throws ProjectNotFoundException {
		try {
			this.repository.delete(id);
		} catch (Exception e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}
		return true;
	}

	@Override
	public List<ActivityDTO> getAllActivitiesForProject(int id) throws ProjectNotFoundException {
		Project project = null;
		try {
			project = this.repository.getOne(id);
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
			project = this.repository.getOne(projectId);
			activity = new Activity().convertToActivity(activityDTO);
			activity.setProject(project);
			project.addActivity(activity);

			project = this.repository.saveAndFlush(project);

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
			project = this.repository.getOne(projectId);
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

		this.repository.save(project);

		return true;
	}
}
