package com.tech.boot.myclient.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tech.boot.myclient.config.SchedulerProperties;

@Component
//@EnableConfigurationProperties(SchedulerProperties.class)
@EnableScheduling
public class MyScheduler {

	private static Logger LOGGER=LoggerFactory.getLogger(MyScheduler.class);
	private final SchedulerProperties schedulerProps;

	@Autowired
	public MyScheduler(SchedulerProperties schedulerProps) {
		this.schedulerProps = schedulerProps;
	}
	
	@Scheduled(cron="${myclient.scheduler.cron}",zone="${myclient.scheduler.zone}")
	public void doMyScheduledActivity()
	{
		// do the activity here
		LOGGER.info("in scheduler at timezone {}"+schedulerProps.getZone());
	}

}
