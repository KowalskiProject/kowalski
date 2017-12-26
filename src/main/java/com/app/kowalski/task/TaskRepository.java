package com.app.kowalski.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.kowalski.user.KowalskiUser;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	List<Task> findByAccountable(KowalskiUser user);
}
