package com.example.notificationtools.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.notificationtools.domain.entity.TaskEntity;

import java.util.List;

public interface TaskService extends IService<TaskEntity> {
	TaskEntity addToDataBases(int belong_id, String taskKey, String crontab_rule, String message);
}
