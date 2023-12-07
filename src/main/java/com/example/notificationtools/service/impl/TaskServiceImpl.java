package com.example.notificationtools.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.notificationtools.domain.entity.TaskEntity;
import com.example.notificationtools.mapper.TaskMapper;
import com.example.notificationtools.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, TaskEntity> implements TaskService {

	@Override
	public TaskEntity addToDataBases(int belong_id, String taskKey, String crontab_rule, String message) {
		QueryWrapper<TaskEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("belong_id", belong_id)
						.eq("crontab_rule", crontab_rule)
						.eq("task_key", taskKey)
						.eq("message", message);
		List<TaskEntity> tasks = list(queryWrapper);
		if(!tasks.isEmpty()){
			return null;
		}
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setCrontabRule(crontab_rule);
		taskEntity.setTaskKey(taskKey);
		taskEntity.setBelongId(belong_id);
		taskEntity.setStartTime(new Date());
		taskEntity.setEndTime(new Date());
		taskEntity.setMessage(message);
		if(save(taskEntity)){
			return taskEntity;
		}else{
			return null;
		}
	}
}
