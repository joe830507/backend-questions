package com.order;

import java.util.List;

import org.springframework.data.domain.Page;

public interface IOrderService {
	void buyOneProduct(NewOrderDto newOrder) throws ProductNotFoundException;

	Page<OrderDisplayDto> queryOrders(OrderQueryDto queryDto);

	List<MemberStatisticVo> getMemberOfnumOfOrderGreaterThanN(Long n);
}
