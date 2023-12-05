package com.example.notificationtools.config;

import cn.hutool.core.date.DateUtil;
import com.example.notificationtools.domain.entity.TaskEntity;
import com.example.notificationtools.service.TaskService;
import com.example.notificationtools.service.impl.TaskServiceImpl;
import com.example.notificationtools.utils.SendMessage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@Log
@EnableScheduling
public class TaskConfig implements SchedulingConfigurer {

	private static final String[] messages = {"快去喝水!", "快去提肛！"};
	private static final String[] crontab = {"0 0 8/2 * * ? ", "0 0 8/3 * * ? "};

	@Autowired
	private TaskServiceImpl taskService;

	private static ThreadPoolTaskScheduler taskScheduler = null;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(50);
		taskScheduler.setThreadNamePrefix("-notificationtools-");
		taskScheduler.initialize();
		taskRegistrar.setTaskScheduler(taskScheduler);
		taskScheduler.getScheduledThreadPoolExecutor().getQueue().clear();
		List<TaskEntity> list = taskService.list();
		for (TaskEntity task : list) {
			taskRegistrar.addCronTask(() -> {
				try {
					addTask(task);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}, task.getCrontabRule());
		}
	}

	public static void addTask(TaskEntity task) throws Exception {
		taskScheduler.schedule(() -> {
			try {
				log.info("定时执行，时间" + DateUtil.now() + "，内容" + messages[task.getBelongId()]);
				SendMessage.sendMessage(task.getTaskKey(), messages[task.getBelongId()]);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}, new CronTrigger(task.getCrontabRule()));
	}

	public static int getLength(){
		return messages.length;
	}

	public static String getCron(int id){
		return crontab[id];
	}


}
