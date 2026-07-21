package com.rafael.bff.infrastructure.clientDTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BffUsuarioLoginRequestDTO {

    private String email;
    private String senha;
}
