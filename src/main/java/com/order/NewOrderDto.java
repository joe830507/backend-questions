package com.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderDto {
	private Long productId;
	private Long accountId;
	private Integer purchasedCount;

	public MemberOrder toOrder() {
		return new MemberOrder(null, productId, accountId, purchasedCount, null);
	}
}
