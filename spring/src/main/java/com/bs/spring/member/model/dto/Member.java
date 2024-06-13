package com.bs.spring.member.model.dto;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
	@NotEmpty(message = "아이디는 반드시 입력해야합니다") //@NotNull + 공백 확인
	@Size(min = 4, message = "아이디는 4글자 이상 입력하세요")
	private String userId;
//	@Size(min = 8, message = "
	@Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*()]).{8,}",
			message = "비밀번호는 소문자, 대문자, 숫자, 특수기호를 포함한 8글자 이상으로 작성하세요")
	private String password;
	private String userName;
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
}
