package com.app.kowalski.da.repositories;

import java.util.List;

import com.app.kowalski.da.entities.Activity;
import com.app.kowalski.da.entities.KowalskiUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	List<Activity> findByAccountable(KowalskiUser user);
}
