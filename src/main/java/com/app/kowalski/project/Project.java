package com.app.kowalski.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.app.kowalski.activity.Activity;

@Entity
@Table(name = "project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer projectId;

	private String name;
	private String code;
	private String description;
	private Date startDate;
	private Date endDate;

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Activity> activities = new ArrayList<Activity>();

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Project() {}

	/**
	 * Read all parameters from DTO and save them into project instance
	 *
	 * @param projectDTO information used to create or edit a project
	 * @return project instance
	 */
	public Project convertToProject(ProjectDTO projectDTO) {
		this.name = projectDTO.getName();
		this.code = projectDTO.getCode();
		this.description = projectDTO.getDescription();
		try {
			this.startDate = sdf.parse(projectDTO.getStartDate());
			this.endDate = sdf.parse(projectDTO.getEndDate());
		} catch (ParseException e) {}

		return this;
	}

	/**
	 *
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		activities.add(activity);
	}

	/**
	 *
	 * @return
	 */
	public List<Activity> getActivities() {
		return this.activities;
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
