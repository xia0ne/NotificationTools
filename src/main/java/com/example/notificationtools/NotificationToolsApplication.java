package com.example.notificationtools;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.example.notificationtools.mapper")
public class NotificationToolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationToolsApplication.class, args);
	}

}

