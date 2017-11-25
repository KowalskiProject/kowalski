package com.app.kowalski.user;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		} catch (Exception e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}
		return true;
	}

}
