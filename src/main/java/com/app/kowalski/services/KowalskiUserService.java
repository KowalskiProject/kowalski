package com.app.kowalski.services;

import java.util.List;
import java.util.Set;

import com.app.kowalski.dto.KowalskiUserDTO;
import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.dto.ProjectDTO;

/**
 * Expose allowed methods related to Kowalski users.
 *
 * It receives KowalskiDTO objects in order to perform the
 * operations and does not expose the domain class.
 */
public interface KowalskiUserService {

	/**
	 * Returns all Kowalski registered users.
	 * @return List of kowalski users.
	 */
	public List<KowalskiUserDTO> getKowalskiUsers();

	/**
	 * Return kowalski user data according given id.
	 * @param kowalskiUserId kowalski user reference
	 * @return kowalski user data
	 *
	 * @throws KowalskiUserNotFoundException No KowalskiUser instance found in the system
	 */
	public KowalskiUserDTO getKowalskiUser(int kowalskiUserId) throws KowalskiUserNotFoundException;

	/**
	 * Creates a new kowalski user.
	 * @param kowalskiUserDTO data to create the new kowalski user instance
	 * @return created Kowalski instance
	 */
	public KowalskiUserDTO addKowaslkiUser(KowalskiUserDTO kowalskiUserDTO);

	/**
	 * Edits the kowalski user information according given id.
	 * @param kowalskiUserDTO user to save in the kowalski user
	 * @return kowalski user info after edition
	 *
	 * @throws KowalskiUserNotFoundException No KowalskiUser instance found in the system
	 */
	public KowalskiUserDTO editKowaslkiUser(KowalskiUserDTO kowalskiUserDTO) throws KowalskiUserNotFoundException;

	/**
	 * Removes a kowalski user from the system.
	 * @param kowalskiUserId kowalski user reference
	 * @return true if the kowalski user was removed successfully, false otherwise
	 *
	 * @throws KowalskiUserNotFoundException No KowalskiUser instance found in the system
	 */
	public boolean deleteKowalskiUser(int kowalskiUserId) throws KowalskiUserNotFoundException;

	/**
	 * Returns all projects where given user is member
	 * @param kUserId user reference
	 * @return List of projects
	 *
	 * @throws KowalskiUserNotFoundException No KowalskiUser instance found in the system
	 */
	public Set<ProjectDTO> getProjects(Integer kUserId) throws KowalskiUserNotFoundException;
}
