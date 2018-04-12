package com.app.kowalski.web.rest_controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.kowalski.dto.ActivityDTO;
import com.app.kowalski.dto.KowalskiUserDTO;
import com.app.kowalski.dto.ProjectDTO;
import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.exception.ProjectNotFoundException;
import com.app.kowalski.exception.ProjectServiceException;
import com.app.kowalski.services.KowalskiUserService;
import com.app.kowalski.services.ProjectService;
import com.app.kowalski.util.HateoasLinksBuilder;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	HttpServletRequest request;
	private ProjectService projectService;
	private KowalskiUserService userService;

	@Autowired
	ProjectController(ProjectService projectService, KowalskiUserService userService) {
		this.projectService = projectService;
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ProjectDTO>> getProjects(@RequestParam(value="userId", required=false) Integer userId) {
		List<ProjectDTO> projectsDTO = this.projectService.getProjects(userId);

		for (ProjectDTO projectDTO : projectsDTO) {
			HateoasLinksBuilder.createHateoasForProject(projectDTO);
		}
		return new ResponseEntity<List<ProjectDTO>>(projectsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
	public ResponseEntity<ProjectDTO> getProjectById(@PathVariable int projectId) {
		ProjectDTO projectDTO = null;

		try {
			projectDTO = this.projectService.getProjectById(projectId);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<ProjectDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForProject(projectDTO);

		return new ResponseEntity<ProjectDTO>(projectDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ProjectDTO> addProject(@Valid @RequestBody ProjectDTO projectDTO) {
		if (projectDTO.getAccountableId() == null) {
			try {
				// configure the user that is creating the project as its accountable
				Integer userId = this.userService.getKowalskiUserByUsername(
						request.getUserPrincipal().getName()).getkUserId();
				projectDTO.setAccountableId(userId);
			} catch (KowalskiUserNotFoundException e1) {
				return new ResponseEntity<ProjectDTO>(HttpStatus.PRECONDITION_FAILED);
			}
		}

		try {
			projectDTO = this.projectService.addProject(projectDTO);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<ProjectDTO>(HttpStatus.CONFLICT);
		}
		HateoasLinksBuilder.createHateoasForProject(projectDTO);

		return new ResponseEntity<ProjectDTO>(projectDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}", method = RequestMethod.PUT)
	public ResponseEntity<ProjectDTO> editProject(@PathVariable int id, @RequestBody ProjectDTO projectDTO) {
		projectDTO.setProjectId(id);

		try {
			projectDTO = this.projectService.editProject(projectDTO);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<ProjectDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForProject(projectDTO);

		return new ResponseEntity<ProjectDTO>(projectDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteProject(@PathVariable int projectId) {
		try {
			boolean ret = this.projectService.deleteProject(projectId);
		} catch (ProjectNotFoundException | ProjectServiceException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}/activities", method = RequestMethod.GET)
	public ResponseEntity<List<ActivityDTO>> getActivitiesForProject(
			@PathVariable Integer projectId,
			@RequestParam(value="includeTasks", required=false, defaultValue="false") Boolean includeTasks,
			@RequestParam(value="tasksAccountableId", required=false) Integer tasksAccountableId) {
		List<ActivityDTO> activitiesDTO = null;

		try {
			activitiesDTO = projectService.getAllActivitiesForProject(projectId, includeTasks, tasksAccountableId);
		} catch (ProjectNotFoundException | KowalskiUserNotFoundException e) {
			return new ResponseEntity<List<ActivityDTO>>(HttpStatus.NOT_FOUND);
		}

		for (ActivityDTO activityDTO : activitiesDTO)
			HateoasLinksBuilder.createHateoasForActivity(activityDTO);

		return new ResponseEntity<List<ActivityDTO>>(activitiesDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}/activities", method = RequestMethod.POST)
	public ResponseEntity<ActivityDTO> addActivityForProject(@PathVariable int projectId, @RequestBody ActivityDTO activityDTO) {
		try {
			activityDTO = this.projectService.addActivityForProject(projectId, activityDTO);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForActivity(activityDTO);

		return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}/activities/{activityId}", method = RequestMethod.DELETE)
	public ResponseEntity<ActivityDTO> deleteActivityForProject(@PathVariable int projectId, @PathVariable int activityId) {
		try {
			boolean ret = this.projectService.deleteActivityFromProject(projectId, activityId);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ActivityDTO>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}/accountable", method = RequestMethod.GET)
	public ResponseEntity<KowalskiUserDTO> getAccountableForProject(@PathVariable int projectId) {
		KowalskiUserDTO kowalskiUserDTO = null;

		try {
			kowalskiUserDTO = this.projectService.getAccountableForProject(projectId);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<KowalskiUserDTO>(HttpStatus.NOT_FOUND);
		}

		if (kowalskiUserDTO == null) {
			return new ResponseEntity<KowalskiUserDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForKowalskiUser(kowalskiUserDTO);
		return new ResponseEntity<KowalskiUserDTO>(kowalskiUserDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}/accountable", method = RequestMethod.POST)
	public ResponseEntity<ProjectDTO> setAccountableForProject(@PathVariable Integer projectId, @RequestBody Integer kUserId) {
		ProjectDTO projectDTO = null;

		try {
			projectDTO = this.projectService.setAccountableForProject(projectId, kUserId);
		} catch (ProjectNotFoundException | KowalskiUserNotFoundException e) {
			return new ResponseEntity<ProjectDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForProject(projectDTO);
		return new ResponseEntity<ProjectDTO>(projectDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}/accountable", method = RequestMethod.DELETE)
	public ResponseEntity<ProjectDTO> removeAccountableForProject(@PathVariable Integer projectId) {
		ProjectDTO projectDTO = null;

		try {
			projectDTO = this.projectService.removeAccountableForProject(projectId);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<ProjectDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForProject(projectDTO);
		return new ResponseEntity<ProjectDTO>(projectDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}/members", method = RequestMethod.GET)
	public ResponseEntity<List<KowalskiUserDTO>> getProjectMembers(@PathVariable int projectId) {
		List<KowalskiUserDTO> projectUsersDTO = null;

		try {
			projectUsersDTO = this.projectService.getProjectMembers(projectId);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<List<KowalskiUserDTO>>(HttpStatus.NOT_FOUND);
		}

		for (KowalskiUserDTO kowalskiUserDTO : projectUsersDTO)
			HateoasLinksBuilder.createHateoasForKowalskiUser(kowalskiUserDTO);

		return new ResponseEntity<List<KowalskiUserDTO>>(projectUsersDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}/members", method = RequestMethod.POST)
	public ResponseEntity<List<KowalskiUserDTO>> addMemberToProject(@PathVariable Integer projectId,
			@RequestBody List<Integer> kUserIdList) {
		List<KowalskiUserDTO> projectMembers = null;

		try {
			for (Integer userId : kUserIdList)
				this.projectService.addMemberToProject(projectId, userId);

			projectMembers = this.projectService.getProjectMembers(projectId);
		} catch (ProjectNotFoundException | KowalskiUserNotFoundException e) {
			return new ResponseEntity<List<KowalskiUserDTO>>(HttpStatus.NOT_FOUND);
		}

		for (KowalskiUserDTO kowalskiUserDTO : projectMembers)
			HateoasLinksBuilder.createHateoasForKowalskiUser(kowalskiUserDTO);

		return new ResponseEntity<List<KowalskiUserDTO>>(projectMembers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}/members/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<ProjectDTO> removeMemberFromProject(@PathVariable Integer projectId, @PathVariable Integer userId) {
		ProjectDTO projectDTO = null;

		try {
			projectDTO = this.projectService.removeMemberFromProject(projectId, userId);
		} catch (ProjectNotFoundException | KowalskiUserNotFoundException e) {
			return new ResponseEntity<ProjectDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForProject(projectDTO);
		return new ResponseEntity<ProjectDTO>(projectDTO, HttpStatus.OK);
	}

}
