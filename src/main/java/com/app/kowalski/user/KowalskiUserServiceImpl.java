package com.app.kowalski.user;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.kowalski.activity.ActivityDTO;
import com.app.kowalski.project.ProjectDTO;
import com.app.kowalski.user.exception.KowalskiUserNotFoundException;

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
	public KowalskiUserDTO addKowaslkiUser(KowalskiUserDTO kowalskiUserDTO) {
		// check business rules here
		KowalskiUser kowalskiUser = new KowalskiUser().convertToKowalskiUser(kowalskiUserDTO);
		kowalskiUser = this.repository.save(kowalskiUser);
		return new KowalskiUserDTO(kowalskiUser);
	}

	@Override
	public KowalskiUserDTO editKowaslkiUser(KowalskiUserDTO kowalskiUserDTO) throws KowalskiUserNotFoundException {
		// check business rules here
		try {
			KowalskiUser kowalskiUser = this.repository.getOne(kowalskiUserDTO.getkUserId());
			kowalskiUser = kowalskiUser.convertToKowalskiUser(kowalskiUserDTO);
			kowalskiUser = this.repository.save(kowalskiUser);
			return new KowalskiUserDTO(kowalskiUser);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public boolean deleteKowalskiUser(int kowalskiUserId) throws KowalskiUserNotFoundException {
		try {
			this.repository.delete(kowalskiUserId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}
		return true;
	}

	@Override
	public List<ProjectDTO> getAccountableProjects(Integer kUserId) throws KowalskiUserNotFoundException {
		KowalskiUser kowalskiUser = null;

		try {
			kowalskiUser = this.repository.getOne(kUserId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		return kowalskiUser.getAccountableProjects().stream()
				.map(project -> new ProjectDTO(project))
				.collect(Collectors.toList());
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

	@Override
	public List<ActivityDTO> getAccountableActivities(Integer kUserId) throws KowalskiUserNotFoundException {
		KowalskiUser kowalskiUser = null;

		try {
			kowalskiUser = this.repository.getOne(kUserId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		return kowalskiUser.getAccountableActivities().stream()
				.map(activity -> new ActivityDTO(activity))
				.collect(Collectors.toList());
	}

}
