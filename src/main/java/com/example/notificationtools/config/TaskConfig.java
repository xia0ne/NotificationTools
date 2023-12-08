package com.example.notificationtools.config;

import cn.hutool.core.date.DateUtil;
import com.example.notificationtools.domain.entity.TaskEntity;
import com.example.notificationtools.service.TaskService;
import com.example.notificationtools.service.impl.RedisServiceImpl;
import com.example.notificationtools.service.impl.ScheduleServiceImpl;
import com.example.notificationtools.service.impl.TaskServiceImpl;
import com.example.notificationtools.utils.SendMessage;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@Log
@EnableScheduling
public class TaskConfig implements SchedulingConfigurer {

	@Resource
	private TaskServiceImpl taskService;
	@Resource
	private ScheduleServiceImpl scheduleService;
	@Resource
	private RedisServiceImpl redisService;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(50);
		taskScheduler.setThreadNamePrefix("-notificationtools-");
		taskScheduler.initialize();
		taskRegistrar.setTaskScheduler(taskScheduler);
		taskScheduler.getScheduledThreadPoolExecutor().getQueue().clear();
		List<TaskEntity> list = taskService.list();
		for (TaskEntity task : list) {
			taskRegistrar.addCronTask(() -> {
				try {
					scheduleService.addTask(task);
					redisService.setCount(task.getBelongId());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}, task.getCrontabRule());
		}
	}

	public static List<Integer> getTaskId() {
		return List.of(-1, 0, 1);
	}
}
