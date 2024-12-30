package com.example.notificationtools.service.impl;

import com.example.notificationtools.config.TaskConfig;
import com.example.notificationtools.service.RedisService;
import com.example.notificationtools.service.ScheduleService;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
@Log
public class RedisServiceImpl implements RedisService {
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	/*
	-1 custom
	0 drink
	1 tig
	 */
	@Override
	public boolean setCount(int taskId) {
		try {
			redisTemplate.opsForValue().increment("count:" + taskId);
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<HashMap<Integer, Object>> getCount() {
		try {
			List<Integer> taskId = TaskConfig.getTaskId();
			List<String> keys = taskId.stream().map(id -> "count:" + id).toList();
			List<Object> counts = redisTemplate.opsForValue().multiGet(keys);
			List<HashMap<Integer, Object>> result = new ArrayList<>();
			for (int i = 0; i < taskId.size(); i++) {
				if (counts.get(i) != null) {
					result.add(new HashMap<>(Map.of(taskId.get(i), counts.get(i))));
				}
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}


	@Override
	public boolean saveLogs(String logs) {
		log.info("setLog " + logs);
		try {
			redisTemplate.opsForList().rightPush("logs", logs);
			Long size = redisTemplate.opsForList().size("logs");
			if (size == null) {
				return false;
			}
			if (size > 10) {
				redisTemplate.opsForList().trim("log", size - 10, size);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Object> getLogs(int index, int offset) {
		return redisTemplate.opsForList().range("logs", index, Math.min(index + offset - 1, index + 9)).stream()
						.map(Object::toString)
						.collect(Collectors.toList());
	}
}
