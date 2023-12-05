package com.example.notificationtools.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.notificationtools.domain.entity.TaskEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskMapper extends BaseMapper<TaskEntity> {
}
