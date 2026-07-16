package com.rafael.bff.infrastructure.clientDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

    private Long id; // Crucial para os futuros PUT e DELETE

    private String rua;
    private String numero;
    private String cep;
    private String bairro;
    private String cidade;
    private String estado;
}
