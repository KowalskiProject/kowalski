package com.app.kowalski.da.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "kowalskiuserrole")
public class KowalskiUserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer roleId;

	private String role;

	@ManyToMany(mappedBy = "roles")
    private Set<KowalskiUser> users = new HashSet<>();

	public KowalskiUserRole() {}

	public KowalskiUserRole(String role) {
		this.role = role;
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
	 * @return the users
	 */
	public Set<KowalskiUser> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Set<KowalskiUser> users) {
		this.users = users;
	}

}
