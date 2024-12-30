package com.example.notificationtools.service;

import cn.hutool.core.lang.hash.Hash;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RedisService {
	boolean setCount(int taskId);

	List<HashMap<Integer, Object>> getCount();

	boolean saveLogs(String logs);

	List<Object> getLogs(int index, int offset);
}
