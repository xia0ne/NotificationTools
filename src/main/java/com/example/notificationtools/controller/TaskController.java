package com.example.notificationtools.controller;

import cn.hutool.core.date.DateUtil;
import com.example.notificationtools.config.TaskConfig;
import com.example.notificationtools.domain.entity.TaskEntity;
import com.example.notificationtools.service.impl.TaskServiceImpl;
import com.example.notificationtools.utils.ResponseResult;
import com.example.notificationtools.utils.SendMessage;
import jdk.jshell.execution.Util;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Log
public class TaskController {

	@Autowired
	TaskServiceImpl taskService;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseResult<String> test(){
		return ResponseResult.success("test");
	}
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<TaskEntity> handleAdd(@RequestParam("key") String key,
																							@RequestParam("task") int task) throws Exception {
		if(task > TaskConfig.getLength()){
			return ResponseResult.fail("not found the tasks!");
		}
		TaskEntity success = taskService.addToDataBases(task, key, TaskConfig.getCron(task));
		if(success == null){
			return ResponseResult.fail("Add failed, it is possible that this task has been added before");
		}
		new Thread(() -> {
			try {
				SendMessage.sendMessage(key, DateUtil.now() + " 当你收到这条消息，代表添加成功");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).start();
		TaskConfig.addTask(success);
		return ResponseResult.success(success);
	}
}
