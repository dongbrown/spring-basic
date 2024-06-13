package com.bs.spring.demo.model.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bs.spring.demo.model.dao.DemoDao;
import com.bs.spring.demo.model.dto.Demo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DemoServiceImpl implements DemoService {
	
	@Autowired
	private final SqlSessionTemplate session;
	private final DemoDao dao;

//	public DemoServiceImpl(DemoDao dao) {
//		this.dao = dao;
//	}
	
	@Override
	public int insertDemo(Demo d) {
		return dao.insertDemo(session, d);
	}

	@Override
	public int updateDemo(Demo d) {
		// TODO Auto-generated method stub
		return dao.updateDemo(session, d);
	}

	@Override
	public List<Demo> selectDemoList(Map<String, Integer> param) {
		return dao.selectDemo(session, param);
	}

	@Override
	public Demo selectDemoByNo(int no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectDemoCount() {
		return dao.selectDemoCount(session);
	}

	@Override
	public int deleteDemo(int devNo) {
		return dao.deleteDemo(session, devNo);
	}



}
