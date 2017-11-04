package com.app.kowalski.project.dto;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.io.Serializable;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.app.kowalski.project.Project;
import com.app.kowalski.project.ProjectController;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Class used to expose project's parameters through the REST API
 * following the Data Transfer Object pattern.
 */
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class ProjectSummaryDTO extends ResourceSupport implements Serializable {

	private Integer projectId;
	private String name;
	private String code;
	private String description;

	public ProjectSummaryDTO() {}

	public ProjectSummaryDTO(Project project) {
		this.projectId = project.getProjectId();
		this.name = project.getName();
		this.description = project.getDescription();
		Link selfLink = linkTo(ProjectController.class).slash(this.getProjectId()).withSelfRel();
		this.add(selfLink);
	}

	/**
	 * @return the id
	 */
	public Integer getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the id to set
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
}
