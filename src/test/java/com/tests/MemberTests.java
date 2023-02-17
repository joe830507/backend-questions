package com.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.common.ApiResult;
import com.common.BackendQuestionsApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.MemberCreationDto;
import com.member.MemberDisplayDto;
import com.member.MemberModifiedDto;

@SpringBootTest(classes = BackendQuestionsApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberTests {

	private String memberPath = "/members";

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	@Order(1)
	void get_one_member_ok() throws Exception {
		MemberDisplayDto dto = new MemberDisplayDto(1l, "John");
		mockMvc.perform(requestOneOrMoreMembers(Optional.of(1l))).andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(ApiResult.ok(dto))));
	}

	private RequestBuilder requestOneOrMoreMembers(Optional<Long> id) {
		return id.isPresent() ? MockMvcRequestBuilders.get(String.format("%s/%d", memberPath, id.get()))
				: MockMvcRequestBuilders.get(memberPath);
	}

	@Test
	@Order(2)
	void member_not_found() throws Exception {
		MvcResult result = mockMvc.perform(requestOneOrMoreMembers(Optional.of(8l))).andExpect(status().isBadRequest())
				.andReturn();
		assertThat(result.getResponse().getContentAsString()).contains("Could not find the member.");
	}

	@Test
	@Order(3)
	void first_page_top_five_members_ok() throws Exception {
		List<MemberDisplayDto> list = first_page_with_five_members_list();
		Page<MemberDisplayDto> pageMembers = new PageImpl<>(list, PageRequest.of(0, 5), 6);
		mockMvc.perform(requestSomePageWithSeveralMembers(0, 5)).andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(ApiResult.ok(pageMembers))));
	}

	private RequestBuilder requestSomePageWithSeveralMembers(int page, int size) {
		return MockMvcRequestBuilders.get(String.format("%s?page=%d&size=%d", memberPath, page, size));
	}

	private List<MemberDisplayDto> first_page_with_five_members_list() {
		List<MemberDisplayDto> members = new ArrayList<>();
		members.add(new MemberDisplayDto(1l, "John"));
		members.add(new MemberDisplayDto(2l, "Mary"));
		members.add(new MemberDisplayDto(3l, "James"));
		members.add(new MemberDisplayDto(4l, "Chris"));
		members.add(new MemberDisplayDto(5l, "Johnson"));
		return members;
	}

	@Test
	@Order(4)
	void second_page_top_three_members_ok() throws Exception {
		List<MemberDisplayDto> list = second_page_with_three_members_list();
		Page<MemberDisplayDto> pageMembers = new PageImpl<>(list, PageRequest.of(1, 3), 6);
		mockMvc.perform(requestSomePageWithSeveralMembers(1, 3)).andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(ApiResult.ok(pageMembers))));
	}

	private List<MemberDisplayDto> second_page_with_three_members_list() {
		List<MemberDisplayDto> members = new ArrayList<>();
		members.add(new MemberDisplayDto(4l, "Chris"));
		members.add(new MemberDisplayDto(5l, "Johnson"));
		members.add(new MemberDisplayDto(6l, "Henry"));
		return members;
	}

	@Test
	@Order(5)
	void register_member_ok() throws JsonProcessingException, Exception {
		MemberCreationDto creationDto = new MemberCreationDto(7l, "Jimmy", "jimmy123");
		mockMvc.perform(post(URI.create(memberPath)).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(creationDto))).andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(ApiResult.ok("new member created"))));
	}

	@Test
	@Order(6)
	void remove_one_row_by_id() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(URI.create(memberPath + "/7")))
				.andExpect(status().isOk()).andReturn();
		assertThat(result.getResponse().getContentAsString()).contains("the memeber was unregistered");
	}

	@Test
	@Order(7)
	void modify_one_member_ok() throws JsonProcessingException, Exception {
		MemberModifiedDto modifiedDto = new MemberModifiedDto(6l, "Henry456");
		mockMvc.perform(put(URI.create(memberPath)).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(modifiedDto))).andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(ApiResult.ok("the member information updated"))));
	}
}
