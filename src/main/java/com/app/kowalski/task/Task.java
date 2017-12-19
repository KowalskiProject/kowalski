package com.app.kowalski.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.app.kowalski.activity.Activity;
import com.app.kowalski.user.KowalskiUser;

@Entity
@Table(name = "task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer taskId;

	private String name;
	private String description;
	private String status;
	private Date startDate;
	private Date endDate;

	@ManyToOne
    @JoinColumn(name="activity_activityId")
    private Activity activity;

	@ManyToOne
    @JoinColumn(name="kowalskiuser_kUserId")
	private KowalskiUser accountable;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Task() {}

	public Task convertToTask(TaskDTO taskDTO) {
		this.name = taskDTO.getName();
		this.description = taskDTO.getDescription();
		this.status = taskDTO.getStatus();
		try {
			this.startDate = sdf.parse(taskDTO.getStartDate());
			this.endDate = sdf.parse(taskDTO.getEndDate());
		} catch (ParseException e) {}

		return this;
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
	 * @return the activity
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	/**
	 * @return the accountable
	 */
	public KowalskiUser getAccountable() {
		return accountable;
	}

	/**
	 * @param accountable the accountable to set
	 */
	public void setAccountable(KowalskiUser accountable) {
		this.accountable = accountable;
	}

}
