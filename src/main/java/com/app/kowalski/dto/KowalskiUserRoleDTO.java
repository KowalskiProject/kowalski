package com.app.kowalski.dto;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.app.kowalski.da.entities.KowalskiUserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Class used to expose kowalski user's parameters through the REST API
 * following the Data Transfer Object pattern.
 */
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class KowalskiUserRoleDTO extends ResourceSupport implements Serializable {

	private Integer roleId;
	private String value;

	public KowalskiUserRoleDTO() {}

	public KowalskiUserRoleDTO(KowalskiUserRole role) {
		this.roleId = role.getRoleId();
		this.value = role.getRole();
	}

	/**
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
