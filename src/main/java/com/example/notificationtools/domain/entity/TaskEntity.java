package com.example.notificationtools.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tasks")
public class TaskEntity {
	@TableId(value="id", type = IdType.AUTO)
	private Integer id;
	@TableField("task_key")
	private String taskKey;
	@TableField("belong_id")
	private Integer belongId;
	@TableField("crontab_rule")
	private String crontabRule;
	@TableField("message")
	private String message;
	@TableField("start_time")
	private Date startTime;
	@TableField("end_time")
	private Date endTime;
}
