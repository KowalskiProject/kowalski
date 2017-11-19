package com.app.kowalski.activity;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.kowalski.activity.exception.ActivityNotFoundException;
import com.app.kowalski.util.HateoasLinksBuilder;

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

	@RequestMapping(value = "/{activityId}", method = RequestMethod.GET)
	public ResponseEntity<ActivityDTO> getActivityById(@PathVariable int activityId) {
		ActivityDTO activityDTO = null;
		try {
			activityDTO = this.activityService.getActivityById(activityId);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForActivity(activityDTO);

		return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{activityId}", method = RequestMethod.PUT)
	public ResponseEntity<ActivityDTO> editActivity(@PathVariable int activityId, @RequestBody ActivityDTO activityDTO) {
		activityDTO.setActivityId(activityId);
		try {
			activityDTO = this.activityService.editActivity(activityDTO);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForActivity(activityDTO);

		return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);

	}

}
