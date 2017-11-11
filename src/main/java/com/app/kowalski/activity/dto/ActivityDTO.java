package com.app.kowalski.activity.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import com.app.kowalski.activity.Activity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Class used to expose activity's parameters through the REST API
 * following the Data Transfer Object pattern.
 */
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class ActivityDTO extends ResourceSupport implements Serializable {

	private Integer activityId;
	private String name;
	private String description;
	private String justification;
	private String status;
	private Date startDate;
	private Date endDate;
	private Integer projectId;

	public ActivityDTO() {}

	public ActivityDTO(Activity activity) {
		this.activityId = activity.getActivityId();
		this.name = activity.getName();
		this.description = activity.getDescription();
		this.justification = activity.getJustification();
		this.status = activity.getStatus();
		this.startDate = activity.getStartDate();
		this.endDate = activity.getEndDate();
		this.projectId = activity.getProject().getProjectId();
	}

	/**
	 * @return the activityId
	 */
	public Integer getActivityId() {
		return activityId;
	}

	/**
	 * @param activityId the phaseId to set
	 */
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
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
	 * @return the justification
	 */
	public String getJustification() {
		return justification;
	}

	/**
	 * @param justification the justification to set
	 */
	public void setJustification(String justification) {
		this.justification = justification;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the projectId
	 */
	public Integer getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
}
