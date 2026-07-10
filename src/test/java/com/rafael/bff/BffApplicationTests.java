package com.rafael.bff;

import com.rafael.bff.business.client.NotificacaoClient;
import com.rafael.bff.business.client.TarefaClient;
import com.rafael.bff.business.client.UsuarioClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(properties = {
		"usuario.service.url=http://localhost:8080"
})
class BffApplicationTests {

	@MockBean
	private TarefaClient tarefaClient;

	@MockBean
	private UsuarioClient usuarioClient;

	@MockBean
	private NotificacaoClient notificacaoClient;

	@Test
	void contextLoads() {
	}
}