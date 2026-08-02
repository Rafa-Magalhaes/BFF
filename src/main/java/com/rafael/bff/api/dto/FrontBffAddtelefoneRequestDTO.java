package com.rafael.bff.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrontBffAddtelefoneRequestDTO {

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 8, message = "O número não pode ter mais que 8 números")
    @Pattern(regexp = "^\\d+$", message = "Informar somente números no telefone")
    private String numero;

    @NotBlank(message = "DDD é obrigatório")
    @Size(max = 3, message = "O DDD não pode ter mais que 3 números")
    @Pattern(regexp = "^\\d+$", message = "Informar somente números no DDD")
    private String ddd;
}