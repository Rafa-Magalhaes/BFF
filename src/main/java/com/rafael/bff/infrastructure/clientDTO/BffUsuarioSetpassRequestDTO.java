package com.rafael.bff.infrastructure.clientDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BffUsuarioSetpassRequestDTO {
    private String senha;
    private String novaSenha;
}