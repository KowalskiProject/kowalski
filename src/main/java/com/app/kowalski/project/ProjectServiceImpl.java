package com.app.kowalski.project;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.kowalski.project.dto.ProjectDTO;
import com.app.kowalski.project.dto.ProjectSummaryDTO;
import com.app.kowalski.project.exception.ProjectNotFoundException;

@Service
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository repository;

	@Autowired
	public ProjectServiceImpl(ProjectRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<ProjectSummaryDTO> getProjects() {
		List<Project> projects = this.repository.findAll();
		return projects.stream()
				.map(project -> new ProjectSummaryDTO(project))
				.collect(Collectors.toList());
	}

	@Override
	public ProjectDTO getProjectById(int id) throws ProjectNotFoundException {
		try {
			return new ProjectDTO(this.repository.getOne(id));
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public ProjectDTO addProject(ProjectDTO projectDTO) {
		// check business rules here
		Project project = new Project().convertToProject(projectDTO);
		project = this.repository.save(project);
		return new ProjectDTO(project);
	}

	@Override
	public ProjectDTO editProject(ProjectDTO projectDTO) throws ProjectNotFoundException {
		// check business rules here
		try {
			Project project = this.repository.getOne(projectDTO.getProjectId());
			project = project.convertToProject(projectDTO);
			project = this.repository.save(project);
			return new ProjectDTO(project);
		} catch (EntityNotFoundException e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public boolean deleteProject(int id) throws ProjectNotFoundException {
		try {
			this.repository.delete(id);
		} catch (Exception e) {
			throw new ProjectNotFoundException(e.getMessage(), e.getCause());
		}
		return true;
	}

}
