package com.bs.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bs.spring.member.model.dao.MemberDao;
import com.bs.spring.member.model.dto.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

//	@Autowired
	private final SqlSessionTemplate session;
	private final MemberDao dao;
	
	
	@Override
	public Member selectMemberById(String userId) {
		return dao.selectMemberById(session, userId);
	}

	//DML -> 트렌젝션 처리 autocommit 해줌
	// -> 롤백 처리 시점만 설정해주면됨(나중에 AOP 배우고)
	@Override
	public int insertMember(Member m) {
		return dao.insertMember(session, m);
	}
	


}
