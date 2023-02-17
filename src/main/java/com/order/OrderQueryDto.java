package com.order;

import java.util.Date;

import com.common.CommonConstant;

import lombok.Data;

@Data
public class OrderQueryDto {
	private Long id;
	private String productName;
	private Date purchasedDate;
	private int page = CommonConstant.DEFAULT_PAGE;
	private int size = CommonConstant.DEFAULT_SIZE;

}
