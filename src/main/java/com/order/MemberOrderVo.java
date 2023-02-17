package com.order;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberOrderVo {
	private Long id;
	private Long product_id;
	private Long account_id;
	private Integer purchased_count;
	private Date purchased_date;
}
