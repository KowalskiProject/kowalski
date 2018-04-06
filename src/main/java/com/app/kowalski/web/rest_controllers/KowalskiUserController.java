package com.app.kowalski.web.rest_controllers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.kowalski.da.entities.KowalskiUserRole;
import com.app.kowalski.dto.ActivityDTO;
import com.app.kowalski.dto.KowalskiUserDTO;
import com.app.kowalski.dto.ProjectDTO;
import com.app.kowalski.dto.TaskDTO;
import com.app.kowalski.dto.TimeRecordDTO;
import com.app.kowalski.exception.InvalidTimeRecordException;
import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.exception.KowalskiUserServiceException;
import com.app.kowalski.mail.Mail;
import com.app.kowalski.services.ActivityService;
import com.app.kowalski.services.KowalskiUserService;
import com.app.kowalski.services.MailService;
import com.app.kowalski.services.ProjectService;
import com.app.kowalski.services.TaskService;
import com.app.kowalski.services.TimeRecordService;
import com.app.kowalski.util.HateoasLinksBuilder;

@RestController
@RequestMapping("/users")
//This annotation is needed due Apache Shiro
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class KowalskiUserController {

	@Autowired
	HttpServletRequest request;
	private KowalskiUserService kowalskiUserService;
	private ProjectService projectService;
	private ActivityService activityService;
	private TaskService taskService;
	private TimeRecordService trService;
	private MailService mailService;

	public static final LocalDate today = LocalDate.now();
	public static final LocalDate oneWeekLater = today.minusDays(7);

	@Autowired
	KowalskiUserController(KowalskiUserService kowalskiUserService, ProjectService projectService,
			ActivityService activityService, TaskService taskService, TimeRecordService trService,
			MailService mailService) {
		this.kowalskiUserService = kowalskiUserService;
		this.projectService = projectService;
		this.activityService = activityService;
		this.taskService = taskService;
		this.trService = trService;
		this.mailService = mailService;
	}

	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public ResponseEntity<String> testEmail() {
		Mail mail = new Mail();
        mail.setMailFrom("itlic@lenovo.com");
        mail.setMailTo("fdassan@lenovo.com");
        mail.setMailSubject("Spring 4 - Email with FreeMarker template");

        Map<String, Object> model = new HashMap < String, Object > ();
        model.put("firstName", "Yashwant");
        model.put("lastName", "Chavan");
        model.put("location", "Pune");
        model.put("signature", "www.technicalkeeda.com");
        mail.setModel(model);

        System.out.println("*** X");
        this.mailService.sendEmail(mail);
        System.out.println("*** Y");

		return new ResponseEntity<String>("Ok", HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<KowalskiUserDTO>> getAllKowalskiUsers() {
		List<KowalskiUserDTO> kowalskiUsersDTO = this.kowalskiUserService.getKowalskiUsers();
		for (KowalskiUserDTO kowalskiUserDTO : kowalskiUsersDTO) {
			HateoasLinksBuilder.createHateoasForKowalskiUser(kowalskiUserDTO);
		}
		return new ResponseEntity<List<KowalskiUserDTO>>(kowalskiUsersDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public ResponseEntity<List<KowalskiUserRole>> getKowalskiUserRoles() {
		return new ResponseEntity<List<KowalskiUserRole>>(Arrays.asList(KowalskiUserRole.values()), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<KowalskiUserDTO> addKowalskiUser(@RequestBody KowalskiUserDTO kowalskiUserDTO) {
		try {
			kowalskiUserDTO = this.kowalskiUserService.addKowaslkiUser(kowalskiUserDTO);
		} catch (KowalskiUserServiceException e) {
			return new ResponseEntity<KowalskiUserDTO>(HttpStatus.PRECONDITION_FAILED);
		}
		HateoasLinksBuilder.createHateoasForKowalskiUser(kowalskiUserDTO);
		return new ResponseEntity<KowalskiUserDTO>(kowalskiUserDTO, HttpStatus.CREATED);
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
			projectsDTO = this.projectService.getAccountableProjectsForUser(kUserId);
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
			activitiesDTO = this.activityService.getAccountableActivitiesForUser(kUserId);
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
			tasksDTO = this.taskService.getAccountableTasksForUser(kUserId);
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

	@RequestMapping(value = "/{kUserId}/timerecords", method = RequestMethod.GET)
	public ResponseEntity<List<TimeRecordDTO>> getTimeRecords(
			@PathVariable int kUserId,
			@RequestParam(value = "from", required = false) String startDate,
			@RequestParam(value = "to", required = false) String endDate) {
		List<TimeRecordDTO> timeRecords = null;

		try {
			timeRecords = trService.getAllRecordsForUser(kUserId, startDate, endDate);
		} catch (KowalskiUserNotFoundException | InvalidTimeRecordException e) {
			return new ResponseEntity<List<TimeRecordDTO>>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<TimeRecordDTO>>(timeRecords, HttpStatus.OK);
	}

}
