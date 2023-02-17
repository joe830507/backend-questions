package com.tests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;

import com.common.ApiResult;
import com.common.BackendQuestionsApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.Member;
import com.member.MemberRepository;
import com.order.MemberStatisticVo;
import com.order.NewOrderDto;
import com.order.OrderDisplayDto;

@SpringBootTest(classes = BackendQuestionsApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderTests {

	private String orderPath = "/orders";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MemberRepository memberRepository;

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	@Order(2)
	void buy_one_product() throws JsonProcessingException, Exception {
		List<Member> members = memberRepository.findAll();
		Member member = members.stream().findAny().get();
		NewOrderDto newOrder = new NewOrderDto(1l, member.getId(), 5);
		mockMvc.perform(post(URI.create(orderPath)).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(newOrder))).andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(ApiResult.ok("new order created"))));
	}

	@Test
	@Order(1)
	void query_order_by_id() throws JsonProcessingException, Exception {
		List<OrderDisplayDto> list = first_page_with_one_record();
		Page<OrderDisplayDto> pageOrders = new PageImpl<>(list, PageRequest.of(0, 5), 1);
		mockMvc.perform(requestOrders(1l, null, null)).andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(ApiResult.ok(pageOrders))));
	}

	private List<OrderDisplayDto> first_page_with_one_record() throws ParseException {
		List<OrderDisplayDto> memberOrders = new ArrayList<>();
		memberOrders.add(new OrderDisplayDto(1l, 1l, 2l, 1, stringToDate("2021-01-12")));
		return memberOrders;
	}

	private Date stringToDate(String string) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.parse(string);
	}

	private RequestBuilder requestOrders(Long id, String productName, Date purchasedDate) {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(orderPath);
		if (id != null) {
			builder.param("id", String.valueOf(id));
		}
		if (StringUtils.hasText(productName)) {
			builder.param("productName", productName);
		}
		if (purchasedDate != null) {
			builder.param("purchasedDate", toStringDate(purchasedDate));
		}
		return builder;
	}

	private String toStringDate(Date purchasedDate) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String strDate = dateFormat.format(purchasedDate);
		return strDate;
	}

	@Test
	@Order(3)
	void get_members_order_greater_than_1() throws JsonProcessingException, Exception {
		MemberStatisticVo vo = new MemberStatisticVo(1l, "John", 3l);
		mockMvc.perform(getGreaterThanNRequest(1l)).andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(ApiResult.ok(Arrays.asList(vo)))));
	}

	private RequestBuilder getGreaterThanNRequest(long n) {
		return MockMvcRequestBuilders.get(orderPath + "/" + n);
	}
}
