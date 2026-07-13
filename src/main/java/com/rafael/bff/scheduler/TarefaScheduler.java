package com.rafael.bff.scheduler;

import com.rafael.bff.domain.service.TarefaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TarefaScheduler {

    private final TarefaService tarefaService;

    // fixedDelay = 30 minutos (1.800.000 ms)
    // initialDelay = 10 segundos (10.000 ms)
    @Scheduled(fixedDelay = 1800000, initialDelay = 10000)
    public void processarTarefasPendentes() {
        log.info("=== Iniciando job de processamento de tarefas pendentes ===");
        tarefaService.processarTarefasPendentes();
        log.info("=== Finalizado job de processamento de tarefas pendentes ===");
    }
}