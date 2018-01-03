package com.app.kowalski.da.repositories;

import java.util.List;

import com.app.kowalski.da.entities.KowalskiUser;
import com.app.kowalski.da.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

	List<Project> findByAccountable(KowalskiUser user);

}
