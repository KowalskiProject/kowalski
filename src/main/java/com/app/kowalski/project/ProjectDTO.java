/**
 *
 */
package com.app.kowalski.project;

import java.util.Date;

/**
 *
 */
@SuppressWarnings("serial")
public class ProjectDTO extends ProjectSummaryDTO {

	private String target;
	private String motivation;
	private Date startDate;
	private Date endDate;

	public ProjectDTO() {}

	public ProjectDTO(Project project) {
		super(project);
		this.target = project.getTarget();
		this.motivation = project.getMotivation();
		this.startDate = project.getStartDate();
		this.endDate = project.getEndDate();
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the motivation
	 */
	public String getMotivation() {
		return motivation;
	}

	/**
	 * @param motivation the motivation to set
	 */
	public void setMotivation(String motivation) {
		this.motivation = motivation;
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

}
