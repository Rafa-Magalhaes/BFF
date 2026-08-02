package com.rafael.bff.api.dto;

import com.rafael.bff.infrastructure.clientDTO.EnderecoDTO;
import com.rafael.bff.infrastructure.clientDTO.TelefoneDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BffFrontPerfilResponseDTO {

    private Long usuarioId;
    private String nome;
    private String email;
    private List<EnderecoDTO> enderecos;
    private List<TelefoneDTO> telefones;
}
