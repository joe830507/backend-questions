package com.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.helloworld", "com.member", "com.order", "com.product" })
@EnableJpaRepositories(basePackages = { "com.member", "com.order", "com.product" })
@EntityScan(basePackages = { "com.member", "com.order", "com.product" })
public class BackendQuestionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendQuestionsApplication.class, args);
	}

}
