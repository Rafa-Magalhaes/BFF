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
        configuration = ServiceTokenFeignConfig.class
)
public interface AgendadorClient {

    // ====================== ALTERAR STATUS DE AGENDAMENTO ======================
    @PatchMapping("/internal/tarefas/{id}/status")
    void alterarStatus(@PathVariable("id") Long id, @RequestBody BffAgendadorStatusUpdateDTO statusUpdate);

    // ====================== SCHEDULER: VERIFICAR PENDÊNCIAS ======================
    @GetMapping("/internal/tarefas/pendentes")
    List<AgendadorBffMailResponseDTO> buscarTarefasPendentes();

    // ====================== DELETAR AGENDAMENTO  ======================
    @DeleteMapping("/internal/tarefas/{tarefaId}")
    void deletarTarefa(@PathVariable("tarefaId") Long tarefaId, @RequestParam("usuarioId") Long usuarioId);

    // ==================== DELETAR CADASTRO - TAREFA ====================
    @DeleteMapping("/internal/tarefas/perfil/{usuarioId}")
    void deletarTarefasPorUsuarioId(@PathVariable("usuarioId") Long usuarioId);

    @PostMapping("/internal/tarefas")
    AgendadorBffAgendamentoResponseDTO criarTarefa(@RequestBody BffAgendadorAgendamentoRequestDTO request);
}