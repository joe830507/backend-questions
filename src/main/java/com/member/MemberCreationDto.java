package com.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreationDto {
	private Long id;
	private String account;
	private String password;

	public Member toMember() {
		return new Member(id, account, password);
	}
}
