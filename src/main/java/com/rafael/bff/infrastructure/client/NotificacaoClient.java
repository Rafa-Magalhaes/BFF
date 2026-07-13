package com.rafael.bff.infrastructure.client;

import com.rafael.bff.infrastructure.clientDTO.BffNotificacaoMailRequestDTO;
import com.rafael.bff.infrastructure.config.ServiceTokenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "notificacao-service",
        url = "${notificacao.service.url:http://localhost:8083}",
        configuration = ServiceTokenFeignConfig.class
)
public interface NotificacaoClient {

    @PostMapping("/emails/enviar-tarefa")
    void enviarNotificacaoTarefa(@RequestBody BffNotificacaoMailRequestDTO request);
}