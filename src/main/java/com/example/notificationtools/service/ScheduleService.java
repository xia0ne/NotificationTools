package com.example.notificationtools.service;

import com.example.notificationtools.domain.entity.TaskEntity;

import java.util.List;

public interface ScheduleService {
	public void setTaskScheduler(TaskEntity task);

	public void addTask(TaskEntity task);

	public int getLength();

	public String getCron(int id);

	public String getMessage(int id);
	
}
