package com.rafael.bff.business.scheduler;

import com.rafael.bff.business.service.TarefaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TarefaScheduler {

    private final TarefaService tarefaService;

    @Scheduled(fixedDelay = 300000) // 5 minutos = 300.000 milissegundos
    public void verificarTarefasPendentes() {
        log.info("Iniciando verificação de tarefas pendentes...");
        tarefaService.buscarTarefasPendentes();
        log.info("Verificação de tarefas pendentes finalizada.");
    }
}