package com.app.kowalski.timerecord;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.kowalski.task.Task;
import com.app.kowalski.user.KowalskiUser;

public interface TimeRecordRepository extends JpaRepository<TimeRecord, Integer> {

	List<TimeRecord> findByUser(KowalskiUser user);
	List<TimeRecord> findByTask(Task task);
}
