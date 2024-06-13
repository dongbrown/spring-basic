package com.bs.spring.demo.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.bs.spring.demo.model.dto.Demo;

@Repository
public class DemoDaoImpl implements DemoDao {

	@Override
	public int insertDemo(SqlSessionTemplate session, Demo demo) {
		return session.insert("demo.insertDemo", demo);
	}

	@Override
	public int updateDemo(SqlSession session, Demo demo) {
		return session.update("demo.updateDemo", demo);
	}

	@Override
	public List<Demo> selectDemo(SqlSession session, Map<String, Integer> param) {
		RowBounds rb = new RowBounds((param.get("cPage") - 1) * param.get("numPerpage"), param.get("numPerpage"));
		return session.selectList("demo.selectDemoAll", null, rb);
	}

	@Override
	public int selectDemoCount(SqlSession session) {
		return session.selectOne("demo.selectDemoCount");
	}
	
	@Override
	public Demo selectDemoByNo(SqlSession session, int no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteDemo(SqlSessionTemplate session, int devNo) {
		return session.delete("demo.deleteDemo", devNo);
	}

	

}
