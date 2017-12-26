package com.app.kowalski.project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.kowalski.user.KowalskiUser;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

	List<Project> findByAccountable(KowalskiUser user);

}
