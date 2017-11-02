package com.app.kowalski.project;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

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
		p1.setTarget("Objetivo do projeto");
		p1.setMotivation("Motivação do projeto");
		p1.setStartDate(new Date());
		p1.setEndDate(new Date());
		projectRepository.save(p1);

		log.info("Projeto 1 added successfully");
	}

}
