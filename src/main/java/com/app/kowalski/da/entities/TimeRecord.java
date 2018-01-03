package com.app.kowalski.da.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.app.kowalski.da.entities.Task;
import com.app.kowalski.da.entities.KowalskiUser;

@Entity
@Table(name = "timerecord")
public class TimeRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer trId;

	@ManyToOne
    @JoinColumn(name="kowalskiuser_kUserId")
    private KowalskiUser user;

	@ManyToOne
    @JoinColumn(name="task_taskId")
    private Task task;

	private Date createDate;
	private LocalDate reportedDay;
	private LocalTime reportedTime;
	private String comment;

	private TimeRecordState state;

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	private List<TimeRecordReview> reviews = new ArrayList<TimeRecordReview>();

	public TimeRecord() {}

	public TimeRecord(KowalskiUser user, Task task, LocalDate reportedDay, LocalTime reportedTime, String comment) {
		this.user = user;
		this.task = task;
		this.createDate = new Date();
		this.reportedDay = reportedDay;
		this.reportedTime = reportedTime;
		this.comment = comment;
		this.state = TimeRecordState.NEW;
	}

	public TimeRecord editTimeRecord(KowalskiUser user, Task task, LocalDate reportedDay, LocalTime reportedTime,
			String comment) {
		this.user = user;
		this.task = task;
		this.createDate = new Date();
		this.reportedDay = reportedDay;
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
	 * @return the reportedDay
	 */
	public LocalDate getReportedDay() {
		return reportedDay;
	}

	/**
	 * @param reportedDay the reportedDay to set
	 */
	public void setReportedDay(LocalDate reportedDay) {
		this.reportedDay = reportedDay;
	}

	/**
	 * @return the reportedTime
	 */
	public LocalTime getReportedTime() {
		return reportedTime;
	}

	/**
	 * @param reportedTime the reportedTime to set
	 */
	public void setReportedTime(LocalTime reportedTime) {
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
	public TimeRecordState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(TimeRecordState state) {
		this.state = state;
	}

	/**
	 * @return the reviews
	 */
	public List<TimeRecordReview> getReviews() {
		return reviews;
	}

	/**
	 *
	 * @param review
	 */
	public void addReview(TimeRecordReview review) {
		this.reviews.add(review);
	}
}
