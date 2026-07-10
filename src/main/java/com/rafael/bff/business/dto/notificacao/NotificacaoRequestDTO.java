package com.rafael.bff.business.dto.notificacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoRequestDTO {

    private String emailDestinatario;
    private String nomeDestinatario;
    private String tituloTarefa;
    private String descricaoTarefa;
    private LocalDateTime dataHoraAgendada;

}