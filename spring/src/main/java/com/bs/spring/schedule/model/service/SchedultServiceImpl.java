package com.bs.spring.schedule.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bs.spring.schedule.model.dao.ScheduleDao;
import com.bs.spring.schedule.model.dto.Schedule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SchedultServiceImpl implements ScheduleService {

	@Autowired
	private final SqlSessionTemplate session;
	private final ScheduleDao dao;
	
	@Override
	public int insertSchedule(Schedule schedule) {
		return dao.insertSchedule(session, schedule);
	}

}
