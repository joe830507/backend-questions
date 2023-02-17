package com.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.ApiResult;
import com.member.MemberNotFoundException;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private IOrderService orderService;

	@PostMapping
	public ResponseEntity<ApiResult> buyOneProduct(@RequestBody NewOrderDto newOrder) {
		try {
			orderService.buyOneProduct(newOrder);
			return ResponseEntity.ok(ApiResult.ok("new order created"));
		} catch (MemberNotFoundException e) {
			return ResponseEntity.badRequest().body(ApiResult.error(e.getMessage()));
		}
	}

	@GetMapping
	public ResponseEntity<ApiResult> queryOrder(OrderQueryDto query) {
		Page<OrderDisplayDto> result = orderService.queryOrders(query);
		return ResponseEntity.ok(ApiResult.ok(result));
	}

	@GetMapping("/{n}")
	public ResponseEntity<ApiResult> getMemberOfnumOfOrderGreaterThanN(@PathVariable("n") Long n) {
		List<MemberStatisticVo> vos = orderService.getMemberOfnumOfOrderGreaterThanN(n);
		return ResponseEntity.ok(ApiResult.ok(vos));
	}

}
