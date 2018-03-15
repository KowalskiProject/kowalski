package com.app.kowalski.da.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.kowalski.da.entities.Activity;
import com.app.kowalski.da.entities.KowalskiUser;
import com.app.kowalski.da.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	List<Task> findByAccountable(KowalskiUser user);
	List<Task> findByActivityAndAccountable(Activity activity, KowalskiUser user);
}
