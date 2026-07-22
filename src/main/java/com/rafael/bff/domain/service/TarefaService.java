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
        String statusFinal = "FALHOU"; // Por segurança, assume falha se algo der errado no meio do caminho

        try {
            // 1. Enriquece os dados buscando o usuário na API Usuario
            UsuarioBffMailResponseDTO usuario = usuarioClient.buscarUsuarioPorId(tarefa.getUsuarioId());

            // 2. Confecciona o pacote que vai para a API Notificacao
            BffNotificacaoMailRequestDTO notificacaoRequest = montarNotificacaoRequest(tarefa, usuario);

            // 3. Dispara a notificação de forma síncrona e CAPTURA a resposta real ("ENVIADO" ou "FALHOU")
            NotificacaoBffMailResponseDTO response = notificacaoClient.enviarNotificacaoTarefa(notificacaoRequest);

            // 4. O status vem diretamente da resposta da API de Notificação!
            statusFinal = response.getStatus();

            log.info("Notificação processada para a tarefa ID: {}. Status retornado: {}", tarefa.getId(), statusFinal);

        } catch (feign.FeignException e) {
            // Se a API de Notificação devolveu HTTP 500 (Erro no Gmail)
            statusFinal = "FALHOU";
            log.error("API de Notificação reportou erro HTTP {} para a tarefa ID: {}", e.status(), tarefa.getId());

        } catch (Exception e) {
            // Se a API Usuario caiu, a Notificação caiu, ou deu Timeout
            statusFinal = "FALHOU";
            log.error("Falha sistêmica ao processar tarefa ID: {}. Erro: {}", tarefa.getId(), e.getMessage(), e);

        } finally {
            // 5. O bloco finally garante que OBRIGATÓRIAMENTE o status será atualizado no Agendador,
            // seja ele "ENVIADO" ou "FALHOU", sem risco de deixar a tarefa travada no limbo.
            atualizarStatus(tarefa.getId(), statusFinal);
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

    // ====================== DELETAR AGENDAMENTO ======================
    public void deletarTarefa(Long tarefaId, String email) {
        Long usuarioId = usuarioClient.buscarIdPorEmail(email);
        agendadorClient.deletarTarefa(tarefaId, usuarioId);
    }

    // ====================== ALTERAR STATUS DE AGENDAMENTO  ======================
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






    // ====================== CRIAR AGENDAMENTO  ======================
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