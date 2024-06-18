package com.bs.spring.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.bs.spring.board.model.dto.Attachment;
import com.bs.spring.board.model.dto.Board;

public interface BoardDao {

	List<Board> selectBoardAll(SqlSession session, Map<String, Integer> param);
	Board selectBoardByNo(SqlSession session, int boardNo);
	int selectBoardCount(SqlSession session);
	
	int insertBoard(SqlSession session, Board board);
	int updateBoardReadCount(SqlSession session,int boardNo);
	int deleteBoard(SqlSession session, int boardNo);
	int insertAttachment(SqlSession session, Attachment attach);
}
