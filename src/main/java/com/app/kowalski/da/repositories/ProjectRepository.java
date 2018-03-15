package com.app.kowalski.da.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.kowalski.da.entities.KowalskiUser;
import com.app.kowalski.da.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

	List<Project> findByAccountable(KowalskiUser user);
	List<Project> findByName(String name);
	List<Project> findByCode(String code);

	@Query("select project from Project project where ?1 member of project.members")
	List<Project> findWithMember(KowalskiUser user);
}
