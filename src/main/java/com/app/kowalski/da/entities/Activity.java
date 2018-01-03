package com.app.kowalski.da.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.app.kowalski.dto.ActivityDTO;

@Entity
@Table(name = "activity")
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer activityId;

	private String name;
	private String description;
	private String status;
	private Date startDate;
	private Date endDate;

	@ManyToOne
    @JoinColumn(name="project_projectId")
    private Project project;

	@ManyToOne
    @JoinColumn(name="kowalskiuser_kUserId")
	private KowalskiUser accountable;

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Task> tasks = new ArrayList<Task>();

	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
    	name="Activity_KowalskiUser",
    	joinColumns={@JoinColumn(name = "activity_activityId")},
    	inverseJoinColumns={@JoinColumn(name = "kowalskiuser_kUserId")}
    )
    Set<KowalskiUser> members = new HashSet<>();

	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
    	name="Activity_ActivityGroup",
    	joinColumns={@JoinColumn(name = "activity_activityId")},
    	inverseJoinColumns={@JoinColumn(name = "activitygroup_activityGroupId")}
    )
    Set<ActivityGroup> groups = new HashSet<>();

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Activity() {}

	public Activity(String name, String description, String status, Date startDate, Date endDate) {
		this.name = name;
		this.description = description;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Activity convertToActivity(ActivityDTO activityDTO) {
		this.name = activityDTO.getName();
		this.description = activityDTO.getDescription();
		this.status = activityDTO.getStatus();
		try {
			this.startDate = sdf.parse(activityDTO.getStartDate());
			this.endDate = sdf.parse(activityDTO.getEndDate());
		} catch (ParseException e) {}


		return this;
	}

	/**
	 * @return the id
	 */
	public Integer getActivityId() {
		return activityId;
	}

	/**
	 * @param id the id to set
	 */
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
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
	 * @param task
	 */
	public void addTask(Task task) {
		this.tasks.add(task);
		task.setActivity(this);
	}

	/**
	 *
	 * @param task
	 */
	public void removeTask(Task task) {
		this.tasks.remove(task);
		task.setActivity(null);
	}

	/**
	 *
	 * @return
	 */
	public List<Task> getTasks() {
		return this.tasks;
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
	 * @return
	 */
	public Set<ActivityGroup> getGroups() {
		return this.groups;
	}

	/**
	 *
	 * @param activityGroup
	 */
	public void addActivityGroup(ActivityGroup activityGroup) {
		this.groups.add(activityGroup);
	}

	/**
	 *
	 * @param activityGroup
	 */
	public void removeActivityGroup(ActivityGroup activityGroup) {
		this.groups.remove(activityGroup);
	}

}
