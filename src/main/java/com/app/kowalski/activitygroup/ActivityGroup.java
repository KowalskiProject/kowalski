package com.app.kowalski.activitygroup;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.app.kowalski.activity.Activity;

@Entity
@Table(name = "activitygroup")
public class ActivityGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer activityGroupId;

	private String name;
	private String description;

	@ManyToMany(mappedBy = "groups")
    private Set<Activity> activities = new HashSet<>();

	public ActivityGroup() {}

	public ActivityGroup(ActivityGroupDTO activityGroupDTO) {
		this.name = activityGroupDTO.getName();
		this.description = activityGroupDTO.getDescription();
	}

	public ActivityGroup(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the activityGroupId
	 */
	public Integer getActivityGroupId() {
		return activityGroupId;
	}

	/**
	 * @param activityGroupId the activityGroupId to set
	 */
	public void setActivityGroupId(Integer activityGroupId) {
		this.activityGroupId = activityGroupId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the activities
	 */
	public Set<Activity> getActivities() {
		return activities;
	}

}
