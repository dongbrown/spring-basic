package com.bs.spring.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;

import com.bs.spring.member.model.dto.Member;

public interface MemberDao {


	Member selectMemberById(SqlSessionTemplate session, String userId);

	int insertMember(SqlSessionTemplate session, Member m);
	
}
