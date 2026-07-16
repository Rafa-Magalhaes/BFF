package com.rafael.bff.api.controller;

import com.rafael.bff.api.dto.BffFrontAgendamentoResponseDTO;
import com.rafael.bff.api.dto.FrontBffAgendamentoRequestDTO;
import com.rafael.bff.domain.service.TarefaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<Void> deletarTarefa(@PathVariable("tarefaId") Long tarefaId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        tarefaService.deletarTarefa(tarefaId, email);
        return ResponseEntity.noContent().build();
    }
}