package com.example.notificationtools.Test;

import com.example.notificationtools.NotificationToolsApplication;
import com.example.notificationtools.config.TaskConfig;
import com.example.notificationtools.domain.entity.TaskEntity;
import com.example.notificationtools.service.ScheduleService;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskConfigTest {

	@Resource
	private ScheduleService scheduleService;

	@Test
	public void testAddTest() throws Exception {
		TaskEntity task = new TaskEntity();
		task.setCrontabRule("0/1 * * ? * ?");
		scheduleService.addTask(task);
		try {
			Thread.sleep(10000); // 等待 10 秒
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
