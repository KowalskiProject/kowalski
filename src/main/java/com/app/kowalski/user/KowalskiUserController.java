package com.app.kowalski.user;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.kowalski.activity.ActivityDTO;
import com.app.kowalski.project.ProjectDTO;
import com.app.kowalski.task.TaskDTO;
import com.app.kowalski.user.exception.KowalskiUserNotFoundException;
import com.app.kowalski.util.HateoasLinksBuilder;

@RestController
@RequestMapping("/users")
public class KowalskiUserController {

	@Autowired
	HttpServletRequest request;
	private KowalskiUserService kowalskiUserService;

	@Autowired
	KowalskiUserController(KowalskiUserService kowalskiUserService) {
		this.kowalskiUserService = kowalskiUserService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<KowalskiUserDTO>> getAllKowalskiUsers() {
		List<KowalskiUserDTO> kowalskiUsersDTO = this.kowalskiUserService.getKowalskiUsers();
		for (KowalskiUserDTO kowalskiUserDTO : kowalskiUsersDTO) {
			HateoasLinksBuilder.createHateoasForKowalskiUser(kowalskiUserDTO);
		}
		return new ResponseEntity<List<KowalskiUserDTO>>(kowalskiUsersDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<KowalskiUserDTO> addKowalskiUser(@RequestBody KowalskiUserDTO kowalskiUserDTO) {
		KowalskiUserDTO kowalskiUsersDTO = this.kowalskiUserService.addKowaslkiUser(kowalskiUserDTO);
		HateoasLinksBuilder.createHateoasForKowalskiUser(kowalskiUserDTO);
		return new ResponseEntity<KowalskiUserDTO>(kowalskiUsersDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{kUserId}", method = RequestMethod.GET)
	public ResponseEntity<KowalskiUserDTO> getKowalskiUser(@PathVariable int kUserId) {
		KowalskiUserDTO kowalskiUserDTO;
		try {
			kowalskiUserDTO = this.kowalskiUserService.getKowalskiUser(kUserId);
		} catch (KowalskiUserNotFoundException e) {
			return new ResponseEntity<KowalskiUserDTO>(HttpStatus.NOT_FOUND);
		}
		HateoasLinksBuilder.createHateoasForKowalskiUser(kowalskiUserDTO);
		return new ResponseEntity<KowalskiUserDTO>(kowalskiUserDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{kUserId}", method = RequestMethod.PUT)
	public ResponseEntity<KowalskiUserDTO> editKowalskiUser(@PathVariable int kUserId,
			@RequestBody KowalskiUserDTO kowalskiUserDTO) {
		kowalskiUserDTO.setkUserId(kUserId);
		try {
			kowalskiUserDTO = this.kowalskiUserService.editKowaslkiUser(kowalskiUserDTO);
		} catch (KowalskiUserNotFoundException e) {
			return new ResponseEntity<KowalskiUserDTO>(HttpStatus.NOT_FOUND);
		}
		HateoasLinksBuilder.createHateoasForKowalskiUser(kowalskiUserDTO);
		return new ResponseEntity<KowalskiUserDTO>(kowalskiUserDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{kUserId}", method = RequestMethod.DELETE)
	public ResponseEntity<KowalskiUserDTO> deleteKowalskiUser(@PathVariable int kUserId) {
		try {
			boolean ret = this.kowalskiUserService.deleteKowalskiUser(kUserId);
		} catch (KowalskiUserNotFoundException e) {
			return new ResponseEntity<KowalskiUserDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<KowalskiUserDTO>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{kUserId}/accountableProjects", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectDTO>> getAccountableProjects(@PathVariable int kUserId) {
		List<ProjectDTO> projectsDTO = null;
		try {
			projectsDTO = this.kowalskiUserService.getAccountableProjects(kUserId);
		} catch (KowalskiUserNotFoundException e) {
			return new ResponseEntity<List<ProjectDTO>>(HttpStatus.NOT_FOUND);
		}

		for (ProjectDTO projectDTO : projectsDTO)
			HateoasLinksBuilder.createHateoasForProject(projectDTO);

		return new ResponseEntity<List<ProjectDTO>>(projectsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{kUserId}/accountableActivities", method = RequestMethod.GET)
	public ResponseEntity<List<ActivityDTO>> getAccountableActivities(@PathVariable int kUserId) {
		List<ActivityDTO> activitiesDTO = null;
		try {
			activitiesDTO = this.kowalskiUserService.getAccountableActivities(kUserId);
		} catch (KowalskiUserNotFoundException e) {
			return new ResponseEntity<List<ActivityDTO>>(HttpStatus.NOT_FOUND);
		}

		for (ActivityDTO activityDTO : activitiesDTO)
			HateoasLinksBuilder.createHateoasForActivity(activityDTO);

		return new ResponseEntity<List<ActivityDTO>>(activitiesDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{kUserId}/accountableTasks", method = RequestMethod.GET)
	public ResponseEntity<List<TaskDTO>> getAccountableTasks(@PathVariable int kUserId) {
		List<TaskDTO> tasksDTO = null;
		try {
			tasksDTO = this.kowalskiUserService.getAccountableTasks(kUserId);
		} catch (KowalskiUserNotFoundException e) {
			return new ResponseEntity<List<TaskDTO>>(HttpStatus.NOT_FOUND);
		}

		for (TaskDTO taskDTO : tasksDTO)
			HateoasLinksBuilder.createHateoasForTask(taskDTO);

		return new ResponseEntity<List<TaskDTO>>(tasksDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{kUserId}/projects", method = RequestMethod.GET)
	public ResponseEntity<Set<ProjectDTO>> getProjects(@PathVariable int kUserId) {
		Set<ProjectDTO> projectsDTO = null;
		try {
			projectsDTO = this.kowalskiUserService.getProjects(kUserId);
		} catch (KowalskiUserNotFoundException e) {
			return new ResponseEntity<Set<ProjectDTO>>(HttpStatus.NOT_FOUND);
		}

		for (ProjectDTO projectDTO : projectsDTO)
			HateoasLinksBuilder.createHateoasForProject(projectDTO);

		return new ResponseEntity<Set<ProjectDTO>>(projectsDTO, HttpStatus.OK);
	}

}
