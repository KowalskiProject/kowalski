/**
 *
 */
package com.app.kowalski.activity;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import org.springframework.hateoas.ResourceSupport;

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
	private String status;
	private String startDate;
	private String endDate;
	private Integer projectId;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public ActivityDTO() {}

	public ActivityDTO(Activity activity) {
		this.activityId = activity.getActivityId();
		this.name = activity.getName();
		this.description = activity.getDescription();
		this.status = activity.getStatus();
		this.startDate = sdf.format(activity.getStartDate());
		this.endDate = sdf.format(activity.getEndDate());
		this.setProjectId(activity.getProject().getProjectId());
	}

	public ActivityDTO(String name, String description, String status, String startDate, String endDate) {
		this.name = name;
		this.description = description;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * @return the activityId
	 */
	public Integer getActivityId() {
		return activityId;
	}

	/**
	 * @param activityId the activityId to set
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
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
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
