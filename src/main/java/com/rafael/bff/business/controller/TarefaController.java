package com.rafael.bff.business.controller;

import com.rafael.bff.business.dto.tarefa.TarefaPendenteResponseDTO;
import com.rafael.bff.business.service.TarefaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @GetMapping("/pendentes")
    public List<TarefaPendenteResponseDTO> buscarTarefasPendentes() {
        return tarefaService.buscarTarefasPendentes();
    }
}