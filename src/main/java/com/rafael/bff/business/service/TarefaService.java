package com.rafael.bff.business.service;

import com.rafael.bff.business.client.TarefaClient;
import com.rafael.bff.business.client.UsuarioClient;
import com.rafael.bff.business.dto.tarefa.TarefaPendenteResponseDTO;
import com.rafael.bff.business.dto.tarefa.TarefaResponseDTO;
import com.rafael.bff.business.dto.usuario.UsuarioResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaClient tarefaClient;
    private final UsuarioClient usuarioClient;

    public List<TarefaPendenteResponseDTO> buscarTarefasPendentes() {

        // 1. Busca as tarefas pendentes no Agendador-tarefas
        List<TarefaResponseDTO> tarefasPendentes = tarefaClient.buscarTarefasPendentes();

        // 2. Para cada tarefa, busca os dados do usuário e monta o DTO enriquecido
        return tarefasPendentes.stream()
                .map(tarefa -> {
                    // Busca os dados do usuário na API de Usuários
                    UsuarioResponseDTO usuario = usuarioClient.buscarUsuarioPorId(tarefa.getUsuarioId());

                    // Monta o objeto TarefaPendenteResponseDTO com os dados da tarefa + usuário
                    return TarefaPendenteResponseDTO.builder()
                            .id(tarefa.getId())
                            .usuarioId(tarefa.getUsuarioId())
                            .titulo(tarefa.getTitulo())
                            .descricao(tarefa.getDescricao())
                            .dataHoraAgendada(tarefa.getDataHoraAgendada())
                            .dataCriacao(tarefa.getDataCriacao())
                            .status(tarefa.getStatus())
                            .emailUsuario(usuario.getEmail())   // ← vindo da API de Usuários
                            .nomeUsuario(usuario.getNome())     // ← vindo da API de Usuários
                            .build();
                })
                .toList();
    }
}