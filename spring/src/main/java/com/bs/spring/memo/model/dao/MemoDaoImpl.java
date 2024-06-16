package com.bs.spring.memo.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.bs.spring.memo.model.dto.Memo;

@Repository
public class MemoDaoImpl implements MemoDao {

	@Override
	public List<Memo> selectMemoAll(SqlSessionTemplate session, Map<String, Integer> param) {
		RowBounds rb = new RowBounds((param.get("cPage") - 1) * param.get("numPerpage"), param.get("numPerpage"));
		return session.selectList("memo.selectMemoAll", null, rb);
	}

	@Override
	public int selectMemoCount(SqlSessionTemplate session) {
		return session.selectOne("memo.selectMemoCount");
	}

	@Override
	public int insertMemo(SqlSessionTemplate session, Memo memo) {
		return session.insert("memo.insertMemo", memo);
	}

	@Override
	public int deleteMemo(SqlSessionTemplate session, int memoNo) {
		return session.delete("memo.deleteMemo", memoNo);
	}

}
