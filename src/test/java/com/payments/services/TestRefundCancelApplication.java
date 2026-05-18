package com.payments.services;

import org.springframework.boot.SpringApplication;

public class TestRefundCancelApplication {

	public static void main(String[] args) {
		SpringApplication.from(RefundCancelApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
