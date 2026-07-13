package com.rafael.bff.domain.service;

import com.rafael.bff.api.dto.BffFrontAgendamentoResponseDTO;
import com.rafael.bff.infrastructure.client.NotificacaoClient;
import com.rafael.bff.infrastructure.client.AgendadorClient;
import com.rafael.bff.infrastructure.client.UsuarioClient;
import com.rafael.bff.infrastructure.clientDTO.*;
import com.rafael.bff.api.dto.FrontBffAgendamentoRequestDTO;
import com.rafael.bff.infrastructure.clientDTO.BffAgendadorStatusUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TarefaService {

    private final AgendadorClient agendadorClient;
    private final UsuarioClient usuarioClient;
    private final NotificacaoClient notificacaoClient;

    public void processarTarefasPendentes() {
        log.info("=== Iniciando processamento de tarefas pendentes ===");

        List<AgendadorBffMailResponseDTO> tarefasPendentes = agendadorClient.buscarTarefasPendentes();

        if (tarefasPendentes == null || tarefasPendentes.isEmpty()) {
            log.info("Nenhuma tarefa pendente encontrada.");
            return;
        }

        for (AgendadorBffMailResponseDTO tarefa : tarefasPendentes) {
            processarTarefaIndividual(tarefa);
        }

        log.info("=== Finalizado processamento de tarefas pendentes ===");
    }

    private void processarTarefaIndividual(AgendadorBffMailResponseDTO tarefa) {
        try {
            UsuarioBffMailResponseDTO usuario = usuarioClient.buscarUsuarioPorId(tarefa.getUsuarioId());

            BffNotificacaoMailRequestDTO notificacaoRequest = montarNotificacaoRequest(tarefa, usuario);

            enviarNotificacao(notificacaoRequest);
            atualizarStatus(tarefa.getId(), "ENVIADO");

            log.info("Notificação enviada com sucesso. Tarefa ID: {}", tarefa.getId());

        } catch (Exception e) {
            log.error("Falha ao processar tarefa ID: {}. Erro: {}", tarefa.getId(), e.getMessage(), e);
            atualizarStatus(tarefa.getId(), "FALHOU");
        }
    }

    private BffNotificacaoMailRequestDTO montarNotificacaoRequest(AgendadorBffMailResponseDTO tarefa, UsuarioBffMailResponseDTO usuario) {
        return BffNotificacaoMailRequestDTO.builder()
                .email(usuario.getEmail())
                .nome(usuario.getNome())
                .tituloTarefa(tarefa.getTitulo())
                .descricaoTarefa(tarefa.getDescricao())
                .dataHoraAgendada(tarefa.getDataHoraAgendada())
                .build();
    }

    private void enviarNotificacao(BffNotificacaoMailRequestDTO request) {
        try {
            notificacaoClient.enviarNotificacaoTarefa(request);
        } catch (Exception e) {
            log.error("Falha ao chamar API de Notificação");
            throw new RuntimeException("Erro ao enviar notificação", e);
        }
    }

    private void atualizarStatus(Long id, String novoStatus) {
        try {
            BffAgendadorStatusUpdateDTO statusUpdate = BffAgendadorStatusUpdateDTO.builder()
                    .status(novoStatus)
                    .build();

            agendadorClient.alterarStatus(id, statusUpdate);
        } catch (Exception e) {

            log.error("Falha ao atualizar status da tarefa ID: {} para status: {}", id, novoStatus, e);
        }
    }

    public BffFrontAgendamentoResponseDTO criarTarefa(FrontBffAgendamentoRequestDTO request) {

        String emailUsuario = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        log.info(">>> [BFF] Iniciando criação de tarefa. Extraído e-mail do token: {}", emailUsuario);

        UsuarioBffAgendamentoResponseDTO usuario = usuarioClient.buscarUsuarioPorEmail(emailUsuario);

        log.info(">>> [BFF] Usuário validado na API de Usuários. ID recuperado: {}", usuario.getId());

        BffAgendadorAgendamentoRequestDTO envioAgendador = BffAgendadorAgendamentoRequestDTO.builder()
                .usuarioId(usuario.getId())
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .dataHoraAgendada(request.getDataHoraAgendada())
                .build();

        log.info(">>> [BFF] Repassando carga útil para a API Agendador...");

        AgendadorBffAgendamentoResponseDTO tarefaCriada = agendadorClient.criarTarefa(envioAgendador);

        log.info(">>> [BFF] Sucesso! Tarefa gerada no banco NoSQL. ID: {} | Status: {}", tarefaCriada.getId(), tarefaCriada.getStatus());

        return BffFrontAgendamentoResponseDTO.builder()
                .id(tarefaCriada.getId())
                .titulo(tarefaCriada.getTitulo())
                .dataHoraAgendada(tarefaCriada.getDataHoraAgendada())
                .status(tarefaCriada.getStatus())
                .build();
    }
}