package com.rafael.bff.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrontBffAddtelefoneRequestDTO {

    @NotBlank (message = "O campo número é obrigatório")
    @Size(min = 8, max = 9)
    private String numero;

    @NotBlank (message = "O campo DDD é obrigatório")
    @Size(min = 3, max = 3)
    private String ddd;
}
