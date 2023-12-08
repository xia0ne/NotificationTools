package com.example.notificationtools.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.notificationtools.domain.entity.TaskEntity;
import com.example.notificationtools.service.ScheduleService;
import com.example.notificationtools.utils.SendMessage;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class ScheduleServiceImpl implements ScheduleService {

	private final String[] messages = {"快去喝水!", "快去提肛！"};
	private final String[] crontab = {"0 0 8/2 * * ? ", "0 0 8/3 * * ? "};

	@Resource
	private ThreadPoolTaskScheduler taskScheduler;
	@Resource
	private RedisServiceImpl redisService;

	@Override
	public void setTaskScheduler(TaskEntity task) {
		try {
			redisService.setLog(DateUtil.now() + " : " + task.getId());
			log.info("定时执行，时间" + DateUtil.now() + "，内容" + messages[task.getBelongId()]);
			SendMessage.sendMessage(task.getTaskKey(), messages[task.getBelongId()]);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addTask(TaskEntity task) {
		taskScheduler.schedule(() -> {
			setTaskScheduler(task);
		}, new CronTrigger(task.getCrontabRule()));
	}

	@Override
	public int getLength() {
		return messages.length;
	}

	@Override
	public String getCron(int id) {
		return crontab[id];
	}

	@Override
	public String getMessage(int id) {
		return messages[id];
	}
}
