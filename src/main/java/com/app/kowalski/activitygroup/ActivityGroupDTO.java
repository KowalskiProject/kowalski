package com.app.kowalski.activitygroup;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class ActivityGroupDTO extends ResourceSupport implements Serializable {

	private Integer activityGroupId;
	private String name;
	private String description;

	public ActivityGroupDTO() {}

	public ActivityGroupDTO(ActivityGroup activityGroup) {
		this.activityGroupId = activityGroup.getActivityGroupId();
		this.name = activityGroup.getName();
		this.description = activityGroup.getDescription();
	}

	public ActivityGroupDTO(String name, String description) {
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

}
