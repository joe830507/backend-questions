package com.member;

import com.common.CommonConstant;

import lombok.Data;

@Data
public class MemberQueryDto {
	private int page = CommonConstant.DEFAULT_PAGE;
	private int size = CommonConstant.DEFAULT_SIZE;
}
