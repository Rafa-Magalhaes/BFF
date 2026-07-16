package com.rafael.bff.infrastructure.clientDTO;

import java.util.List;
import lombok.Data;

@Data
public class UsuarioBffPerfilResponseDTO {

    private Long usuarioId;
    private String nome;
    private String email;
    private List<EnderecoDTO> enderecos;
    private List<TelefoneDTO> telefones;
}
