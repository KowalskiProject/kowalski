package com.app.kowalski.timerecord;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.kowalski.task.Task;
import com.app.kowalski.user.KowalskiUser;

public interface TimeRecordRepository extends JpaRepository<TimeRecord, Integer> {

	List<TimeRecord> findByUser(KowalskiUser user);
	List<TimeRecord> findByUserAndReportedDayGreaterThanEqual(KowalskiUser user, LocalDate start);
	List<TimeRecord> findByUserAndReportedDayLessThanEqual(KowalskiUser user, LocalDate end);
	List<TimeRecord> findByUserAndReportedDayBetween(KowalskiUser user, LocalDate start, LocalDate end);

	List<TimeRecord> findByTask(Task task);
	List<TimeRecord> findByTaskAndReportedDayGreaterThanEqual(Task task, LocalDate start);
	List<TimeRecord> findByTaskAndReportedDayLessThanEqual(Task task, LocalDate end);
	List<TimeRecord> findByTaskAndReportedDayBetween(Task task, LocalDate start, LocalDate end);

}
