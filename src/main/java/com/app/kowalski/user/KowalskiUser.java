package com.app.kowalski.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.app.kowalski.activity.Activity;
import com.app.kowalski.project.Project;
import com.app.kowalski.timerecord.TimeRecord;

@Entity
@Table(name = "kowalskiuser")
public class KowalskiUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer kUserId;

	private String name;
	private String username;
	private String email;
	private String password;
	private Date creationDate;

	@ManyToMany(mappedBy = "members")
    private Set<Project> projects = new HashSet<>();

	@ManyToMany(mappedBy = "members")
    private Set<Activity> activities = new HashSet<>();

	@OneToMany
    private List<TimeRecord> timeRecords = new ArrayList<TimeRecord>();

	public KowalskiUser() {}

	public KowalskiUser(String name, String username, String email, String password) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.creationDate = new Date();
	}

	public KowalskiUser convertToKowalskiUser(KowalskiUserDTO kowalskiUserDTO) {
		this.name = kowalskiUserDTO.getName();
		this.username = kowalskiUserDTO.getUsername();
		this.email = kowalskiUserDTO.getEmail();
		this.password = kowalskiUserDTO.getPassword();
		this.creationDate = new Date();

		return this;
	}

	/**
	 * @return the kUserId
	 */
	public Integer getkUserId() {
		return kUserId;
	}

	/**
	 * @param kUserId the kUserId to set
	 */
	public void setkUserId(Integer kUserId) {
		this.kUserId = kUserId;
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 *
	 * @return
	 */
	public Set<Project> getProjects() {
		return this.projects;
	}

	public Set<Activity> getActivities() {
		return this.activities;
	}

	/**
	 * @return the timeRecords
	 */
	public List<TimeRecord> getTimeRecords() {
		return timeRecords;
	}

	/**
	 *
	 * @param timeRecord
	 */
	public void addTimeRecord(TimeRecord timeRecord) {
		this.timeRecords.add(timeRecord);
	}

	/**
	 *
	 * @param timeRecord
	 */
	public void removeTimeRecord(TimeRecord timeRecord) {
		this.timeRecords.remove(timeRecord);
	}
}
