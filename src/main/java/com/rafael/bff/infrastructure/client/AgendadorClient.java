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

    // ====================== BUSCAR TAREFA POR ID ======================
    @GetMapping("/internal/tarefas/{tarefaId}")
    AgendadorBffMailResponseDTO buscarTarefaPorId(
            @PathVariable("tarefaId") String tarefaId,
            @RequestParam("usuarioId") Long usuarioId);

    // ====================== LISTAR TODAS AS TAREFAS ======================
    @GetMapping("/internal/tarefas")
    List<AgendadorBffMailResponseDTO> listarTarefas(@RequestParam("usuarioId") Long usuarioId);

    // ====================== CRIAR AGENDAMENTO ======================
    @PostMapping("/internal/tarefas")
    AgendadorBffAgendamentoResponseDTO criarTarefa(@RequestBody BffAgendadorAgendamentoRequestDTO request);

    // ====================== DELETAR AGENDAMENTO  ======================
    @DeleteMapping("/internal/tarefas/{tarefaId}")
    void deletarTarefa(@PathVariable("tarefaId") String tarefaId, @RequestParam("usuarioId") Long usuarioId);

    // ====================== ALTERAR STATUS DE AGENDAMENTO ======================
    @PatchMapping("/internal/tarefas/{id}/status")
    void alterarStatus(@PathVariable("id") String id, @RequestBody BffAgendadorStatusUpdateDTO statusUpdate);

    // ====================== SCHEDULER: VERIFICAR PENDÊNCIAS ======================
    @GetMapping("/internal/tarefas/status/pendentes")
    List<AgendadorBffMailResponseDTO> buscarTarefasPendentes();

    // ==================== DELETAR CADASTRO - TAREFA ====================
    @DeleteMapping("/internal/tarefas/perfil/{usuarioId}")
    void deletarTarefasPorUsuarioId(@PathVariable("usuarioId") Long usuarioId);
}