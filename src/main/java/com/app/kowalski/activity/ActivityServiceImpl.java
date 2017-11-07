package com.app.kowalski.activity;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.kowalski.activity.dto.ActivityDTO;
import com.app.kowalski.activity.exception.ActivityNotFoundException;

@Service
public class ActivityServiceImpl implements ActivityService {

	private final ActivityRepository repository;

	@Autowired
	public ActivityServiceImpl(ActivityRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<ActivityDTO> getActivities() {
		List<Activity> activities = this.repository.findAll();
		return activities.stream()
				.map(activity -> new ActivityDTO(activity))
				.collect(Collectors.toList());
	}

	@Override
	public ActivityDTO getActivityById(int id) throws ActivityNotFoundException {
		try {
			return new ActivityDTO(this.repository.getOne(id));
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public ActivityDTO addActivity(ActivityDTO activityDTO) {
		// check business rules here
		Activity phase = new Activity().convertToActivity(activityDTO);
		phase = this.repository.save(phase);
		return new ActivityDTO(phase);
	}

	@Override
	public ActivityDTO editActivity(ActivityDTO activityDTO) throws ActivityNotFoundException {
		// check business rules here
		try {
			Activity activity = this.repository.getOne(activityDTO.getActivityId());
			activity = activity.convertToActivity(activityDTO);
			activity = this.repository.save(activity);
			return new ActivityDTO(activity);
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public boolean deleteActivity(int id) throws ActivityNotFoundException {
		try {
			this.repository.delete(id);
		} catch (EntityNotFoundException e) {
			throw new ActivityNotFoundException(e.getMessage(), e.getCause());
		}
		return true;
	}

}
