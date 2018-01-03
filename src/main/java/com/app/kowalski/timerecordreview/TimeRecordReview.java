package com.app.kowalski.timerecordreview;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.app.kowalski.timerecord.TimeRecord;
import com.app.kowalski.timerecord.TimeRecordState;
import com.app.kowalski.user.KowalskiUser;

@Entity
@Table(name = "timerecordreview")
public class TimeRecordReview {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer trReviewId;

	private TimeRecordState previousState;
	private TimeRecordState nextState;
	private String comment;
	private Date createdDate;

	@ManyToOne
    @JoinColumn(name="timerecord_trId")
    private TimeRecord timeRecord;

	@ManyToOne
	@JoinColumn(name="kowalskiuser_kUserId")
	private KowalskiUser reviewer;

	public TimeRecordReview() {}

	public TimeRecordReview(TimeRecordReviewDTO trReviewDTO, KowalskiUser reviewer) {
		this.previousState =  TimeRecordState.valueOf(trReviewDTO.getPreviousState().toUpperCase());
		this.nextState = TimeRecordState.valueOf(trReviewDTO.getNextState().toUpperCase());
		this.comment = trReviewDTO.getComment();
		this.createdDate = new Date();
		this.reviewer = reviewer;
	}

	public TimeRecordReview(TimeRecordState nextState, String comment, KowalskiUser reviewer) {
		//this.previousState = timeRecord.getState();
		this.nextState = nextState;
		this.comment = comment;
		this.createdDate = new Date();
		this.reviewer = reviewer;
	}

	/**
	 * @return the trReviewId
	 */
	public Integer getTrReviewId() {
		return trReviewId;
	}

	/**
	 * @param trReviewId the trReviewId to set
	 */
	public void setTrReviewId(Integer trReviewId) {
		this.trReviewId = trReviewId;
	}

	/**
	 * @return the timeRecord
	 */
	public TimeRecord getTimeRecord() {
		return timeRecord;
	}

	/**
	 * @param timeRecord the timeRecord to set
	 */
	public void setTimeRecord(TimeRecord timeRecord) {
		this.timeRecord = timeRecord;
	}

	/**
	 * @return the previousState
	 */
	public TimeRecordState getPreviousState() {
		return previousState;
	}

	/**
	 * @param previousState the previousState to set
	 */
	public void setPreviousState(TimeRecordState previousState) {
		this.previousState = previousState;
	}

	/**
	 * @return the nextState
	 */
	public TimeRecordState getNextState() {
		return nextState;
	}

	/**
	 * @param nextState the nextState to set
	 */
	public void setNextState(TimeRecordState nextState) {
		this.nextState = nextState;
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
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the reviewer
	 */
	public KowalskiUser getReviewer() {
		return reviewer;
	}

	/**
	 * @param reviewer the reviewer to set
	 */
	public void setReviewer(KowalskiUser reviewer) {
		this.reviewer = reviewer;
	}
}
