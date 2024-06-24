package com.bs.spring.security.service;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bs.spring.member.model.dao.MemberDao;
import com.bs.spring.member.model.dto.Member;

import lombok.AllArgsConstructor;

//시큐리티가 이용하는 인증처리 클래스
//UserDetailService 인터페이스 구현!
// -> loadUserByUsername()메소드를 재정의
// public userDetails loadUserByUsername(String username) 
//			throws UsernameNotFoundException
// -> 매개변수 username : 클라이언트가 입력한 아이디 값 (시큐리티가 알아서 줌)
// -> 반환값 UserDetails : UserDetails 인터페이스를 구현한 객체
// Member DTO객체가 구현

@AllArgsConstructor //-> security-context에 bean 등록하기 위해
public class SecurityLoginProvider implements UserDetailsService{

	private final MemberDao dao;
	private final SqlSessionTemplate session;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member loginMember = dao.selectMemberById(session, username);
		// Member DTO -> implements UserDetails
		return loginMember;
	}

}
