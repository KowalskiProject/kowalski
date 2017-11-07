package com.app.kowalski.activity;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ActivityLoader implements ApplicationListener<ContextRefreshedEvent> {

	private ActivityRepository activityRepository;

	private Logger log = Logger.getLogger(ActivityLoader.class);

	@Autowired
	public void setActivityRepository(ActivityRepository activityRepository) {
		this.activityRepository = activityRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		Activity activity1 = new Activity();
		activity1.setName("Inicio do projeto");
		activity1.setDescription("Fase inicial do projeto");
		activityRepository.save(activity1);

		log.info("Activity " + activity1.getName() + " added successfully");
	}

}
