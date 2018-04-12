package com.app.kowalski.da.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.app.kowalski.dto.ProjectDTO;

@Entity
@Table(name = "project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer projectId;

	@NotNull
	private String name;

	@NotNull
	private String code;

	@Lob
	@Column
	private String description;

	private Date startDate;
	private Date endDate;

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Activity> activities = new ArrayList<Activity>();

	@ManyToOne
    @JoinColumn(name="kowalskiuser_kUserId")
	private KowalskiUser accountable;

	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
    	name="Project_KowalskiUser",
    	joinColumns={@JoinColumn(name = "project_projectId")},
    	inverseJoinColumns={@JoinColumn(name = "kowalskiuser_kUserId")}
    )
    Set<KowalskiUser> members = new HashSet<>();

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Project() {}

	public Project(String name, String code, String description, Date startDate, Date endDate) {
		this.name = name;
		this.code = code;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Read all parameters from DTO and save them into project instance
	 *
	 * @param projectDTO information used to create or edit a project
	 */
	public Project(ProjectDTO projectDTO) {
		this.name = projectDTO.getName();
		this.code = projectDTO.getCode();
		this.description = projectDTO.getDescription();
		try {
			this.startDate = sdf.parse(projectDTO.getStartDate());
			this.endDate = sdf.parse(projectDTO.getEndDate());
		} catch (ParseException | NullPointerException e) {}
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

	/**
	 *
	 * @return
	 */
	public Set<KowalskiUser> getMembers() {
		return this.members;
	}

	/**
	 *
	 * @param kowalskiUser
	 */
	public void addMember(KowalskiUser kowalskiUser) {
		this.members.add(kowalskiUser);
	}

	/**
	 *
	 * @param kowalskiUser
	 */
	public void removeMember(KowalskiUser kowalskiUser) {
		this.members.remove(kowalskiUser);
	}

	/**
	 *
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		activities.add(activity);
		activity.setProject(this);
	}

	/**
	 *
	 * @param activity
	 */
	public void removeActivity(Activity activity) {
		activities.remove(activity);
		activity.setProject(null);
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
