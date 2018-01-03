/**
 *
 */
package com.app.kowalski.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import com.app.kowalski.da.entities.Task;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Class used to expose tasks' parameters through the REST API
 * following the Data Transfer Object pattern.
 */
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class TaskDTO extends ResourceSupport implements Serializable {

	private Integer taskId;
	private String name;
	private String description;
	private String status;
	private String startDate;
	private String endDate;
	private Integer activityId;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public TaskDTO() {}

	public TaskDTO(Task task) {
		this.taskId = task.getTaskId();
		this.name = task.getName();
		this.description = task.getDescription();
		this.status = task.getStatus();
		this.startDate = sdf.format(task.getStartDate());
		this.endDate = sdf.format(task.getEndDate());
		this.activityId = task.getActivity().getActivityId();
	}

	public TaskDTO(String name, String description, String status, String startDate, String endDate) {
		this.name = name;
		this.description = description;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * @return the taskId
	 */
	public Integer getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
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

}
