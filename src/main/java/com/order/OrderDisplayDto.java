package com.order;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDisplayDto {
	private Long id;
	private Long productId;
	private Long accountId;
	private Integer purchasedCount;
	private Date purchasedDate;
}
