package com.bs.spring.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.bs.spring.board.model.dto.Attachment;
import com.bs.spring.board.model.dto.Board;
@Repository
public class BoardDaoImpl implements BoardDao {

	@Override
	public List<Board> selectBoardAll(SqlSession session, Map<String, Integer> param) {
		RowBounds rb = new RowBounds((param.get("cPage") - 1) * param.get("numPerpage"), param.get("numPerpage"));
		return session.selectList("board.boardList", null, rb);
	}

	@Override
	public int selectBoardCount(SqlSession session) {
		return session.selectOne("board.selectBoardCount");
	}

	@Override
	public int insertBoard(SqlSession session, Board board) {
		return session.insert("board.boardInsert", board);
	}

	@Override
	public Board selectBoardByNo(SqlSession session, int boardNo) {
		return session.selectOne("board.boardByNo", boardNo);
	}

	@Override
	public int updateBoardReadCount(SqlSession session, int boardNo) {
		return session.update("board.boardUpdate", boardNo);
	}

	@Override
	public int deleteBoard(SqlSession session, int boardNo) {
		return session.delete("board.boardDelete", boardNo);
	}

	@Override
	public int insertAttachment(SqlSession session, Attachment attach) {
		return session.insert("board.insertAttach", attach);
	}

}
