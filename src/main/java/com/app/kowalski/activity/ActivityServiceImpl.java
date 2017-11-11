package com.app.kowalski.activity;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.kowalski.activity.exception.ActivityNotFoundException;
import com.app.kowalski.project.Project;
import com.app.kowalski.project.ProjectDTO;

@Service
public class ActivityServiceImpl implements ActivityService {

	private final ActivityRepository activityRepository;

	@Autowired
	public ActivityServiceImpl(ActivityRepository repository) {
		this.activityRepository = repository;
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
}
