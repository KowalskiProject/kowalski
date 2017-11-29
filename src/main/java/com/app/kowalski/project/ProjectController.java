package com.app.kowalski.project;

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
import com.app.kowalski.project.exception.ProjectNotFoundException;
import com.app.kowalski.user.KowalskiUserDTO;
import com.app.kowalski.user.exception.KowalskiUserNotFoundException;
import com.app.kowalski.util.HateoasLinksBuilder;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	HttpServletRequest request;
	private ProjectService projectService;

	@Autowired
	ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ProjectDTO>> getProjects() {
		List<ProjectDTO> projectsDTO = this.projectService.getProjects();

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
	public ResponseEntity<ProjectDTO> addProject(@RequestBody ProjectDTO projectDTO) {
		projectDTO = this.projectService.addProject(projectDTO);
		HateoasLinksBuilder.createHateoasForProject(projectDTO);

		return new ResponseEntity<ProjectDTO>(projectDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}", method = RequestMethod.PUT)
	public ResponseEntity<ProjectDTO> editProject(@PathVariable("id") int id, @RequestBody ProjectDTO projectDTO) {
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
	public ResponseEntity<String> deleteProject(@PathVariable("id") int projectId) {
		try {
			boolean ret = this.projectService.deleteProject(projectId);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}/activities", method = RequestMethod.GET)
	public ResponseEntity<List<ActivityDTO>> getActivitiesForProject(@PathVariable int projectId) {
		List<ActivityDTO> activitiesDTO = null;

		try {
			activitiesDTO = projectService.getAllActivitiesForProject(projectId);
		} catch (ProjectNotFoundException e) {
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
	public ResponseEntity<Set<KowalskiUserDTO>> getProjectMembers(@PathVariable int projectId) {
		Set<KowalskiUserDTO> projectUsersDTO = null;

		try {
			projectUsersDTO = this.projectService.getProjectMembers(projectId);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<Set<KowalskiUserDTO>>(HttpStatus.NOT_FOUND);
		}

		for (KowalskiUserDTO kowalskiUserDTO : projectUsersDTO)
			HateoasLinksBuilder.createHateoasForKowalskiUser(kowalskiUserDTO);

		return new ResponseEntity<Set<KowalskiUserDTO>>(projectUsersDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectId}/members", method = RequestMethod.POST)
	public ResponseEntity<ProjectDTO> addMemberToProject(@PathVariable Integer projectId, @RequestBody Integer kUserId) {
		ProjectDTO projectDTO = null;

		try {
			projectDTO = this.projectService.addMemberToProject(projectId, kUserId);
		} catch (ProjectNotFoundException | KowalskiUserNotFoundException e) {
			return new ResponseEntity<ProjectDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForProject(projectDTO);
		return new ResponseEntity<ProjectDTO>(projectDTO, HttpStatus.OK);
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
