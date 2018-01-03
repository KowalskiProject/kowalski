package com.app.kowalski.timerecordreview;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Class used to expose time record review parameters through the REST API
 * following the Data Transfer Object pattern.
 */
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class TimeRecordReviewDTO extends ResourceSupport implements Serializable {

	private Integer trReviewId;
	private Integer trId;
	private String previousState;
	private String nextState;
	private String comment;
	private Integer reviewerId;

	public TimeRecordReviewDTO() {}

	public TimeRecordReviewDTO(TimeRecordReview trReview) {
		this.trReviewId = trReview.getTrReviewId();
		this.trId = trReview.getTimeRecord().getTrId();
		this.previousState = trReview.getPreviousState().toString();
		this.nextState = trReview.getNextState().toString();
		this.comment = trReview.getComment();
		this.reviewerId = trReview.getReviewer().getkUserId();
	}

	public TimeRecordReviewDTO(String nextState, String comment, Integer reviewerId) {
		this.nextState = nextState;
		this.comment = comment;
		this.reviewerId = reviewerId;
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
	 * @return the previousState
	 */
	public String getPreviousState() {
		return previousState;
	}

	/**
	 * @param previousState the previousState to set
	 */
	public void setPreviousState(String previousState) {
		this.previousState = previousState;
	}

	/**
	 * @return the nextState
	 */
	public String getNextState() {
		return nextState;
	}

	/**
	 * @param nextState the nextState to set
	 */
	public void setNextState(String nextState) {
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
	 * @return the reviewerId
	 */
	public Integer getReviewerId() {
		return reviewerId;
	}

	/**
	 * @param reviewerId the reviewerId to set
	 */
	public void setReviewerId(Integer reviewerId) {
		this.reviewerId = reviewerId;
	}
}
