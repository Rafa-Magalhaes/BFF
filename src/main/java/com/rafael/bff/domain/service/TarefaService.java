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
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TarefaService {

    private final AgendadorClient agendadorClient;
    private final UsuarioClient usuarioClient;
    private final NotificacaoClient notificacaoClient;

    // ====================== BUSCAR TAREFA POR ID ======================
    public BffFrontAgendamentoResponseDTO buscarTarefaPorId(String tarefaId, String emailUsuario) {

        log.info(">>> [BFF] Buscando tarefa {} para o usuário: {}", tarefaId, emailUsuario);
        Long usuarioId = usuarioClient.buscarIdPorEmail(emailUsuario);

        AgendadorBffMailResponseDTO tarefa = agendadorClient.buscarTarefaPorId(tarefaId, usuarioId);

        return BffFrontAgendamentoResponseDTO.builder()
                .id(tarefa.getId())
                .titulo(tarefa.getTitulo())
                .dataHoraAgendada(tarefa.getDataHoraAgendada())
                .status(tarefa.getStatus())
                .build();
    }

    // ====================== LISTAR TODAS AS TAREFAS DO USUÁRIO ======================
    public List<BffFrontAgendamentoResponseDTO> listarTarefas(String emailUsuario) {

        log.info(">>> [BFF] Listando todas as tarefas para o usuário: {}", emailUsuario);
        Long usuarioId = usuarioClient.buscarIdPorEmail(emailUsuario);

        List<AgendadorBffMailResponseDTO> tarefas = agendadorClient.listarTarefas(usuarioId);

        return tarefas.stream()
                .map(tarefa -> BffFrontAgendamentoResponseDTO.builder()
                        .id(tarefa.getId())
                        .titulo(tarefa.getTitulo())
                        .dataHoraAgendada(tarefa.getDataHoraAgendada())
                        .status(tarefa.getStatus())
                        .build())
                .toList();
    }

    // ====================== CRIAR AGENDAMENTO ======================
    public BffFrontAgendamentoResponseDTO criarTarefa(FrontBffAgendamentoRequestDTO request, String emailUsuario) {

        log.info(">>> [BFF] Iniciando criação de tarefa. Extraído e-mail repassado pelo Controller: {}", emailUsuario);

        Long usuarioId = usuarioClient.buscarIdPorEmail(emailUsuario);

        log.info(">>> [BFF] Usuário validado na API de Usuários. ID recuperado: {}", usuarioId);

        BffAgendadorAgendamentoRequestDTO envioAgendador = BffAgendadorAgendamentoRequestDTO.builder()
                .usuarioId(usuarioId)
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

    // ====================== DELETAR AGENDAMENTO ======================
    public void deletarTarefa(String tarefaId, String email) {
        Long usuarioId = usuarioClient.buscarIdPorEmail(email);
        agendadorClient.deletarTarefa(tarefaId, usuarioId);
    }

    // ====================== SCHEDULER: VERIFICAR PENDÊNCIAS ======================
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

    // ====================== SCHEDULER: ENRIQUECER E PROCESSAR NOTIFICAÇÃO ======================
    private void processarTarefaIndividual(AgendadorBffMailResponseDTO tarefa) {
        String statusFinal = "FALHOU";

        try {
            UsuarioBffMailResponseDTO usuario = usuarioClient.buscarUsuarioPorId(tarefa.getUsuarioId());

            BffNotificacaoMailRequestDTO notificacaoRequest = montarNotificacaoRequest(tarefa, usuario);

            NotificacaoBffMailResponseDTO response = notificacaoClient.enviarNotificacaoTarefa(notificacaoRequest);

            statusFinal = response.getStatus();

            log.info("Notificação processada para a tarefa ID: {}. Status retornado: {}", tarefa.getId(), statusFinal);

        } catch (feign.FeignException e) {
            statusFinal = "FALHOU";
            log.error("API de Notificação reportou erro HTTP {} para a tarefa ID: {}", e.status(), tarefa.getId());

        } catch (Exception e) {
            statusFinal = "FALHOU";
            log.error("Falha sistêmica ao processar tarefa ID: {}. Erro: {}", tarefa.getId(), e.getMessage(), e);

        } finally {
            atualizarStatus(tarefa.getId(), statusFinal);
        }
    }

    // ====================== ALTERAR STATUS DE AGENDAMENTO  ======================
    private void atualizarStatus(String id, String statusFinal) {
        try {
            // 1. Instancia e empacota o status no DTO esperado pelo body da requisição
            BffAgendadorStatusUpdateDTO statusUpdate = BffAgendadorStatusUpdateDTO.builder()
                    .status(statusFinal)
                    .build();

            // 2. Aciona o Client passando a String (ID da URL) e o DTO (Corpo)
            agendadorClient.alterarStatus(id, statusUpdate);

            log.info("Status da tarefa ID: {} atualizado com sucesso para {} no Agendador.", id, statusFinal);

        } catch (Exception e) {
            log.error("Erro CRÍTICO ao tentar atualizar o status da tarefa ID: {} para {}. Erro: {}",
                    id, statusFinal, e.getMessage(), e);
        }
    }

    // ====================== SCHEDULER: CONFECCIONAR E-MAIL ======================
    private BffNotificacaoMailRequestDTO montarNotificacaoRequest(AgendadorBffMailResponseDTO tarefa, UsuarioBffMailResponseDTO usuario) {
        return BffNotificacaoMailRequestDTO.builder()
                .email(usuario.getEmail())
                .nome(usuario.getNome())
                .tituloTarefa(tarefa.getTitulo())
                .descricaoTarefa(tarefa.getDescricao())
                .dataHoraAgendada(tarefa.getDataHoraAgendada())
                .build();
    }
}