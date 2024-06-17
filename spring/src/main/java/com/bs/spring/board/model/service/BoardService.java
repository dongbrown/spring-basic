package com.bs.spring.board.model.service;

import java.util.List;
import java.util.Map;


import com.bs.spring.board.model.dto.Board;

public interface BoardService {

	List<Board> selectBoardList(Map<String, Integer> param);

	Board selectBoardByNo(int boardNo);
	
	int selectBoardCount();

	int insertBoard(Board b);
	int updateBoardReadCount(int boardNo);

	int deleteBoard(int boardNo);

}
