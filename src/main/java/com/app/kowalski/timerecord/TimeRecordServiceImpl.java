package com.app.kowalski.timerecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.kowalski.exception.InvalidTimeRecordException;
import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.exception.TaskNotFoundException;
import com.app.kowalski.exception.TimeRecordNotFoundException;
import com.app.kowalski.task.Task;
import com.app.kowalski.task.TaskRepository;
import com.app.kowalski.user.KowalskiUser;
import com.app.kowalski.user.KowalskiUserRepository;

@Service
public class TimeRecordServiceImpl implements TimeRecordService {

	private final TimeRecordRepository trRepository;
	private final KowalskiUserRepository userRepository;
	private final TaskRepository taskRepository;

	private static final SimpleDateFormat sdfReportedTime = new SimpleDateFormat("HH:mm");

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
		Date reportedTimeDate = null;
		KowalskiUser user = null;
		Task task = null;

		try {
			reportedTimeDate = sdfReportedTime.parse(timeRecordDTO.getReportedTime());
		} catch (ParseException e) {
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

		TimeRecord timeRecord = new TimeRecord(user, task, reportedTimeDate, timeRecordDTO.getComment());
		timeRecord = this.trRepository.save(timeRecord);

		/*user.addTimeRecord(timeRecord);
		user = this.userRepository.save(user);

		task.addTimeRecord(timeRecord);
		task = this.taskRepository.save(task);*/

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
		Date reportedTimeDate = null;
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
			reportedTimeDate = sdfReportedTime.parse(timeRecordDTO.getReportedTime());
		} catch (ParseException e) {
			throw new InvalidTimeRecordException(e.getMessage(), e.getCause());
		}

		timeRecord = timeRecord.editTimeRecord(user, task, reportedTimeDate, timeRecordDTO.getComment());
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
	public List<TimeRecordDTO> getAllRecordsForUser(Integer userId) throws KowalskiUserNotFoundException {
		KowalskiUser user = null;

		try {
			user = this.userRepository.getOne(userId);
		} catch (EntityNotFoundException e) {
			throw new KowalskiUserNotFoundException(e.getMessage(), e.getCause());
		}

		List<TimeRecord> results = this.trRepository.findByUser(user);

		return results.stream()
				.map(timeRecord -> new TimeRecordDTO(timeRecord))
				.collect(Collectors.toList());
	}

	@Override
	public List<TimeRecordDTO> getAllRecordsForTask(Integer taskId) throws TaskNotFoundException {
		Task task = null;

		try {
			task = this.taskRepository.getOne(taskId);
		} catch (EntityNotFoundException e) {
			throw new TaskNotFoundException(e.getMessage(), e.getCause());
		}

		List<TimeRecord> results = this.trRepository.findByTask(task);

		return results.stream()
				.map(timeRecord -> new TimeRecordDTO(timeRecord))
				.collect(Collectors.toList());
	}

}
