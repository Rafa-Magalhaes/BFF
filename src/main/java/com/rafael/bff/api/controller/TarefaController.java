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

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    // ==================== CRIAR AGENDAMENTO OK====================
    @PostMapping
    public ResponseEntity<BffFrontAgendamentoResponseDTO> criarTarefa(
            @Valid @RequestBody FrontBffAgendamentoRequestDTO request) {

        BffFrontAgendamentoResponseDTO response = tarefaService.criarTarefa(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ==================== DELETAR AGENDAMENTO====================
    @DeleteMapping("/{tarefaId}")
    public ResponseEntity<Void> deletarTarefa(
            @PathVariable("tarefaId") Long tarefaId,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        tarefaService.deletarTarefa(tarefaId, email);
        return ResponseEntity.noContent().build();
    }
}