package com.rafael.bff.api.controller;

import com.rafael.bff.api.dto.BffFrontAgendamentoResponseDTO;
import com.rafael.bff.api.dto.FrontBffAgendamentoRequestDTO;
import com.rafael.bff.domain.service.TarefaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    // ====================== BUSCAR TAREFA POR ID ======================
    @GetMapping("/{tarefaId}")
    public ResponseEntity<BffFrontAgendamentoResponseDTO> buscarTarefaPorId(
            @PathVariable("tarefaId") String tarefaId,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        BffFrontAgendamentoResponseDTO response = tarefaService.buscarTarefaPorId(tarefaId, email);
        return ResponseEntity.ok(response);
    }

    // ====================== LISTAR TODAS AS TAREFAS DO USUÁRIO ======================
    @GetMapping
    public ResponseEntity<List<BffFrontAgendamentoResponseDTO>> listarTarefas(JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        List<BffFrontAgendamentoResponseDTO> response = tarefaService.listarTarefas(email);
        return ResponseEntity.ok(response);
    }

    // ====================== CRIAR AGENDAMENTO ======================
    @PostMapping
    public ResponseEntity<BffFrontAgendamentoResponseDTO> criarTarefa(
            @Valid @RequestBody FrontBffAgendamentoRequestDTO request,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();

        BffFrontAgendamentoResponseDTO response = tarefaService.criarTarefa(request, email);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ==================== DELETAR AGENDAMENTO ====================
    @DeleteMapping("/{tarefaId}")
    public ResponseEntity<Void> deletarTarefa(
            @PathVariable("tarefaId") String tarefaId,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        tarefaService.deletarTarefa(tarefaId, email);
        return ResponseEntity.noContent().build();
    }
}