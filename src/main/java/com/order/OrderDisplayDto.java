package com.order;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date purchasedDate;
}
