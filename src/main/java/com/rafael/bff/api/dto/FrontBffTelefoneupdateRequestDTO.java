package com.rafael.bff.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrontBffTelefoneupdateRequestDTO {

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 9, message = "Telefone deve ter no máximo 9 caracteres")
    private String numero;

    @NotBlank(message = "DDD é obrigatório")
    @Size(max = 3, message = "DDD deve ter no máximo 3 caracteres")
    private String ddd;
}
