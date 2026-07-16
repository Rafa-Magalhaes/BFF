package com.rafael.bff.infrastructure.client;

import com.rafael.bff.infrastructure.clientDTO.AgendadorBffAgendamentoResponseDTO;
import com.rafael.bff.infrastructure.clientDTO.BffAgendadorAgendamentoRequestDTO;
import com.rafael.bff.infrastructure.clientDTO.AgendadorBffMailResponseDTO;
import com.rafael.bff.infrastructure.clientDTO.BffAgendadorStatusUpdateDTO;
import com.rafael.bff.infrastructure.config.ServiceTokenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "agendador-service",
        url = "${agendador.service.url:http://localhost:8081}",
        configuration = ServiceTokenFeignConfig.class   // ← usa Service Token sempre
)
public interface AgendadorClient {

    @GetMapping("/internal/tarefas/pendentes")
    List<AgendadorBffMailResponseDTO> buscarTarefasPendentes();

    @PatchMapping("/internal/tarefas/{id}/status")
    void alterarStatus(@PathVariable("id") Long id, @RequestBody BffAgendadorStatusUpdateDTO statusUpdate);

    @PostMapping("/internal/tarefas")
    AgendadorBffAgendamentoResponseDTO criarTarefa(@RequestBody BffAgendadorAgendamentoRequestDTO request);

    // ====================== DELETAR AGENDAMENTO  ======================
    @DeleteMapping("/internal/tarefas/{tarefaId}")
    void deletarTarefa(@PathVariable("tarefaId") Long tarefaId, @RequestParam("usuarioId") Long usuarioId);

}