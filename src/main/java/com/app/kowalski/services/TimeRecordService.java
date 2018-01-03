package com.app.kowalski.services;

import java.util.List;

import com.app.kowalski.dto.TimeRecordDTO;
import com.app.kowalski.exception.InvalidTimeRecordException;
import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.exception.TaskNotFoundException;
import com.app.kowalski.exception.TimeRecordNotFoundException;
import com.app.kowalski.timerecordreview.TimeRecordReviewDTO;

/**
 * Interface to expose allowed methods related to time records.
 *
 * It receives the TimeRecordDTO object in order to perform the
 * operations and does not expose the domain class.
 *
 * It could receive a JSON though, but the DTO is better to
 * handle the information.
 */
public interface TimeRecordService {

	/**
	 * Creates a new time record using given parameters.
	 *
	 * @param timeRecordDTO data to create time record
	 * @return time record data
	 *
	 * @throws KowalskiUserNotFoundException No user was found in the system
	 * @throws TaskNotFoundException No task was found in the system
	 * @throws InvalidTimeRecordException Error handling time record parameters
	 */
	public TimeRecordDTO addTimeRecord(TimeRecordDTO timeRecordDTO)
			throws KowalskiUserNotFoundException, TaskNotFoundException, InvalidTimeRecordException;

	/**
	 * Reads a specific time record.
	 * @param trId time record reference
	 * @return time record data
	 *
	 * @throws TimeRecordNotFoundException No time record was found in the system
	 */
	public TimeRecordDTO getTimeRecordbyId(Integer trId) throws TimeRecordNotFoundException;

	/**
	 * Edits given time record with given data
	 * @param trId time record reference
	 * @param timeRecordDTO data to be included in the given time record
	 * @return time record data
	 *
	 * @throws KowalskiUserNotFoundException No user was found in the system
	 * @throws TaskNotFoundException No task was found in the system
	 * @throws InvalidTimeRecordException Error handling time record parameters
	 * @throws TimeRecordNotFoundException No time record was found in the system
	 */
	public TimeRecordDTO editTimeRecord(Integer trId, TimeRecordDTO timeRecordDTO)
			throws KowalskiUserNotFoundException, TaskNotFoundException,
			InvalidTimeRecordException, TimeRecordNotFoundException;

	/**
	 * Removes a given time record from the system
	 * @param trId time record reference
	 * @return true if record was removed successfully, false otherwise
	 *
	 * @throws TimeRecordNotFoundException No time record was found in the system
	 */
	public boolean deleteTimeRecord(Integer trId) throws TimeRecordNotFoundException;

	/**
	 * Returns all time records for given user
	 * @param userId user reference
	 * @param startDate initial date to get records
	 * @param endDate final date to get records
	 * @return List of records
	 *
	 * @throws KowalskiUserNotFoundException No user was found in the system
	 * @throws InvalidTimeRecordException Error handling time record parameters
	 */
	public List<TimeRecordDTO> getAllRecordsForUser(Integer userId, String startDate, String endDate)
			throws KowalskiUserNotFoundException, InvalidTimeRecordException;

	/**
	 * Returns all time records for given task
	 * @param taskId task reference
	 * @param startDate initial date to get records
	 * @param endDate final date to get records
	 * @return List of records
	 *
	 * @throws TaskNotFoundException No task was found in the system
	 * @throws InvalidTimeRecordException Error handling time record parameters
	 */
	public List<TimeRecordDTO> getAllRecordsForTask(Integer taskId, String startDate, String endDate)
			throws TaskNotFoundException, InvalidTimeRecordException;

	/**
	 * Returns all reviews associated to given time record
	 * @param trId time record reference
	 * @return List of reviews
	 *
	 * @throws TimeRecordNotFoundException No time record was found in the system
	 */
	public List<TimeRecordReviewDTO> getTimeRecordReviews(Integer trId) throws TimeRecordNotFoundException;

	/**
	 * Includes a new review for given time record
	 * @param trId time record reference
	 * @param trReviewDTO time record review data
	 * @return time record review data
	 *
	 * @throws TimeRecordNotFoundException No time record was found in the system
	 * @throws KowalskiUserNotFoundException No user was found in the system
	 */
	public TimeRecordReviewDTO addTimeRecordReview(Integer trId, TimeRecordReviewDTO trReviewDTO)
			throws TimeRecordNotFoundException, KowalskiUserNotFoundException;
}
