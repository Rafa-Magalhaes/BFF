package com.rafael.bff.business.client;

import com.rafael.bff.business.dto.tarefa.TarefaResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "agendador-tarefas", url = "${agendador-tarefas.url}")
public interface TarefaClient {

    @GetMapping("/tarefas/pendentes")
    List<TarefaResponseDTO> buscarTarefasPendentes();

}