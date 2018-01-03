package com.app.kowalski.project;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.kowalski.activity.ActivityDTO;
import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.exception.ProjectNotFoundException;
import com.app.kowalski.user.KowalskiUserDTO;
import com.app.kowalski.util.HateoasLinksBuilder;

@RestController
@RequestMapping(path = "/projects" ,
		produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
//This annotation is needed due Apache Shiro
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProjectController {

	@Autowired
	HttpServletRequest request;
	private ProjectService projectService;

	@Autowired
	ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@GetMapping
	@RequiresPermissions("apis:read")
	public ResponseEntity<List<ProjectDTO>> getProjects() {
		List<ProjectDTO> projectsDTO = this.projectService.getProjects();

		for (ProjectDTO projectDTO : projectsDTO) {
			HateoasLinksBuilder.createHateoasForProject(projectDTO);
		}
		return new ResponseEntity<List<ProjectDTO>>(projectsDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{projectId}")
	@RequiresPermissions("apis:read")
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

	@PostMapping
    @RequiresPermissions("apis:read")
	public ResponseEntity<ProjectDTO> addProject(@RequestBody ProjectDTO projectDTO) {
		projectDTO = this.projectService.addProject(projectDTO);
		HateoasLinksBuilder.createHateoasForProject(projectDTO);

		return new ResponseEntity<ProjectDTO>(projectDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/{projectId}")
	@RequiresPermissions("apis:write")
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

	@DeleteMapping(value = "/{projectId}")
	@RequiresPermissions("apis:write")
	public ResponseEntity<String> deleteProject(@PathVariable("id") int projectId) {
		try {
			boolean ret = this.projectService.deleteProject(projectId);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@GetMapping(value = "/{projectId}/activities")
	@RequiresPermissions("apis:read")
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

	@PostMapping(value = "/{projectId}/activities")
	@RequiresPermissions("apis:write")
	public ResponseEntity<ActivityDTO> addActivityForProject(@PathVariable int projectId, @RequestBody ActivityDTO activityDTO) {
		try {
			activityDTO = this.projectService.addActivityForProject(projectId, activityDTO);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		HateoasLinksBuilder.createHateoasForActivity(activityDTO);

		return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{projectId}/activities/{activityId}")
	@RequiresPermissions("apis:write")
	public ResponseEntity<ActivityDTO> deleteActivityForProject(@PathVariable int projectId, @PathVariable int activityId) {
		try {
			boolean ret = this.projectService.deleteActivityFromProject(projectId, activityId);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<ActivityDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ActivityDTO>(HttpStatus.OK);
	}

	@GetMapping(value = "/{projectId}/accountable")
	@RequiresPermissions("apis:read")
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

	@PostMapping(value = "/{projectId}/accountable")
	@RequiresPermissions("apis:write")
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

	@DeleteMapping(value = "/{projectId}/accountable")
	@RequiresPermissions("apis:write")
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

	@GetMapping(value = "/{projectId}/members")
	@RequiresPermissions("apis:read")
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

	@PostMapping(value = "/{projectId}/members")
	@RequiresPermissions("apis:write")
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

	@DeleteMapping(value = "/{projectId}/members/{userId}")
	@RequiresPermissions("apis:write")
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
