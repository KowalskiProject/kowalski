package com.app.kowalski.da.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.app.kowalski.dto.KowalskiUserDTO;

@Entity
@Table(name = "kowalskiuser")
public class KowalskiUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer kUserId;

	private String name;
	private String username;
	private String email;
	private Date creationDate;

	@Enumerated(EnumType.ORDINAL)
	private KowalskiUserRole role;

	@ManyToMany(mappedBy = "members")
    private Set<Project> projects = new HashSet<>();

	@ManyToMany(mappedBy = "members")
    private Set<Activity> activities = new HashSet<>();

	public KowalskiUser() {}

	public KowalskiUser(KowalskiUserDTO kowalskiUserDTO) {
		this.name = kowalskiUserDTO.getName();
		this.username = kowalskiUserDTO.getUsername();
		this.email = kowalskiUserDTO.getEmail();
		this.role = KowalskiUserRole.valueOf(kowalskiUserDTO.getRole().toUpperCase());
		this.creationDate = new Date();
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
	 * @return the role
	 */
	public KowalskiUserRole getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(KowalskiUserRole role) {
		this.role = role;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	/**
	 * @param activities the activities to set
	 */
	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}

}
