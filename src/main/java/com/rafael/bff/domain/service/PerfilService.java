package com.rafael.bff.domain.service;

import com.rafael.bff.api.dto.BffFrontPerfilResponseDTO;
import com.rafael.bff.infrastructure.clientDTO.UsuarioBffPerfilResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.rafael.bff.infrastructure.client.UsuarioClient;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final UsuarioClient usuarioClient;

    public BffFrontPerfilResponseDTO buscarPerfil(String email) {

        UsuarioBffPerfilResponseDTO usuario = usuarioClient.buscarPerfil(email);

        return BffFrontPerfilResponseDTO.builder()
                .usuarioId(usuario.getUsuarioId())
                .email(usuario.getEmail())
                .nome(usuario.getNome())
                .enderecos(usuario.getEnderecos())
                .telefones(usuario.getTelefones())
                .build();
    }
}
