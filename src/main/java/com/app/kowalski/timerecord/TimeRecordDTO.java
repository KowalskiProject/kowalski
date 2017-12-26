package com.app.kowalski.timerecord;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Class used to expose time record parameters through the REST API
 * following the Data Transfer Object pattern.
 */
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class TimeRecordDTO extends ResourceSupport implements Serializable {

	private Integer trId;
	private Integer userId;
	private Integer taskId;
	private String createdDate;
	private String reportedTime;
	private String comment;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat sdfReportedTime = new SimpleDateFormat("yyyy-MM-dd");

	public TimeRecordDTO() {}

	public TimeRecordDTO(TimeRecord timeRecord) {
		this.setTrId(timeRecord.getTrId());
		this.userId = timeRecord.getUser().getkUserId();
		this.taskId = timeRecord.getTask().getTaskId();
		this.createdDate = sdf.format(timeRecord.getCreateDate());
		this.reportedTime = sdfReportedTime.format(timeRecord.getReportedTime());
		this.comment = timeRecord.getComment();
	}

	public TimeRecordDTO(Integer userId, Integer taskId, String reportedTime, String comment) {
		this.userId = userId;
		this.taskId = taskId;
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

}
