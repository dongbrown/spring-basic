package com.bs.spring.schedule.model.dao;

import org.mybatis.spring.SqlSessionTemplate;

import com.bs.spring.schedule.model.dto.Schedule;

public interface ScheduleDao {
	
	int insertSchedule(SqlSessionTemplate session, Schedule schedule);

}
