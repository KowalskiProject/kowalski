package com.app.kowalski.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.kowalski.da.entities.KowalskiUser;
import com.app.kowalski.da.entities.KowalskiUserRole;
import com.app.kowalski.da.repositories.KowalskiUserRepository;
import com.app.kowalski.da.repositories.KowalskiUserRoleRepository;
import com.app.kowalski.dto.KowalskiUserDTO;
import com.app.kowalski.dto.KowalskiUserRoleDTO;
import com.app.kowalski.dto.ProjectDTO;
import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.exception.KowalskiUserServiceException;
import com.app.kowalski.services.KowalskiUserService;

@Service
public class KowalskiUserServiceImpl implements KowalskiUserService {

	private final KowalskiUserRepository userRepository;
	private final KowalskiUserRoleRepository roleRepository;

	@Autowired
	public KowalskiUserServiceImpl(KowalskiUserRepository kowalskiUserRepository,
			KowalskiUserRoleRepository roleRepository) {
		this.userRepository = kowalskiUserRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public List<KowalskiUserDTO> getKowalskiUsers() {
		List<KowalskiUser> kowalskiUsers = this.userRepository.findAll();
		return kowalskiUsers.stream()
				.map(kowalskiUser -> new KowalskiUserDTO(kowalskiUser))
				.collect(Collectors.toList());
	}

	@Override
	public KowalskiUserDTO getKowalskiUser(int kowalskiUserId) throws KowalskiUserNotFoundException {
		try {
			return new KowalskiUserDTO(this.userRepository.getOne(kowalskiUserId));
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}
	}

	@Override
	@Transactional
	public KowalskiUserDTO addKowaslkiUser(KowalskiUserDTO kowalskiUserDTO) throws KowalskiUserServiceException {
		if (kowalskiUserDTO.getRoles().isEmpty()) {
			System.out.println("Any role was informed to the user. Aborting...");
			throw new KowalskiUserServiceException("No roles informed to the user");
		}

		KowalskiUser kowalskiUser = new KowalskiUser(kowalskiUserDTO);
		Set<KowalskiUserRole> roles = new HashSet<KowalskiUserRole>();

		for (KowalskiUserRoleDTO roleDTO : kowalskiUserDTO.getRoles()) {
			if (this.roleRepository.exists(roleDTO.getRoleId()))
				roles.add(this.roleRepository.getOne(roleDTO.getRoleId()));
		}
		if (roles.size() > 0)
			kowalskiUser.setRoles(roles);

		kowalskiUser = this.userRepository.save(kowalskiUser);
		return new KowalskiUserDTO(kowalskiUser);
	}

	@Override
	@Transactional
	public KowalskiUserDTO editKowaslkiUser(KowalskiUserDTO kowalskiUserDTO) throws KowalskiUserNotFoundException {
		// check business rules here
		try {
			KowalskiUser kowalskiUser = this.userRepository.getOne(kowalskiUserDTO.getkUserId());
			kowalskiUser = new KowalskiUser(kowalskiUserDTO);
			kowalskiUser.setkUserId(kowalskiUserDTO.getkUserId());
			kowalskiUser = this.userRepository.save(kowalskiUser);
			return new KowalskiUserDTO(kowalskiUser);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}
	}

	@Override
	@Transactional
	public boolean deleteKowalskiUser(int kowalskiUserId) throws KowalskiUserNotFoundException {
		try {
			this.userRepository.delete(kowalskiUserId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}
		return true;
	}

	@Override
	public Set<ProjectDTO> getProjects(Integer kUserId) throws KowalskiUserNotFoundException {
		KowalskiUser kowalskiUser = null;

		try {
			kowalskiUser = this.userRepository.getOne(kUserId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		return kowalskiUser.getProjects().stream()
				.map(project -> new ProjectDTO(project))
				.collect(Collectors.toSet());
	}

}
