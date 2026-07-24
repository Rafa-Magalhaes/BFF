package com.rafael.bff.infrastructure.clientDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BffUsuarioTelefoneUpdateRequestDTO {
    private String numero;
    private String ddd;
}