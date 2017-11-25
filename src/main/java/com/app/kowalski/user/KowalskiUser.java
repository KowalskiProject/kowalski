package com.app.kowalski.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.app.kowalski.project.Project;

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

	@OneToMany
    private List<Project> accountableProjects = new ArrayList<Project>();

	public KowalskiUser() {}

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
	 * @param project
	 */
	public void addAccountableProject(Project project) {
		this.accountableProjects.add(project);
	}

	/**
	 *
	 * @param project
	 */
	public void removeAccountableProject(Project project) {
		this.accountableProjects.remove(project);
	}
}
