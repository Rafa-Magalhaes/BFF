package com.rafael.bff.domain.service;

import com.rafael.bff.api.dto.*;
import com.rafael.bff.infrastructure.client.AgendadorClient;
import com.rafael.bff.infrastructure.clientDTO.*;
import com.rafael.bff.infrastructure.mapper.PerfilConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.rafael.bff.infrastructure.client.UsuarioClient;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final UsuarioClient usuarioClient;
    private final AgendadorClient agendadorClient;
    private final PerfilConverter perfilConverter;

    // ==================== BUSCAR PERFIL ====================
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

    // ==================== DELETAR CADASTRO ====================
    public void deletarCadastroDefinitivo(String email) {
        Long usuarioId = usuarioClient.buscarIdPorEmail(email);
        agendadorClient.deletarTarefasPorUsuarioId(usuarioId);
        usuarioClient.deletarUsuario(email);
    }

    // ==================== ATUALIZAR NOME ====================
    public void atualizarNome(String email, FrontBffSetnameRequestDTO frontRequest) {
        BffUsuarioSetnameRequestDTO requestIntegracao = perfilConverter.toSetnameIntegration(frontRequest);
        usuarioClient.atualizarNome(email, requestIntegracao);
    }

    // ==================== ATUALIZAR SENHA ====================
    public void atualizarSenha(String email, FrontBffSetpassRequestDTO frontRequest) {
        BffUsuarioSetpassRequestDTO requestIntegracao = perfilConverter.toSetpassIntegration(frontRequest);
        usuarioClient.atualizarSenha(email, requestIntegracao);
    }

    // ==================== ATUALIZAR ENDEREÇO ====================
    public void atualizarEndereco(String email, Long enderecoId, FrontBffEnderecoupdateRequestDTO frontRequest) {
        BffUsuarioEnderecoUpdateRequestDTO requestIntegracao = perfilConverter.toEnderecoIntegration(frontRequest);
        usuarioClient.atualizarEndereco(email, enderecoId, requestIntegracao);
    }

    // ==================== ATUALIZAR TELEFONE ====================
    public void atualizarTelefone(String email, Long telefoneId, FrontBffTelefoneupdateRequestDTO frontRequest) {
        BffUsuarioTelefoneUpdateRequestDTO requestIntegracao = perfilConverter.toTelefoneIntegration(frontRequest);
        usuarioClient.atualizarTelefone(email, telefoneId, requestIntegracao);
    }

    // ==================== DELETAR ENDEREÇO ====================
    public void deletarEnderecoDefinitivo(String email, Long enderecoId) {
        usuarioClient.deletarEnderecoDefinitivo(email, enderecoId);
    }

    // ==================== DELETAR TELEFONE ====================
    public void deletarTelefoneDefinitivo(String email, Long telefoneId) {
        usuarioClient.deletarTelefoneDefinitivo(email, telefoneId);
    }

    // ==================== ADICIONAR ENDEREÇO ====================
    public EnderecoDTO adicionarEndereco(String email, FrontBffAddenderecoRequestDTO frontRequest) {
        BffUsuarioAddenderecoRequestDTO request = BffUsuarioAddenderecoRequestDTO.builder()
                .rua(frontRequest.getRua())
                .numero(frontRequest.getNumero())
                .cep(frontRequest.getCep())
                .bairro(frontRequest.getBairro())
                .cidade(frontRequest.getCidade())
                .estado(frontRequest.getEstado())
                .build();

        return usuarioClient.adicionarEndereco(email, request);
    }

    // ==================== ADICIONAR TELEFONE ====================
    public TelefoneDTO adicionarTelefone(String email, FrontBffAddtelefoneRequestDTO frontRequest) {
        BffUsuarioAddtelefoneRequestDTO request = BffUsuarioAddtelefoneRequestDTO.builder()
                .ddd(frontRequest.getDdd())
                .numero(frontRequest.getNumero())
                .build();

        return usuarioClient.adicionarTelefone(email, request);
    }
}