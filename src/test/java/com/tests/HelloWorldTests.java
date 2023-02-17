package com.tests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.common.BackendQuestionsApplication;

@SpringBootTest(classes = BackendQuestionsApplication.class)
@AutoConfigureMockMvc
class HelloWorldTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void test_hello_world() throws Exception {
		mockMvc.perform(request()).andExpect(status().isOk()).andExpect(content().string("hello world"));
	}

	private RequestBuilder request() {
		return MockMvcRequestBuilders.get("/hello_world");
	}

}
