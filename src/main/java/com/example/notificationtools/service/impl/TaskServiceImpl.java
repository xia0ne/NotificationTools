package com.example.notificationtools.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.notificationtools.domain.entity.TaskEntity;
import com.example.notificationtools.mapper.TaskMapper;
import com.example.notificationtools.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, TaskEntity> implements TaskService {
}
