package com.app.kowalski.da.repositories;

import java.util.List;

import com.app.kowalski.da.entities.KowalskiUser;
import com.app.kowalski.da.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	List<Task> findByAccountable(KowalskiUser user);
}
