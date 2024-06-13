package com.bs.spring.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.bs.spring.member.model.dto.Member;

@Repository
public class MemberDaoImpl implements MemberDao {

	@Override
	public Member selectMemberById(SqlSessionTemplate session, String userId) {
		return session.selectOne("member.selectMemberById", userId);
	}

	@Override
	public int insertMember(SqlSessionTemplate session, Member m) {
		return session.insert("member.insertMember", m);
	}


}
