package com.example.notificationtools.Test;

import cn.hutool.core.date.DateUtil;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.DateUtils;

import java.util.Date;

@Log
@Component
public class CronTest {
	@Scheduled(cron = "0/1 * * ? * ?")
	public void cron() {
		log.info("定时执行，时间 " + DateUtil.now());
	}
}
