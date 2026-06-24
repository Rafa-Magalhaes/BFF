package com.rafael.bff.business.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "agendador-tarefas", url = "${agendador-tarefas.url}")
public interface TarefaClient {

    @GetMapping("tarefas/pendentes")
    void buscarTarefasPendentes();

}

