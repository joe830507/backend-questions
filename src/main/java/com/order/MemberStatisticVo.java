package com.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberStatisticVo {
	private Long id;
	private String account;
	private Long count;
}
