package com.app.kowalski.activity;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ActivityDTO> getActivityById(@PathVariable int id) {
		ActivityDTO activityDTO = null;
		try {
			activityDTO = this.activityService.getActivityById(id);
			Link selfLink = linkTo(ActivityController.class).slash(activityDTO.getActivityId()).withSelfRel();
			activityDTO.add(selfLink);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ActivityDTO> editActivity(@PathVariable("id") int id, @RequestBody ActivityDTO activityDTO) {
		activityDTO.setActivityId(id);
		try {
			activityDTO = this.activityService.editActivity(activityDTO);
			Link selfLink = linkTo(ActivityController.class).slash(activityDTO.getActivityId()).withSelfRel();
			activityDTO.add(selfLink);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);

	}

}
