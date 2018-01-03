package com.app.kowalski.timerecord;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.kowalski.exception.InvalidTimeRecordException;
import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.exception.TaskNotFoundException;
import com.app.kowalski.exception.TimeRecordNotFoundException;
import com.app.kowalski.timerecordreview.TimeRecordReviewDTO;
import com.app.kowalski.util.HateoasLinksBuilder;

@RestController
@RequestMapping("/timerecords")
public class TimeRecordController {

	@Autowired
	HttpServletRequest request;
	private TimeRecordService trService;

	@Autowired
	TimeRecordController(TimeRecordService trService) {
		this.trService = trService;
	}

	@RequestMapping(value = "/{trId}", method = RequestMethod.GET)
	public ResponseEntity<TimeRecordDTO> getTimeRecordById(@PathVariable int trId) {
		TimeRecordDTO timeRecordDTO = null;

		try {
			timeRecordDTO = this.trService.getTimeRecordbyId(trId);
		} catch (TimeRecordNotFoundException e) {
			return new ResponseEntity<TimeRecordDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForTimeRecord(timeRecordDTO);

		return new ResponseEntity<TimeRecordDTO>(timeRecordDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TimeRecordDTO> addTimeRecord(@RequestBody TimeRecordDTO timeRecordDTO) {
		try {
			timeRecordDTO = this.trService.addTimeRecord(timeRecordDTO);
		} catch (KowalskiUserNotFoundException | TaskNotFoundException | InvalidTimeRecordException e) {
			return new ResponseEntity<TimeRecordDTO>(HttpStatus.BAD_REQUEST);
		}

		HateoasLinksBuilder.createHateoasForTimeRecord(timeRecordDTO);

		return new ResponseEntity<TimeRecordDTO>(timeRecordDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{trId}", method = RequestMethod.PUT)
	public ResponseEntity<TimeRecordDTO> editTimeRecord(@PathVariable("trId") int trId, @RequestBody TimeRecordDTO timeRecordDTO) {
		try {
			timeRecordDTO = this.trService.editTimeRecord(trId, timeRecordDTO);
		} catch (KowalskiUserNotFoundException | TaskNotFoundException | InvalidTimeRecordException | TimeRecordNotFoundException e) {
			return new ResponseEntity<TimeRecordDTO>(HttpStatus.BAD_REQUEST);
		}

		HateoasLinksBuilder.createHateoasForTimeRecord(timeRecordDTO);

		return new ResponseEntity<TimeRecordDTO>(timeRecordDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{trId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteTimeRecord(@PathVariable("trId") int trId) {
		try {
			boolean ret = this.trService.deleteTimeRecord(trId);
		} catch (TimeRecordNotFoundException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{trId}/reviews", method = RequestMethod.GET)
	public ResponseEntity<List<TimeRecordReviewDTO>> getTimeRecordReviews(@PathVariable Integer trId) {
		List<TimeRecordReviewDTO> reviews = null;

		try {
			reviews = this.trService.getTimeRecordReviews(trId);
		} catch (TimeRecordNotFoundException e) {
			return new ResponseEntity<List<TimeRecordReviewDTO>>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<TimeRecordReviewDTO>>(reviews, HttpStatus.OK);
	}

}
