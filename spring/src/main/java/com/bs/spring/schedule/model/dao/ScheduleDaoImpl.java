package com.bs.spring.schedule.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.bs.spring.schedule.model.dto.Schedule;

@Repository
public class ScheduleDaoImpl implements ScheduleDao {

	@Override
	public int insertSchedule(SqlSessionTemplate session, Schedule schedule) {
		return session.insert("schedule.insertSchedule", schedule);
	}

}
