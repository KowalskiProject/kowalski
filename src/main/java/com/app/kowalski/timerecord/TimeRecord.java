package com.app.kowalski.timerecord;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.app.kowalski.task.Task;
import com.app.kowalski.user.KowalskiUser;

@Entity
@Table(name = "timerecord")
public class TimeRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer trId;

	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="kowalskiuser_kUserId")
    private KowalskiUser user;

	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="task_taskId")
    private Task task;

	private Date createDate;
	private Date reportedTime;
	private String comment;

	public TimeRecord() {}

	public TimeRecord(KowalskiUser user, Task task, Date reportedTime, String comment) {
		this.user = user;
		this.task = task;
		this.createDate = new Date();
		this.reportedTime = reportedTime;
		this.comment = comment;
	}

	public TimeRecord editTimeRecord(KowalskiUser user, Task task, Date reportedTime, String comment) {
		this.user = user;
		this.task = task;
		this.createDate = new Date();
		this.reportedTime = reportedTime;
		this.comment = comment;

		return this;
	}

	/**
	 * @return the taskId
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
	 * @return the user
	 */
	public KowalskiUser getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(KowalskiUser user) {
		this.user = user;
	}

	/**
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * @param task the task to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the reportedTime
	 */
	public Date getReportedTime() {
		return reportedTime;
	}

	/**
	 * @param reportedTime the reportedTime to set
	 */
	public void setReportedTime(Date reportedTime) {
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
