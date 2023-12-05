package com.example.notificationtools.Test;

import com.example.notificationtools.NotificationToolsApplication;
import com.example.notificationtools.config.TaskConfig;
import com.example.notificationtools.domain.entity.TaskEntity;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskConfigTest {
	@Test
	public void testAddTest() throws Exception {
		TaskEntity task = new TaskEntity();
		task.setCrontabRule("0/1 * * ? * ?");
		TaskConfig.addTask(task);
		try {
			Thread.sleep(10000); // 等待 10 秒
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
