package com.rafael.bff.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrontBffAddenderecoRequestDTO {
    @NotBlank(message = "A rua é obrigatória")
    private String rua;
    private String numero;
    @NotBlank(message = "O CEP é obrigatório")
    private String cep;
    private String bairro;
    private String cidade;
    private String estado;
}