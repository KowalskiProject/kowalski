package com.app.kowalski.da.repositories;

import java.time.LocalDate;
import java.util.List;

import com.app.kowalski.da.entities.KowalskiUser;
import com.app.kowalski.da.entities.Task;
import com.app.kowalski.da.entities.TimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

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
