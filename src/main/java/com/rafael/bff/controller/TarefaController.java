package com.rafael.bff.controller;

import com.rafael.bff.business.service.TarefaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @GetMapping("/pendentes")
    public void buscarTarefasPendentes() {
        tarefaService.buscarTarefasPendentes();
    }
}