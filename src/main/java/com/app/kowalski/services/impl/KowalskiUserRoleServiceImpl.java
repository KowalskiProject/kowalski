package com.app.kowalski.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.kowalski.da.repositories.KowalskiUserRoleRepository;
import com.app.kowalski.dto.KowalskiUserRoleDTO;
import com.app.kowalski.services.KowalskiUserRoleService;

@Service
public class KowalskiUserRoleServiceImpl implements KowalskiUserRoleService {

	private final KowalskiUserRoleRepository roleRepository;

	@Autowired
	public KowalskiUserRoleServiceImpl(KowalskiUserRoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public List<KowalskiUserRoleDTO> getKowalskiUserRoles() {
		return this.roleRepository.findAll().stream()
				.map(role -> new KowalskiUserRoleDTO(role))
				.collect(Collectors.toList());
	}

}
