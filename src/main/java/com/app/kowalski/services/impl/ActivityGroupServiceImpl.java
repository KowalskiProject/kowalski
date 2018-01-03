package com.app.kowalski.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.app.kowalski.da.entities.ActivityGroup;
import com.app.kowalski.da.repositories.ActivityGroupRepository;
import com.app.kowalski.dto.ActivityGroupDTO;
import com.app.kowalski.services.ActivityGroupService;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.kowalski.exception.ActivityGroupNotFoundException;

public class ActivityGroupServiceImpl implements ActivityGroupService {

	private final ActivityGroupRepository agRepository;

	@Autowired
	public ActivityGroupServiceImpl(ActivityGroupRepository agRepository) {
		this.agRepository = agRepository;
	}

	@Override
	public List<ActivityGroupDTO> getActivityGroups() {
		List<ActivityGroup> activityGroups = this.agRepository.findAll();
		return activityGroups.stream()
				.map(activityGroup -> new ActivityGroupDTO(activityGroup))
				.collect(Collectors.toList());
	}

	@Override
	public ActivityGroupDTO addActivityGroup(ActivityGroupDTO activityGroupDTO) {
		ActivityGroup activityGroup = new ActivityGroup(activityGroupDTO);
		activityGroup = this.agRepository.save(activityGroup);
		return new ActivityGroupDTO(activityGroup);
	}

	@Override
	public ActivityGroupDTO getActivityGroupById(Integer activityGroupId) throws ActivityGroupNotFoundException {
		ActivityGroup activityGroup = null;

		try {
			activityGroup = this.agRepository.getOne(activityGroupId);
		} catch (EntityNotFoundException e) {
			throw new ActivityGroupNotFoundException(e.getMessage(), e.getCause());
		}

		return new ActivityGroupDTO(activityGroup);
	}

	@Override
	public ActivityGroupDTO editActivityGroup(Integer activityGroupId, ActivityGroupDTO activityGroupDTO)
			throws ActivityGroupNotFoundException {
		ActivityGroup activityGroup = null;

		try {
			activityGroup = this.agRepository.getOne(activityGroupId);
		} catch (EntityNotFoundException e) {
			throw new ActivityGroupNotFoundException(e.getMessage(), e.getCause());
		}

		activityGroup = new ActivityGroup(activityGroupDTO);
		activityGroup.setActivityGroupId(activityGroupId);

		activityGroup = this.agRepository.save(activityGroup);

		return new ActivityGroupDTO(activityGroup);
	}

	@Override
	public boolean removeActivityGroup(Integer activityGroupId) throws ActivityGroupNotFoundException {
		try {
			this.agRepository.delete(activityGroupId);
		} catch (EntityNotFoundException e) {
			throw new ActivityGroupNotFoundException(e.getMessage(), e.getCause());
		}

		return true;
	}

}
