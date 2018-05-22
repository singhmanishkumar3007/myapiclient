package com.tech.boot.myclient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class SchedulerThreadConfig implements SchedulingConfigurer {
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(1);
		taskScheduler.initialize();
		taskRegistrar.setTaskScheduler(taskScheduler);

	}

	// @Bean
	// public TaskScheduler taskScheduler() {
	// return new ConcurrentTaskScheduler(); // single threaded by default
	// }
}
