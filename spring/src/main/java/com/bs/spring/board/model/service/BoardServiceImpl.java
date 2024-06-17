package com.bs.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bs.spring.board.model.dao.BoardDao;
import com.bs.spring.board.model.dto.Board;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private final SqlSession session;
	private final BoardDao dao;
	
	
	@Override
	public List<Board> selectBoardList(Map<String, Integer> param) {
		return dao.selectBoardAll(session, param);
	}

	@Override
	public int selectBoardCount() {
		return dao.selectBoardCount(session);
	}

	@Override
	public int insertBoard(Board board) {
		return dao.insertBoard(session, board);
	}

	@Override
	public Board selectBoardByNo(int boardNo) {
		return dao.selectBoardByNo(session, boardNo);
	}

	@Override
	public int updateBoardReadCount(int boardNo) {
		return dao.updateBoardReadCount(session, boardNo);
	}

	@Override
	public int deleteBoard(int boardNo) {
		return dao.deleteBoard(session, boardNo);
	}

//	public Map<String, Object> selectBoardList(Map<String,Integer> result){
//		Map<String,Object> resultMap = new HashMap());
//		resultMap.put("boards", dao.selectBoardAll(session, page));
//		resultMap.put("boardCount", dao.selectBoardCount(session);
//	}
}
