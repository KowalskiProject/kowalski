package com.app.kowalski.activity;

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

import com.app.kowalski.activity.dto.ActivityDTO;
import com.app.kowalski.activity.exception.ActivityNotFoundException;

@RestController
@RequestMapping("/activities")
public class ActivityController {

	@Autowired
	HttpServletRequest request;
	private ActivityService activityService;

	@Autowired
	ActivityController(ActivityService activityService) {
		this.activityService = activityService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ActivityDTO>> getActivities() {
		return new ResponseEntity<List<ActivityDTO>>(this.activityService.getActivities(), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ActivityDTO> getActivityById(@PathVariable int id) {
		try {
			ActivityDTO activityDTO = this.activityService.getActivityById(id);
			return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ActivityDTO> addActivity(@RequestBody ActivityDTO activityDTO) {
		activityDTO = this.activityService.addActivity(activityDTO);
		return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ActivityDTO> editActivity(@PathVariable("id") int id, @RequestBody ActivityDTO activityDTO) {
		activityDTO.setActivityId(id);
		try {
			activityDTO = this.activityService.editActivity(activityDTO);
			return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteActivity(@PathVariable("id") int id) {
		try {
			boolean ret = this.activityService.deleteActivity(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}

}
