package com.bs.spring.memo.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.bs.spring.memo.model.dto.Memo;

public interface MemoDao {

	List<Memo> selectMemoAll(SqlSessionTemplate session, Map<String, Integer> param);

	int selectMemoCount(SqlSessionTemplate session);

	int insertMemo(SqlSessionTemplate session, Memo memo);

	int deleteMemo(SqlSessionTemplate session, int memoNo);

}
