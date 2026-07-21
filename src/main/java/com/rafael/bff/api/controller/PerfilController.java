package com.rafael.bff.api.controller;

import com.rafael.bff.api.dto.*;
import com.rafael.bff.domain.service.PerfilService;
import com.rafael.bff.infrastructure.clientDTO.EnderecoDTO;
import com.rafael.bff.infrastructure.clientDTO.TelefoneDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@RestController
@RequestMapping("/perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    // ==================== BUSCAR PERFIL ====================
    @GetMapping
    public ResponseEntity<BffFrontPerfilResponseDTO> buscarMeuPerfil(JwtAuthenticationToken token) {
        String email = token.getToken().getSubject();
        BffFrontPerfilResponseDTO response = perfilService.buscarPerfil(email);

        return ResponseEntity.ok(response);
    }

    // ==================== DELETAR CADASTRO - TAREFA E USUARIO ====================
    @DeleteMapping("/definitivo")
    public ResponseEntity<Void> deletarCadastroDefinitivo(JwtAuthenticationToken token) {
        String email = token.getToken().getSubject();
        perfilService.deletarCadastroDefinitivo(email);
        return ResponseEntity.noContent().build();
    }

    // ==================== ATUALIZAR NOME ====================
    @PatchMapping("/nome")
    public ResponseEntity<Void> atualizarNome(
            @RequestBody FrontBffSetnameRequestDTO request,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        String novoNome = request.getNome();
        perfilService.atualizarNome(email, novoNome);
        return ResponseEntity.noContent().build();
    }

    // ==================== ATUALIZAR SENHA ====================
    @PatchMapping("/senha")
    public ResponseEntity<Void> atualizarSenha(
            @Valid @RequestBody FrontBffSetpassRequestDTO request,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        perfilService.atualizarSenha(email, request);
        return ResponseEntity.noContent().build();
    }

    // ==================== ATUALIZAR ENDEREÇO ====================
    @PutMapping("/enderecos/{enderecoId}")
    public ResponseEntity<Void> atualizarEndereco(
            @PathVariable("enderecoId") Long enderecoId,
            @Valid @RequestBody FrontBffEnderecoupdateRequestDTO request,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        perfilService.atualizarEndereco(email, enderecoId, request);
        return ResponseEntity.noContent().build();
    }

    // ==================== ATUALIZAR TELEFONE ====================
    @PutMapping("/telefones/{telefoneId}")
    public ResponseEntity<Void> atualizarTelefone(
            @PathVariable("telefoneId") Long telefoneId,
            @Valid @RequestBody FrontBffTelefoneupdateRequestDTO request,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        perfilService.atualizarTelefone(email, telefoneId, request);
        return ResponseEntity.noContent().build();
    }

    // ==================== DELETAR ENDEREÇO ====================
    @DeleteMapping("/definitivo/enderecos/{enderecoId}")
    public ResponseEntity<Void> deletarEnderecoDefinitivo(
            @PathVariable("enderecoId") Long enderecoId,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        perfilService.deletarEnderecoDefinitivo(email, enderecoId);
        return ResponseEntity.noContent().build();
    }

    // ==================== DELETAR TELEFONE ====================
    @DeleteMapping("/definitivo/telefones/{telefoneId}")
    public ResponseEntity<Void> deletarTelefoneDefinitivo(
            @PathVariable("telefoneId") Long telefoneId,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        perfilService.deletarTelefoneDefinitivo(email, telefoneId);
        return ResponseEntity.noContent().build();
    }

    // ==================== ADICIONAR ENDEREÇO ====================
    @PostMapping("/enderecos")
    public ResponseEntity<EnderecoDTO> adicionarEndereco(
            @Valid @RequestBody FrontBffAddenderecoRequestDTO frontRequest,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        EnderecoDTO addEndereco = perfilService.adicionarEndereco(email, frontRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(addEndereco);
    }

    // ==================== ADICIONAR TELEFONE ====================
    @PostMapping("/telefones")
    public ResponseEntity<TelefoneDTO> adicionarTelefone(
            @Valid @RequestBody FrontBffAddtelefoneRequestDTO frontRequest,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        TelefoneDTO addTelefone = perfilService.adicionarTelefone(email, frontRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(addTelefone);
    }
}