package com.rafael.bff.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrontBffAgendamentoRequestDTO {

    private String titulo;
    private String descricao;
    private LocalDateTime dataHoraAgendada;

}