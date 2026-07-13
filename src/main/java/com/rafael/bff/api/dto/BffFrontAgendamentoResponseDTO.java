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
public class BffFrontAgendamentoResponseDTO {

    private String id;
    private String titulo;
    private LocalDateTime dataHoraAgendada;
    private String status;
}
