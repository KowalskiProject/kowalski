package com.app.kowalski.dto;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.app.kowalski.da.entities.KowalskiUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * Class used to expose kowalski user's parameters through the REST API
 * following the Data Transfer Object pattern.
 */
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class KowalskiUserDTO extends ResourceSupport implements Serializable {

	private Integer kUserId;

	@ApiModelProperty(required = true)
	private String name;

	@ApiModelProperty(required = true)
	private String username;

	@ApiModelProperty(required = true)
	private String email;

	private String creationDate;

	// users are created as active by design
	private boolean active = true;

	public KowalskiUserDTO() {}

	public KowalskiUserDTO(String name, String username, String email, boolean active) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.active = active;
	}

	public KowalskiUserDTO(KowalskiUser kowalskiUser) {
		this.kUserId = kowalskiUser.getkUserId();
		this.name = kowalskiUser.getName();
		this.username = kowalskiUser.getUsername();
		this.email = kowalskiUser.getEmail();
		this.active = kowalskiUser.isActive();
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
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

}
