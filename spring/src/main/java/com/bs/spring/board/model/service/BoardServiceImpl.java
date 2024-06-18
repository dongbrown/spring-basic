package com.bs.spring.board.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bs.spring.board.controller.BoardController;
import com.bs.spring.board.model.dao.BoardDao;
import com.bs.spring.board.model.dto.Board;
import com.bs.spring.common.exception.BadAuthenticationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
//	@Transactional //트랜잭션 처리 @Transactional("") ""안에 속성 설정 가능 
	public int insertBoard(Board b) {
		int result = 0;
		try {
			result = dao.insertBoard(session, b); // boarNo 없는 상태
			//<selectKey keyProperty="boardNo" order="AFTER" resultType="_int">로 b에 boarNo 반환
	
			log.debug("생성된 게시글번호 : " +  b.getBoardNo());
			
			if(result > 0 && b.getFiles().size() > 0) {
				b.getFiles().stream().forEach(attachment->{
					int boardNo = b.getBoardNo();
					//생성된 게시글 번호 넣어주기
					attachment.setBoardNo(boardNo);
					int result1 = dao.insertAttachment(session, attachment);
	//				if(result1 == 0) //저장이 안되면 rollback (spring이 autocommit하기 때문에 특정 시점 정해줘야함(범위로x))
					if(result1 == 0) {
						throw new BadAuthenticationException("첨부파일 등록 실패");
					}
				});
			}else {
				throw new BadAuthenticationException("게시글 등록 실패");
			}
		}catch(RuntimeException e) {
			throw new BadAuthenticationException("게시글 등록 실패");
		}
		return result;
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
