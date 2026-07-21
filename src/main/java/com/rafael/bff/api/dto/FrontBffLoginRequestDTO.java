package com.rafael.bff.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrontBffLoginRequestDTO {

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "Formato de e-mail inválido.")
        private String email;

        @NotBlank(message = "A senha é obrigatória.")
        private String senha;
}