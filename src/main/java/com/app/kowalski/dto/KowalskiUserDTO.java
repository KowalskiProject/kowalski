package com.app.kowalski.dto;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.app.kowalski.da.entities.KowalskiUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Class used to expose kowalski user's parameters through the REST API
 * following the Data Transfer Object pattern.
 */
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class KowalskiUserDTO extends ResourceSupport implements Serializable {

	private Integer kUserId;
	private String name;
	private String username;
	private String email;
	private String creationDate;
	private String role;

	public KowalskiUserDTO() {}

	public KowalskiUserDTO(String name, String username, String email, String role) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.role = role;
	}

	public KowalskiUserDTO(KowalskiUser kowalskiUser) {
		this.kUserId = kowalskiUser.getkUserId();
		this.name = kowalskiUser.getName();
		this.username = kowalskiUser.getUsername();
		this.email = kowalskiUser.getEmail();
		this.role = kowalskiUser.getRole().toString();
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
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

}
