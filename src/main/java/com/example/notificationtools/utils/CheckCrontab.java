package com.example.notificationtools.utils;

import cn.hutool.core.date.DateUtil;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckCrontab {

	public static boolean isValidCronExpression(String crontab) {
		return CronExpression.isValidExpression(crontab);
	}

	public static boolean isFrequencyGreaterThan10Minutes(String crontab) {
		try {
			CronExpression cronExpression = new CronExpression(crontab);
			Date now = DateUtil.date();
			long nextExecutionTime = cronExpression.getNextValidTimeAfter(now).getTime();
			long differenceInMinutes = (nextExecutionTime - now.getTime()) / (60 * 1000);
			return differenceInMinutes > 10;
		} catch (Exception e) {
			return false;
		}
	}
}
