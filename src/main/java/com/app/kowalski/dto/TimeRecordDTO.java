package com.app.kowalski.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import org.springframework.hateoas.ResourceSupport;

import com.app.kowalski.da.entities.TimeRecord;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * Class used to expose time record parameters through the REST API
 * following the Data Transfer Object pattern.
 */
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class TimeRecordDTO extends ResourceSupport implements Serializable {

	private Integer trId;

	@ApiModelProperty(required = true)
	private Integer userId;

	@ApiModelProperty(required = true)
	private Integer taskId;

	private String taskName;
	private Integer activityId;
	private String activityName;
	private Integer projectId;
	private String projectName;
	private String projectCode;

	@ApiModelProperty(required = true)
	private String createdDate;

	@ApiModelProperty(required = true)
	private String reportedDay;

	@ApiModelProperty(required = true)
	private String reportedTime;

	@ApiModelProperty(required = true)
	private String comment;
	private String state;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

	public TimeRecordDTO() {}

	public TimeRecordDTO(TimeRecord timeRecord) {
		this.trId = timeRecord.getTrId();
		this.userId = timeRecord.getUser().getkUserId();
		this.taskId = timeRecord.getTask().getTaskId();
		this.taskName = timeRecord.getTask().getName();
		this.activityId = timeRecord.getActivity().getActivityId();
		this.activityName = timeRecord.getActivity().getName();
		this.projectId = timeRecord.getProject().getProjectId();
		this.projectName = timeRecord.getProject().getName();
		this.projectCode = timeRecord.getProject().getCode();
		this.createdDate = sdf.format(timeRecord.getCreateDate());
		this.reportedDay = timeRecord.getReportedDay().format(dateFormatter);
		this.reportedTime = timeRecord.getReportedTime().format(timeFormatter);
		this.comment = timeRecord.getComment();
		this.state = timeRecord.getState().toString();
	}

	public TimeRecordDTO(Integer userId, Integer taskId, String reportedDay, String reportedTime, String comment) {
		this.userId = userId;
		this.taskId = taskId;
		this.reportedDay = reportedDay;
		this.reportedTime = reportedTime;
		this.comment = comment;
	}

	/**
	 * @return the trId
	 */
	public Integer getTrId() {
		return trId;
	}

	/**
	 * @param trId the trId to set
	 */
	public void setTrId(Integer trId) {
		this.trId = trId;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the reportedDay
	 */
	public String getReportedDay() {
		return reportedDay;
	}

	/**
	 * @param reportedDay the reportedDay to set
	 */
	public void setReportedDay(String reportedDay) {
		this.reportedDay = reportedDay;
	}

	/**
	 * @return the reportedTime
	 */
	public String getReportedTime() {
		return reportedTime;
	}

	/**
	 * @param reportedTime the reportedTime to set
	 */
	public void setReportedTime(String reportedTime) {
		this.reportedTime = reportedTime;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
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
	 * @return the activityName
	 */
	public String getActivityName() {
		return activityName;
	}

	/**
	 * @param activityName the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
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

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the projectCode
	 */
	public String getProjectCode() {
		return projectCode;
	}

	/**
	 * @param projectCode the projectCode to set
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

}
