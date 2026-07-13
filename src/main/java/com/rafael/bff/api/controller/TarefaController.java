package com.rafael.bff.api.controller;

import com.rafael.bff.api.dto.BffFrontAgendamentoResponseDTO;
import com.rafael.bff.api.dto.FrontBffAgendamentoRequestDTO;
import com.rafael.bff.domain.service.TarefaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping("/tarefas")
    public ResponseEntity<BffFrontAgendamentoResponseDTO> criarTarefa(
            @Valid @RequestBody FrontBffAgendamentoRequestDTO request) {

        BffFrontAgendamentoResponseDTO response = tarefaService.criarTarefa(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}