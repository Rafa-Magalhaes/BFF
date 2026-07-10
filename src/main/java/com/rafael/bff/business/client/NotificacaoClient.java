package com.rafael.bff.business.client;

import com.rafael.bff.business.dto.notificacao.NotificacaoRequestDTO;
import com.rafael.bff.infrastructure.config.ServiceTokenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "notificacao-service",
        url = "${notificacao.service.url}",
        configuration = ServiceTokenFeignConfig.class
)
public interface NotificacaoClient {

    @PostMapping("/emails/enviar-tarefa")
    void enviarNotificacaoTarefa(@RequestBody NotificacaoRequestDTO request);
}