package com.rafael.bff.domain.service;

import com.rafael.bff.api.dto.*;
import com.rafael.bff.infrastructure.client.AgendadorClient;
import com.rafael.bff.infrastructure.clientDTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.rafael.bff.infrastructure.client.UsuarioClient;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final UsuarioClient usuarioClient;
    private final AgendadorClient agendadorClient;

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

    // ==================== DELETAR CADASTRO - TAREFA E USUARIO ====================
    public void deletarCadastroDefinitivo(String email) {
        Long usuarioId = usuarioClient.buscarIdPorEmail(email);
        agendadorClient.deletarTarefasPorUsuarioId(usuarioId);
        usuarioClient.deletarUsuario(email);
    }

    // ==================== ATUALIZAR NOME ====================
    public void atualizarNome(String email, String novoNome) {
        usuarioClient.atualizarNome(email, novoNome);
    }

    // ==================== ATUALIZAR SENHA ====================
    public void atualizarSenha(String email, FrontBffSetpassRequestDTO request) {
        usuarioClient.atualizarSenha(email, request);
    }

    // ==================== ATUALIZAR ENDEREÇO ====================
    public void atualizarEndereco(String email, Long enderecoId, FrontBffEnderecoupdateRequestDTO request) {
        usuarioClient.atualizarEndereco(email, enderecoId, request);
    }

    // ==================== ATUALIZAR TELEFONE ====================
    public void atualizarTelefone(String email, Long telefoneId, FrontBffTelefoneupdateRequestDTO request) {
        usuarioClient.atualizarTelefone(email, telefoneId, request);
    }

    // ==================== DELETAR ENDEREÇO ====================
    public void deletarEnderecoDefinitivo(String email, Long enderecoId) {
        usuarioClient.deletarEnderecoDefinitivo(email, enderecoId);
    }

    // ==================== DELETAR TELEFONE ====================
    public void deletarTelefoneDefinitivo (String email, Long telefoneId) {
        usuarioClient.deletarTelefoneDefinitivo(email, telefoneId);
    }

    // ==================== ADICIONAR ENDEREÇO ====================
    public EnderecoDTO adicionarEndereco(String email, FrontBffAddenderecoRequestDTO frontRequest) {
        BffUsuarioAddenderecoRequestDTO request = new BffUsuarioAddenderecoRequestDTO();
        request.setRua(frontRequest.getRua());
        request.setNumero(frontRequest.getNumero());
        request.setCep(frontRequest.getCep());
        request.setBairro(frontRequest.getBairro());
        request.setCidade(frontRequest.getCidade());
        request.setEstado(frontRequest.getEstado());

        return usuarioClient.adicionarEndereco(email, request);
    }

    // ==================== ADICIONAR TELEFONE ====================
    public TelefoneDTO adicionarTelefone(String email, FrontBffAddtelefoneRequestDTO frontRequest) {
        BffUsuarioAddtelefoneRequestDTO request = new BffUsuarioAddtelefoneRequestDTO();
        request.setDdd(frontRequest.getDdd());
        request.setNumero(frontRequest.getNumero());

        return usuarioClient.adicionarTelefone(email, request);
    }

    }


