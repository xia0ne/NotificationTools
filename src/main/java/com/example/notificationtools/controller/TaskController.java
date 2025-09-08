package com.example.notificationtools.controller;

import cn.hutool.core.date.DateUtil;
import com.example.notificationtools.domain.entity.TaskEntity;
import com.example.notificationtools.service.ScheduleService;
import com.example.notificationtools.service.impl.RedisServiceImpl;
import com.example.notificationtools.service.impl.TaskServiceImpl;
import com.example.notificationtools.utils.CheckCrontab;
import com.example.notificationtools.utils.ResponseResult;
import com.example.notificationtools.utils.SendMessage;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Log
public class
TaskController {

	@Resource
	TaskServiceImpl taskService;
	@Resource
	RedisServiceImpl redisService;

	@Resource
	ScheduleService scheduleService;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseResult<String> test() {
		return ResponseResult.success("test");
	}

	@RequestMapping(value = "/custom", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> handleCustom(@RequestParam("key") String key,
																						 @RequestParam("rule") String rule,
																						 @RequestParam("message") String message) throws Exception {

		if (!CheckCrontab.isValidCronExpression(rule)) {
			return ResponseResult.fail("Incorrect Crontab rules!");
		}
		if (!CheckCrontab.isFrequencyGreaterThan10Minutes(rule)) {
			return ResponseResult.fail("The crontab rule is too frequent, please do not submit tasks with a cycle of less than 10 minutes!");
		}
		TaskEntity exists = taskService.findExisting(-1, key, rule, message);
		if (exists != null) {
			return ResponseResult.fail("Add failed, it is possible that this task has been added before");
		}
		TaskEntity success = taskService.addToDataBases(-1, key, rule, message);
		if (success == null) {
			return ResponseResult.fail("Add failed, it is possible that this task has been added before");
		}
		new Thread(() -> {
			try {
				SendMessage.sendMessage(key, DateUtil.now() + " 当你收到这条消息，代表添加成功");
				redisService.saveLogs(DateUtil.now() + " : " + success.getId());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).start();
		scheduleService.addTask(success);
		redisService.setCount(success.getBelongId());
		return ResponseResult.success("successful!");
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> handleAdd(@RequestParam("key") String key,
																					@RequestParam("task") int task) throws Exception {
		if (task > scheduleService.getLength()) {
			return ResponseResult.fail("not found the tasks!");
		}
		TaskEntity exists = taskService.findExisting(task, key, scheduleService.getCron(task), scheduleService.getMessage(task));
		if (exists != null) {
			return ResponseResult.fail("Add failed, it is possible that this task has been added before");
		}
		TaskEntity success = taskService.addToDataBases(task, key, scheduleService.getCron(task), scheduleService.getMessage(task));
		if (success == null) {
			return ResponseResult.fail("Add failed, it is possible that this task has been added before");
		}
		new Thread(() -> {
			try {
				SendMessage.sendMessage(key, DateUtil.now() + " 当你收到这条消息，代表添加成功");
				redisService.saveLogs(DateUtil.now() + " : " + success.getId());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).start();
		scheduleService.addTask(success);
		redisService.setCount(success.getBelongId());
		return ResponseResult.success("successful!");
	}
}
