package com.app.kowalski.da.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.kowalski.da.entities.KowalskiUser;

public interface KowalskiUserRepository extends JpaRepository<KowalskiUser, Integer> {

	KowalskiUser findByUsername(String username);
}
