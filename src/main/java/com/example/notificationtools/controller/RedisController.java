package com.example.notificationtools.controller;

import com.example.notificationtools.service.impl.RedisServiceImpl;
import com.example.notificationtools.utils.ResponseResult;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/logs")
@Log
public class RedisController {
	@Resource
	RedisServiceImpl redisService;

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult<List<HashMap<Integer, Object>>> getLogCount() {
		List<HashMap<Integer, Object>> result = redisService.getCount();
		if (result == null) {
			return ResponseResult.fail("Failed to obtain the number of tasks");
		} else {
			return ResponseResult.success(result);
		}
	}

	@RequestMapping(value = "/getlogs", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult<List<Object>> getLogs() {
		List<Object> result = redisService.getLogs(0, 9);
		if (result == null) {
			return ResponseResult.fail("Failed to obtain the list of tasks");
		} else {
			return ResponseResult.success(result);
		}
	}
}
