package com.app.kowalski.util;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.app.kowalski.activity.Activity;
import com.app.kowalski.project.Project;
import com.app.kowalski.project.ProjectRepository;

@Component
public class ProjectLoader implements ApplicationListener<ContextRefreshedEvent> {

	private ProjectRepository projectRepository;

	private Logger log = Logger.getLogger(ProjectLoader.class);

	@Autowired
	public void setProjectRepository(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		Project p1 = new Project();
		p1.setName("Projeto 1");
		p1.setCode("ABC1");
		p1.setDescription("Pequena descrição do projeto");
		p1.setStartDate(new Date());
		p1.setEndDate(new Date());
		p1 = projectRepository.save(p1);

		Activity a1 = new Activity();
		a1.setName("Inicio do projeto");
		a1.setDescription("Fase inicial do projeto");
		a1.setStartDate(new Date());
		a1.setEndDate(new Date());
		a1.setStatus("Progress");

		Activity a2 = new Activity();
		a2.setName("Meio do projeto");
		a2.setDescription("Fase intermediária do projeto");
		a2.setStartDate(new Date());
		a2.setEndDate(new Date());
		a2.setStatus("Progress");

		Activity a3 = new Activity();
		a3.setName("Fim do projeto");
		a3.setDescription("Fase final do projeto");
		a3.setStartDate(new Date());
		a3.setEndDate(new Date());
		a3.setStatus("Progress");

		p1.addActivity(a1);
		p1.addActivity(a2);
		p1.addActivity(a3);

		projectRepository.save(p1);

		log.info("Projeto " + p1.getName() + " added successfully");
	}

}
