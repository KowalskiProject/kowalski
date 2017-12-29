package com.app.kowalski.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.app.kowalski.activity.ActivityDTO;
import com.app.kowalski.activity.ActivityService;
import com.app.kowalski.exception.ActivityNotFoundException;
import com.app.kowalski.exception.InvalidTimeRecordException;
import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.exception.ProjectNotFoundException;
import com.app.kowalski.exception.TaskNotFoundException;
import com.app.kowalski.project.ProjectDTO;
import com.app.kowalski.project.ProjectService;
import com.app.kowalski.task.TaskDTO;
import com.app.kowalski.task.TaskService;
import com.app.kowalski.timerecord.TimeRecordDTO;
import com.app.kowalski.timerecord.TimeRecordService;
import com.app.kowalski.user.KowalskiUserDTO;
import com.app.kowalski.user.KowalskiUserService;

@Component
public class ProjectLoader implements ApplicationListener<ContextRefreshedEvent> {

	private KowalskiUserService kowalskiUserService;
	private ProjectService projectService;
	private ActivityService activityService;
	private TaskService taskService;
	private TimeRecordService trService;

	@Autowired
	public void setProjectService(KowalskiUserService kowalskiUserService, ProjectService projectService,
			ActivityService activityService, TaskService taskService, TimeRecordService trService) {
		this.kowalskiUserService = kowalskiUserService;
		this.projectService = projectService;
		this.activityService = activityService;
		this.taskService = taskService;
		this.trService = trService;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		/*******************************************************************************
		 * Users
		 *******************************************************************************/
		KowalskiUserDTO k1 = new KowalskiUserDTO("Cartman", "cartman", "cartman@kowalski.com", "cartman");
		KowalskiUserDTO k2 = new KowalskiUserDTO("Stan", "stan", "stan@kowalski.com", "stan");
		KowalskiUserDTO k3 = new KowalskiUserDTO("Kyle", "kyle", "kyle@kowalski.com", "kyle");
		KowalskiUserDTO k4 = new KowalskiUserDTO("Kenny", "kenny", "kenny@kowalski.com", "kenny");

		k1 = kowalskiUserService.addKowaslkiUser(k1);
		k2 = kowalskiUserService.addKowaslkiUser(k2);
		k3 = kowalskiUserService.addKowaslkiUser(k3);
		k4 = kowalskiUserService.addKowaslkiUser(k4);

		/*******************************************************************************
		 * Projects
		 *******************************************************************************/
		ProjectDTO p1 = new ProjectDTO("Projeto 1", "ABC1", "Pequena descrição do projeto", "2017-12-20", "2018-12-20");
		p1 = projectService.addProject(p1);

		try {
			projectService.setAccountableForProject(p1.getProjectId(), k1.getkUserId());
		} catch (ProjectNotFoundException | KowalskiUserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*******************************************************************************
		 * Activities
		 *******************************************************************************/
		ActivityDTO a1 = new ActivityDTO("Levantamento de requisitos", "Requisitos do projeto", "Progress", "2017-12-20", "2018-12-20");
		ActivityDTO a2 = new ActivityDTO("Implementação do back-end", "Código projeto", "Progress", "2017-12-20", "2018-12-20");
		ActivityDTO a3 = new ActivityDTO("Implementação do front-end", "Código do projeto", "Progress", "2017-12-20", "2018-12-20");

		try {
			a1 = projectService.addActivityForProject(p1.getProjectId(), a1);
			a2 = projectService.addActivityForProject(p1.getProjectId(), a2);
			a3 = projectService.addActivityForProject(p1.getProjectId(), a3);
		} catch (ProjectNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			a1 = activityService.setAccountableForActivity(a1.getActivityId(), k2.getkUserId());
			a2 = activityService.setAccountableForActivity(a2.getActivityId(), k3.getkUserId());
			a3 = activityService.setAccountableForActivity(a3.getActivityId(), k4.getkUserId());
		} catch (ActivityNotFoundException | KowalskiUserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*******************************************************************************
		 * Tasks
		 *******************************************************************************/
		TaskDTO t1 = new TaskDTO("Pesquisa de funcionalidades", "Pesquisa para elaboração dos requisitos", "Progress", "2017-12-20", "2018-12-20");
		TaskDTO t2 = new TaskDTO("Reunião com o cliente", "Discussão para levantamento de requisitos", "Done", "2017-12-20", "2018-12-20");
		TaskDTO t3 = new TaskDTO("Implementação e teste da classe A", "Classe de código do back-end", "Progress", "2017-12-20", "2018-12-20");
		TaskDTO t4 = new TaskDTO("Implementação e teste da classe B", "Classe de código do front-end", "Progress", "2017-12-20", "2018-12-20");

		try {
			t1 = activityService.addTaskForActivity(a1.getActivityId(), t1);
			t2 = activityService.addTaskForActivity(a1.getActivityId(), t2);
			t3 = activityService.addTaskForActivity(a2.getActivityId(), t3);
			t4 = activityService.addTaskForActivity(a3.getActivityId(), t4);
		} catch (ActivityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			t1 = taskService.setAccountableForTask(t1.getTaskId(), k4.getkUserId());
			t2 = taskService.setAccountableForTask(t2.getTaskId(), k3.getkUserId());
			t3 = taskService.setAccountableForTask(t3.getTaskId(), k2.getkUserId());
			t4 = taskService.setAccountableForTask(t4.getTaskId(), k1.getkUserId());
		} catch (TaskNotFoundException | KowalskiUserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*******************************************************************************
		 * Time records
		 *******************************************************************************/
		TimeRecordDTO tr1 = new TimeRecordDTO(k1.getkUserId(), t1.getTaskId(), "2017-12-15", "08:00", "Comment 1");
		TimeRecordDTO tr2 = new TimeRecordDTO(k2.getkUserId(), t2.getTaskId(), "2017-12-16", "08:00", "Comment 2");
		TimeRecordDTO tr3 = new TimeRecordDTO(k3.getkUserId(), t3.getTaskId(), "2017-12-17", "08:00", "Comment 3");
		TimeRecordDTO tr4 = new TimeRecordDTO(k4.getkUserId(), t3.getTaskId(), "2017-12-18", "02:00", "Comment 4");
		TimeRecordDTO tr4a = new TimeRecordDTO(k4.getkUserId(), t4.getTaskId(), "2017-12-18", "06:00", "Comment 4a");

		TimeRecordDTO tr5 = new TimeRecordDTO(k1.getkUserId(), t1.getTaskId(), "2017-12-19", "08:00", "Comment 5");
		TimeRecordDTO tr6 = new TimeRecordDTO(k2.getkUserId(), t2.getTaskId(), "2017-12-20", "08:00", "Comment 6");
		TimeRecordDTO tr7 = new TimeRecordDTO(k3.getkUserId(), t3.getTaskId(), "2017-12-21", "08:00", "Comment 7");
		TimeRecordDTO tr8 = new TimeRecordDTO(k4.getkUserId(), t4.getTaskId(), "2017-12-22", "08:00", "Comment 8");

		try {
			tr1 = trService.addTimeRecord(tr1);
			tr2 = trService.addTimeRecord(tr2);
			tr3 = trService.addTimeRecord(tr3);
			tr4 = trService.addTimeRecord(tr4);
			tr4a = trService.addTimeRecord(tr4a);
			tr5 = trService.addTimeRecord(tr5);
			tr6 = trService.addTimeRecord(tr6);
			tr7 = trService.addTimeRecord(tr7);
			tr8 = trService.addTimeRecord(tr8);
		} catch (KowalskiUserNotFoundException | TaskNotFoundException | InvalidTimeRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
