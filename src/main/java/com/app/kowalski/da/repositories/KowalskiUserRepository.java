package com.app.kowalski.da.repositories;

import com.app.kowalski.da.entities.KowalskiUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KowalskiUserRepository extends JpaRepository<KowalskiUser, Integer> {

}
