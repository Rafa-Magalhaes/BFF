package com.rafael.bff.business.client;

import com.rafael.bff.business.dto.tarefa.TarefaEnvioAgendadorRequestDTO;
import com.rafael.bff.business.dto.tarefa.TarefaResponseDTO;
import com.rafael.bff.business.dto.tarefa.TarefaStatusUpdateRequestDTO;
import com.rafael.bff.infrastructure.config.ServiceTokenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "agendador-service",
        url = "${agendador.service.url:http://localhost:8081}",
        configuration = ServiceTokenFeignConfig.class   // ← usa Service Token sempre
)
public interface TarefaClient {

    @GetMapping("/internal/tarefas/pendentes")
    List<TarefaResponseDTO> buscarTarefasPendentes();

    @PatchMapping("/internal/tarefas/{id}/status")
    TarefaResponseDTO alterarStatus(@PathVariable("id") String id,
                                    @RequestBody TarefaStatusUpdateRequestDTO statusUpdate);

    @PostMapping("/internal/tarefas")
    TarefaResponseDTO criarTarefa(@RequestBody TarefaEnvioAgendadorRequestDTO request);
}