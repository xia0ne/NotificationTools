package com.example.notificationtools.service;

import com.example.notificationtools.domain.entity.TaskEntity;

import java.util.List;

public interface ScheduleService {
	void setTaskScheduler(TaskEntity task);

	void addTask(TaskEntity task);

	int getLength();

	String getCron(int id);

	String getMessage(int id);
	
}
