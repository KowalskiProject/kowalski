package com.app.kowalski.services.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.app.kowalski.da.entities.TimeRecord;
import com.app.kowalski.da.entities.TimeRecordReview;
import com.app.kowalski.da.repositories.TimeRecordRepository;
import com.app.kowalski.dto.TimeRecordDTO;
import com.app.kowalski.dto.TimeRecordReviewDTO;
import com.app.kowalski.services.TimeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.kowalski.exception.InvalidTimeRecordException;
import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.exception.TaskNotFoundException;
import com.app.kowalski.exception.TimeRecordNotFoundException;
import com.app.kowalski.da.entities.Task;
import com.app.kowalski.da.repositories.TaskRepository;
import com.app.kowalski.da.entities.KowalskiUser;
import com.app.kowalski.da.repositories.KowalskiUserRepository;

@Service
public class TimeRecordServiceImpl implements TimeRecordService {

	private final TimeRecordRepository trRepository;
	private final KowalskiUserRepository userRepository;
	private final TaskRepository taskRepository;

	@Autowired
	public TimeRecordServiceImpl(TimeRecordRepository trRepository, KowalskiUserRepository userRepository,
			TaskRepository taskRepository) {
		this.trRepository = trRepository;
		this.userRepository = userRepository;
		this.taskRepository = taskRepository;
	}

	@Override
	@Transactional
	public TimeRecordDTO addTimeRecord(TimeRecordDTO timeRecordDTO)
			throws KowalskiUserNotFoundException, TaskNotFoundException, InvalidTimeRecordException {
		LocalDate reportedDay = null;
		LocalTime reportedTime = null;
		KowalskiUser user = null;
		Task task = null;

		try {
			reportedDay = LocalDate.parse(timeRecordDTO.getReportedDay());
		} catch (DateTimeParseException e) {
			throw new InvalidTimeRecordException(e.getMessage(), e.getCause());
		}

		try {
			reportedTime = LocalTime.parse(timeRecordDTO.getReportedTime());
		} catch (DateTimeParseException e) {
			throw new InvalidTimeRecordException(e.getMessage(), e.getCause());
		}

		try {
			user = this.userRepository.getOne(timeRecordDTO.getUserId());
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		try {
			task = this.taskRepository.getOne(timeRecordDTO.getTaskId());
		} catch (EntityNotFoundException e) {
			throw new TaskNotFoundException(e.getMessage(), e.getCause());
		}

		TimeRecord timeRecord = new TimeRecord(user, task, reportedDay, reportedTime, timeRecordDTO.getComment());
		timeRecord = this.trRepository.saveAndFlush(timeRecord);

		return new TimeRecordDTO(timeRecord);
	}

	@Override
	public TimeRecordDTO getTimeRecordbyId(Integer trId) throws TimeRecordNotFoundException {
		TimeRecord timeRecord = null;

		try {
			timeRecord = this.trRepository.getOne(trId);
		} catch (EntityNotFoundException e) {
			throw new TimeRecordNotFoundException(e.getMessage(), e.getCause());
		}

		return new TimeRecordDTO(timeRecord);
	}

	@Override
	@Transactional
	public TimeRecordDTO editTimeRecord(Integer trId, TimeRecordDTO timeRecordDTO)
			throws KowalskiUserNotFoundException, TaskNotFoundException,
			InvalidTimeRecordException, TimeRecordNotFoundException {
		LocalDate reportedDay = null;
		LocalTime reportedTime = null;
		TimeRecord timeRecord = null;
		KowalskiUser user = null;
		Task task = null;

		try {
			timeRecord = this.trRepository.getOne(trId);
		} catch (EntityNotFoundException e) {
			throw new TimeRecordNotFoundException(e.getMessage(), e.getCause());
		}

		try {
			user = this.userRepository.getOne(timeRecordDTO.getUserId());
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		try {
			task = this.taskRepository.getOne(timeRecordDTO.getTaskId());
		} catch (EntityNotFoundException e) {
			throw new TaskNotFoundException(e.getMessage(), e.getCause());
		}

		try {
			reportedDay = LocalDate.parse(timeRecordDTO.getReportedDay());
		} catch (DateTimeParseException e) {
			throw new InvalidTimeRecordException(e.getMessage(), e.getCause());
		}

		try {
			reportedTime = LocalTime.parse(timeRecordDTO.getReportedTime());
		} catch (DateTimeParseException e) {
			throw new InvalidTimeRecordException(e.getMessage(), e.getCause());
		}

		timeRecord = timeRecord.editTimeRecord(user, task, reportedDay, reportedTime, timeRecordDTO.getComment());
		timeRecord = this.trRepository.save(timeRecord);

		return new TimeRecordDTO(timeRecord);
	}

	@Override
	@Transactional
	public boolean deleteTimeRecord(Integer trId) throws TimeRecordNotFoundException {
		try {
			this.trRepository.delete(trId);
		} catch (EntityNotFoundException e) {
			throw new TimeRecordNotFoundException(e.getMessage(), e.getCause());
		}

		return true;
	}

	@Override
	public List<TimeRecordDTO> getAllRecordsForUser(Integer userId, String startDate, String endDate)
			throws KowalskiUserNotFoundException, InvalidTimeRecordException {
		KowalskiUser user = null;
		LocalDate start = null;
		LocalDate end = null;
		List<TimeRecord> results = null;

		if (startDate != null) {
			try {
				start = LocalDate.parse(startDate);
			} catch (DateTimeParseException e) {
				throw new InvalidTimeRecordException(e.getMessage(), e.getCause());
			}
		}

		if (endDate != null) {
			try {
				end = LocalDate.parse(endDate);
			} catch (DateTimeParseException e) {
				throw new InvalidTimeRecordException(e.getMessage(), e.getCause());
			}
		}

		try {
			user = this.userRepository.getOne(userId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		if (start == null && end == null)
			results = this.trRepository.findByUser(user);
		else if (start != null && end == null)
			results = this.trRepository.findByUserAndReportedDayGreaterThanEqual(user, start);
		else if (start == null && end != null)
			results = this.trRepository.findByUserAndReportedDayLessThanEqual(user, end);
		else if (start != null && end != null)
			results = this.trRepository.findByUserAndReportedDayBetween(user, start, end);

		return results.stream()
				.map(timeRecord -> new TimeRecordDTO(timeRecord))
				.collect(Collectors.toList());
	}

	@Override
	public List<TimeRecordDTO> getAllRecordsForTask(Integer taskId, String startDate, String endDate)
			throws TaskNotFoundException, InvalidTimeRecordException {
		Task task = null;
		LocalDate start = null;
		LocalDate end = null;
		List<TimeRecord> results = null;

		if (startDate != null) {
			try {
				start = LocalDate.parse(startDate);
			} catch (DateTimeParseException e) {
				throw new InvalidTimeRecordException(e.getMessage(), e.getCause());
			}
		}

		if (endDate != null) {
			try {
				end = LocalDate.parse(endDate);
			} catch (DateTimeParseException e) {
				throw new InvalidTimeRecordException(e.getMessage(), e.getCause());
			}
		}

		try {
			task = this.taskRepository.getOne(taskId);
		} catch (EntityNotFoundException e) {
			throw new TaskNotFoundException(e.getMessage(), e.getCause());
		}

		if (start == null && end == null)
			results = this.trRepository.findByTask(task);
		else if (start != null && end == null)
			results = this.trRepository.findByTaskAndReportedDayGreaterThanEqual(task, start);
		else if (start == null && end != null)
			results = this.trRepository.findByTaskAndReportedDayLessThanEqual(task, end);
		else if (start != null && end != null)
			results = this.trRepository.findByTaskAndReportedDayBetween(task, start, end);

		return results.stream()
				.map(timeRecord -> new TimeRecordDTO(timeRecord))
				.collect(Collectors.toList());
	}

	@Override
	public List<TimeRecordReviewDTO> getTimeRecordReviews(Integer trId) throws TimeRecordNotFoundException {
		TimeRecord timeRecord = null;

		try {
			timeRecord = this.trRepository.getOne(trId);
		} catch (EntityNotFoundException e) {
			throw new TimeRecordNotFoundException(e.getMessage(), e.getCause());
		}

		return timeRecord.getReviews().stream()
				.map(timeRecordReview -> new TimeRecordReviewDTO(timeRecordReview))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public TimeRecordReviewDTO addTimeRecordReview(Integer trId, TimeRecordReviewDTO trReviewDTO)
			throws TimeRecordNotFoundException, KowalskiUserNotFoundException {
		TimeRecord timeRecord = null;
		KowalskiUser reviewer = null;

		try {
			timeRecord = this.trRepository.getOne(trId);
			trReviewDTO.setPreviousState(timeRecord.getState().toString());
		} catch (EntityNotFoundException e) {
			throw new TimeRecordNotFoundException(e.getMessage(), e.getCause());
		}

		try {
			reviewer = this.userRepository.getOne(trReviewDTO.getReviewerId());
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		TimeRecordReview trReview = new TimeRecordReview(trReviewDTO, reviewer);
		trReview.setTimeRecord(timeRecord);

		timeRecord.addReview(trReview);
		timeRecord.setState(trReview.getNextState());

		timeRecord = this.trRepository.saveAndFlush(timeRecord);

		// update time record review id
		trReview = timeRecord.getReviews().get(timeRecord.getReviews().size() - 1);

		return new TimeRecordReviewDTO(trReview);
	}

}
