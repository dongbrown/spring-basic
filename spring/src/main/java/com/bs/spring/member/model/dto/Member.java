package com.bs.spring.member.model.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member implements UserDetails{
	@NotEmpty(message = "아이디는 반드시 입력해야합니다") //@NotNull + 공백 확인
	@Size(min = 4, message = "아이디는 4글자 이상 입력하세요")
	private String userId;
//	@Size(min = 8, message = "
	@Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*()]).{8,}",
			message = "비밀번호는 소문자, 대문자, 숫자, 특수기호를 포함한 8글자 이상으로 작성하세요")
	private String password;
	private String name;
	private String gender;
	@Min(value = 19, message = "19세 이상")
	@Max(value = 100, message = "100세 이하")
	private int age;
	@Email(message = "이메일 형식에 맞지 않습니다")
	private String email;
	private String phone;
	private String address;
	private String[] hobby;
	private Date enrollDate;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//사용자에게 부여된 권한을 확인(다수의 권한을 가질 수 있기 때문에 -> Collection(List, Map, Set)
		List<GrantedAuthority> auth = new ArrayList<>();
		//simplegrantedAuthority클래스를 이용해서 권한객체를 생성
		if(userId.equals("admin")) {
			auth.add(new SimpleGrantedAuthority("admin")); //admin이라는 권한 부여
		}
		
		auth.add(new SimpleGrantedAuthority("user"));
		return auth;
	}
	@Override
	public String getUsername() {
		//아이디를 반환하는 메소드
		return this.userId;
	}
	@Override
	public boolean isAccountNonExpired() {
		//인증 유효기간에 대한 분기 처리 
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		//로그인 실패 5번 (실패 시 마다 count해서 session에 저장)-> 잠구기(false로)
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
