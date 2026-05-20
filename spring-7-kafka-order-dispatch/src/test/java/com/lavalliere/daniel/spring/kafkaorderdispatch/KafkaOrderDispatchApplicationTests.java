package com.lavalliere.daniel.spring.kafkaorderdispatch;

import com.lavalliere.daniel.spring.kafkaorderdispatch.message.OrderDispatched;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class KafkaOrderDispatchApplicationTests {

	@MockitoBean
	private KafkaTemplate<String, OrderDispatched> kafkaProducerMock;

	@Test
	void contextLoads() {
	}

}
