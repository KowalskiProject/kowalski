/**
 *
 */
package com.app.kowalski.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import com.app.kowalski.da.entities.Project;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Class used to expose project's parameters through the REST API
 * following the Data Transfer Object pattern.
 */
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class ProjectDTO extends ResourceSupport implements Serializable {

	private Integer projectId;
	private String name;
	private String code;
	private String description;
	private String startDate;
	private String endDate;
	private Integer accountableId;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public ProjectDTO() {}

	public ProjectDTO(String name, String code, String description, String startDate, String endDate) {
		this.name = name;
		this.code = code;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public ProjectDTO(Project project) {
		this.projectId = project.getProjectId();
		this.name = project.getName();
		this.description = project.getDescription();
		this.startDate = sdf.format(project.getStartDate());
		this.endDate = sdf.format(project.getEndDate());
		if (project.getAccountable() != null)
			this.setAccountableId(project.getAccountable().getkUserId());
		else
			this.setAccountableId(null);
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return the accountableId
	 */
	public Integer getAccountableId() {
		return accountableId;
	}

	/**
	 * @param accountableId the accountableId to set
	 */
	public void setAccountableId(Integer accountableId) {
		this.accountableId = accountableId;
	}

}
