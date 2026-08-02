package com.rafael.bff.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrontBffAddenderecoRequestDTO {

    @NotBlank(message = "A rua é obrigatória")
    private String rua;

    @Size(max = 10, message = "O número não pode ter mais que 10 caracteres")
    private String numero;

    @NotBlank(message = "O CEP é obrigatório")
    @Size(max = 8, message = "O CEP não pode ter mais que 8 números")
    @Pattern(regexp = "^\\d+$", message = "Informar somente números no CEP")
    private String cep;

    @NotBlank(message = "O bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "O estado é obrigatório")
    @Size(min = 2, max = 2, message = "O estado deve conter exatamente 2 caracteres (Sigla da UF, ex: SP)")
    private String estado;
}