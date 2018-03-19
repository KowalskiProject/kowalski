package com.app.kowalski.services;

import java.util.List;

import com.app.kowalski.dto.KowalskiUserRoleDTO;

/**
 * Expose allowed methods related to Kowalski user roles
 */
public interface KowalskiUserRoleService {

	/**
	 * Returns all kowalski user roles.
	 * @return List of roles.
	 */
	public List<KowalskiUserRoleDTO> getKowalskiUserRoles();
}
