package com.app.kowalski.project;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public ProjectDTO getProjectById(int id) {
		Project project = this.repository.getOne(id);
		return new ProjectDTO(project);
	}

	@Override
	public ProjectDTO addProject(ProjectDTO projectDTO) {
		Project project = new Project(projectDTO);
		project = this.repository.save(project);
		return new ProjectDTO(project);
	}

	@Override
	public ProjectDTO editProject(ProjectDTO projectDTO) {
		Project project = new Project(projectDTO);
		project.setProjectId(projectDTO.getProjectId());
		project = this.repository.save(project);

		return new ProjectDTO(project);
	}

	@Override
	public boolean deleteProject(int id) {
		this.repository.delete(id);
		return true;
	}

}
