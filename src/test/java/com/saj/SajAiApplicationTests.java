package com.saj;

import com.saj.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SajAiApplicationTests {

	@Autowired
	private OrderRepository orderRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void loadsDefaultOrdersIntoH2() {
		assertEquals(20, orderRepository.count());
		assertTrue(orderRepository.findByOrderId("ORD-1001").isPresent());
	}

}
