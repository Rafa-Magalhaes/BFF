package com.rafael.bff;

import com.rafael.bff.business.client.TarefaClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class BffApplicationTests {

	@MockBean
	private TarefaClient tarefaClient;

	@Test
	void contextLoads() {
	}
}