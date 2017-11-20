package com.app.kowalski.util;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.app.kowalski.activity.Activity;
import com.app.kowalski.activity.ActivityRepository;
import com.app.kowalski.project.Project;
import com.app.kowalski.project.ProjectRepository;
import com.app.kowalski.task.Task;

@Component
public class ProjectLoader implements ApplicationListener<ContextRefreshedEvent> {

	private ProjectRepository projectRepository;
	private ActivityRepository activityRepository;

	private Logger log = Logger.getLogger(ProjectLoader.class);

	@Autowired
	public void setProjectRepository(ProjectRepository projectRepository, ActivityRepository activityRepository) {
		this.projectRepository = projectRepository;
		this.activityRepository = activityRepository;
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
		a1.setName("Levantamento de requisitos");
		a1.setDescription("Requisitos do projeto");
		a1.setStartDate(new Date());
		a1.setEndDate(new Date());
		a1.setStatus("Progress");

		Activity a2 = new Activity();
		a2.setName("Implementação do back-end");
		a2.setDescription("Código projeto");
		a2.setStartDate(new Date());
		a2.setEndDate(new Date());
		a2.setStatus("Progress");

		Activity a3 = new Activity();
		a3.setName("Implementação do front-end");
		a3.setDescription("Código do projeto");
		a3.setStartDate(new Date());
		a3.setEndDate(new Date());
		a3.setStatus("Progress");

		p1.addActivity(a1);
		p1.addActivity(a2);
		p1.addActivity(a3);

		Task t1 = new Task();
		t1.setName("Pesquisa de funcionalidades");
		t1.setDescription("Pesquisa para elaboração dos requisitos");
		t1.setStartDate(new Date());
		t1.setEndDate(new Date());
		t1.setStatus("Progress");

		Task t2 = new Task();
		t2.setName("Reunião com o cliente");
		t2.setDescription("Discussão para levantamento de requisitos");
		t2.setStartDate(new Date());
		t2.setEndDate(new Date());
		t2.setStatus("Progress");

		Task t3 = new Task();
		t3.setName("Implementação e teste da classe A");
		t3.setDescription("Classe de código do back-end");
		t3.setStartDate(new Date());
		t3.setEndDate(new Date());
		t3.setStatus("Progress");

		Task t4 = new Task();
		t4.setName("Implementação e teste da classe B");
		t4.setDescription("Classe de código do front-end");
		t4.setStartDate(new Date());
		t4.setEndDate(new Date());
		t4.setStatus("Progress");

		a1.addTask(t1);
		a1.addTask(t2);
		a2.addTask(t3);
		a3.addTask(t4);

		projectRepository.save(p1);

		log.info("Projeto " + p1.getName() + " added successfully");
	}

}
