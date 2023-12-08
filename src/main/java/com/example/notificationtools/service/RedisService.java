package com.example.notificationtools.service;

import cn.hutool.core.lang.hash.Hash;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RedisService {
	public boolean setCount(int taskId);

	public List<HashMap<Integer, Object>> getCount();

	public boolean setLog(String logs);

	public List<Object> getLogs(int index, int offset);
}
