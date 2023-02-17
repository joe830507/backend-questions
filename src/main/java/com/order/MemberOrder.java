package com.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "member_order")
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "product_id")
	private Long product_id;
	@Column(name = "account_id")
	private Long account_id;
	@Column(name = "purchased_count")
	private Integer purchased_count;
	@Column(name = "purchased_date")
	private Date purchased_date;

	public OrderDisplayDto toOrderDisplayDto() {
		return new OrderDisplayDto(id, product_id, account_id, purchased_count, purchased_date);
	}
}
