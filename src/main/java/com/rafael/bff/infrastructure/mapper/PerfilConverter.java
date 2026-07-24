package com.rafael.bff.infrastructure.mapper;

import com.rafael.bff.api.dto.*;
import com.rafael.bff.infrastructure.clientDTO.*;
import org.springframework.stereotype.Component;

@Component
public class PerfilConverter {

    public BffUsuarioSetpassRequestDTO toSetpassIntegration(FrontBffSetpassRequestDTO dto) {
        return BffUsuarioSetpassRequestDTO.builder()
                .senha(dto.getSenha())
                .novaSenha(dto.getNovaSenha())
                .build();
    }

    public BffUsuarioEnderecoUpdateRequestDTO toEnderecoIntegration(FrontBffEnderecoupdateRequestDTO dto) {
        return BffUsuarioEnderecoUpdateRequestDTO.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .bairro(dto.getBairro())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .build();
    }

    public BffUsuarioTelefoneUpdateRequestDTO toTelefoneIntegration(FrontBffTelefoneupdateRequestDTO dto) {
        return BffUsuarioTelefoneUpdateRequestDTO.builder()
                .ddd(dto.getDdd())
                .numero(dto.getNumero())
                .build();
    }

    public BffUsuarioSetnameRequestDTO toSetnameIntegration(FrontBffSetnameRequestDTO dto) {
        return BffUsuarioSetnameRequestDTO.builder()
                .nome(dto.getNome())
                .build();
    }
}