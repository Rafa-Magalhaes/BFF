package com.rafael.bff.infrastructure.clientDTO;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BffUsuarioLoginRequestDTO {

    private String email;
    private String senha;
}
