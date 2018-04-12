package com.app.kowalski.services.impl;

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
import com.app.kowalski.dto.KowalskiUserDTO;
import com.app.kowalski.dto.ProjectDTO;
import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.exception.KowalskiUserServiceException;
import com.app.kowalski.services.KowalskiUserService;

@Service
public class KowalskiUserServiceImpl implements KowalskiUserService {

	private final KowalskiUserRepository repository;

	@Autowired
	public KowalskiUserServiceImpl(KowalskiUserRepository kowalskiUserRepository) {
		this.repository = kowalskiUserRepository;
	}

	@Override
	public List<KowalskiUserDTO> getKowalskiUsers() {
		List<KowalskiUser> kowalskiUsers = this.repository.findAll();
		return kowalskiUsers.stream()
				.map(kowalskiUser -> new KowalskiUserDTO(kowalskiUser))
				.collect(Collectors.toList());
	}

	@Override
	public KowalskiUserDTO getKowalskiUser(int kowalskiUserId) throws KowalskiUserNotFoundException {
		try {
			return new KowalskiUserDTO(this.repository.getOne(kowalskiUserId));
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public KowalskiUserDTO getKowalskiUserByUsername(String username) throws KowalskiUserNotFoundException {
		try {
			return new KowalskiUserDTO(this.repository.findByUsername(username));
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}
	}

	@Override
	@Transactional
	public KowalskiUserDTO addKowaslkiUser(KowalskiUserDTO kowalskiUserDTO) throws KowalskiUserServiceException {
		if (kowalskiUserDTO.getRole() != null) {
			try {
				KowalskiUserRole.valueOf(kowalskiUserDTO.getRole().toUpperCase());
			} catch(IllegalArgumentException e) {
				System.out.println("Invalid role for given user. Aborting...");
				throw new KowalskiUserServiceException("Invalid role");
			}
		}
		KowalskiUser kowalskiUser = new KowalskiUser(kowalskiUserDTO);
		kowalskiUser = this.repository.save(kowalskiUser);
		return new KowalskiUserDTO(kowalskiUser);
	}

	@Override
	@Transactional
	public KowalskiUserDTO editKowaslkiUser(KowalskiUserDTO kowalskiUserDTO) throws KowalskiUserNotFoundException {
		// check business rules here
		try {
			KowalskiUser kowalskiUser = this.repository.getOne(kowalskiUserDTO.getkUserId());
			kowalskiUser = new KowalskiUser(kowalskiUserDTO);
			kowalskiUser.setkUserId(kowalskiUserDTO.getkUserId());
			kowalskiUser = this.repository.save(kowalskiUser);
			return new KowalskiUserDTO(kowalskiUser);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}
	}

	@Override
	@Transactional
	public boolean deleteKowalskiUser(int kowalskiUserId) throws KowalskiUserNotFoundException {
		try {
			this.repository.delete(kowalskiUserId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}
		return true;
	}

	@Override
	public Set<ProjectDTO> getProjects(Integer kUserId) throws KowalskiUserNotFoundException {
		KowalskiUser kowalskiUser = null;

		try {
			kowalskiUser = this.repository.getOne(kUserId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		return kowalskiUser.getProjects().stream()
				.map(project -> new ProjectDTO(project))
				.collect(Collectors.toSet());
	}

}
