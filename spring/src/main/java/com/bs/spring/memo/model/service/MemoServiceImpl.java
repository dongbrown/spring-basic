package com.bs.spring.memo.model.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bs.spring.memo.model.dao.MemoDao;
import com.bs.spring.memo.model.dto.Memo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemoServiceImpl implements MemoService {

	@Autowired
	private final SqlSessionTemplate session;
	private final MemoDao dao;
	
	@Override
	public List<Memo> selectMemoList(Map<String, Integer> param) {
		return dao.selectMemoAll(session, param);
	}

	@Override
	public int selectMemoCount() {
		return dao.selectMemoCount(session);
	}

	@Override
	public int insertMemo(Memo memo) {
		return dao.insertMemo(session, memo);
	}

	@Override
	public int deleteMemo(int memoNo) {
		return dao.deleteMemo(session, memoNo);
	}

}
