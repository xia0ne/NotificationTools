package com.example.notificationtools.utils;

import cn.hutool.core.date.DateUtil;
import org.quartz.CronExpression;

import java.util.Date;

public class CheckCrontab {

	public static boolean isValidCronExpression(String crontab) {
		return CronExpression.isValidExpression(crontab);
	}

	public static boolean isFrequencyGreaterThan10Minutes(String crontab) {
		try {
			CronExpression cronExpression = new CronExpression(crontab);
			Date now = DateUtil.date();
			Date first = cronExpression.getNextValidTimeAfter(now);
			if (first == null) {
				return false;
			}
			Date second = cronExpression.getNextValidTimeAfter(first);
			if (second == null) {
				return false;
			}
			long intervalInMinutes = (second.getTime() - first.getTime()) / (60 * 1000);
			return intervalInMinutes >= 10;
		} catch (Exception e) {
			return false;
		}
	}
}
