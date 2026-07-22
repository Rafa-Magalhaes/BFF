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
    @NotBlank(message = "O bairro é obrigatório")
    private String bairro;
    @NotBlank(message = "A cidade é obrigatória")
    private String cidade;
    @NotBlank(message = "O estado é obrigatório")
    private String estado;
}