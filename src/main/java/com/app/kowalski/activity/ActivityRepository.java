package com.app.kowalski.activity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.kowalski.user.KowalskiUser;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	List<Activity> findByAccountable(KowalskiUser user);
}
