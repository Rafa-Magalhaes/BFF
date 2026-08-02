package com.rafael.bff;

import com.rafael.bff.infrastructure.client.NotificacaoClient;
import com.rafael.bff.infrastructure.client.AgendadorClient;
import com.rafael.bff.infrastructure.client.UsuarioClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class BffApplicationTests {

	@MockitoBean
	private AgendadorClient tarefaClient;

	@MockitoBean
	private UsuarioClient usuarioClient;

	@MockitoBean
	private NotificacaoClient notificacaoClient;

	@Test
	void contextLoads() {
	}
}