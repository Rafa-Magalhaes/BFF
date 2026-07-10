package com.rafael.bff.business.dto.tarefa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarefaEnvioAgendadorRequestDTO {

    private Long usuarioId;
    private String titulo;
    private String descricao;
    private LocalDateTime dataHoraAgendada;
}