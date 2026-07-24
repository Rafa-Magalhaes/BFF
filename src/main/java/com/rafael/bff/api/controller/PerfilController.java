package com.rafael.bff.api.controller;

import com.rafael.bff.api.dto.*;
import com.rafael.bff.domain.service.PerfilService;
import com.rafael.bff.infrastructure.clientDTO.EnderecoDTO;
import com.rafael.bff.infrastructure.clientDTO.TelefoneDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/perfil")
@RequiredArgsConstructor
@Tag(name = "Perfil", description = "Endpoints para gerenciamento do perfil do usuário autenticado")
public class PerfilController {

    private final PerfilService perfilService;

    // ==================== BUSCAR PERFIL ====================
    @GetMapping
    @Operation(summary = "Busca os dados completos do perfil", description = "Retorna os dados do usuário logado, incluindo listas de endereços e telefones.")
    public ResponseEntity<BffFrontPerfilResponseDTO> buscarMeuPerfil(JwtAuthenticationToken token) {
        String email = token.getToken().getSubject();
        BffFrontPerfilResponseDTO response = perfilService.buscarPerfil(email);
        return ResponseEntity.ok(response);
    }

    // ==================== DELETAR CADASTRO ====================
    @DeleteMapping("/definitivo")
    @Operation(summary = "Deleta a conta do usuário em definitivo", description = "Exclui o usuário e, em cascata, todos os seus agendamentos.")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Cadastro deletado com sucesso") })
    public ResponseEntity<Void> deletarCadastroDefinitivo(JwtAuthenticationToken token) {
        String email = token.getToken().getSubject();
        perfilService.deletarCadastroDefinitivo(email);
        return ResponseEntity.noContent().build();
    }

    // ==================== ATUALIZAR NOME ====================
    @PatchMapping("/nome")
    @Operation(summary = "Atualiza o nome do usuário")
    public ResponseEntity<Void> atualizarNome(
            @Valid @RequestBody FrontBffSetnameRequestDTO request,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        perfilService.atualizarNome(email, request);
        return ResponseEntity.noContent().build();
    }

    // ==================== ATUALIZAR SENHA ====================
    @PatchMapping("/senha")
    @Operation(summary = "Atualiza a senha do usuário")
    public ResponseEntity<Void> atualizarSenha(
            @Valid @RequestBody FrontBffSetpassRequestDTO request,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        perfilService.atualizarSenha(email, request);
        return ResponseEntity.noContent().build();
    }

    // ==================== ATUALIZAR ENDEREÇO ====================
    @PutMapping("/enderecos/{enderecoId}")
    @Operation(summary = "Atualiza um endereço específico")
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
    @Operation(summary = "Atualiza um telefone específico")
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
    @Operation(summary = "Deleta um endereço específico do perfil")
    public ResponseEntity<Void> deletarEnderecoDefinitivo(
            @PathVariable("enderecoId") Long enderecoId,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        perfilService.deletarEnderecoDefinitivo(email, enderecoId);
        return ResponseEntity.noContent().build();
    }

    // ==================== DELETAR TELEFONE ====================
    @DeleteMapping("/definitivo/telefones/{telefoneId}")
    @Operation(summary = "Deleta um telefone específico do perfil")
    public ResponseEntity<Void> deletarTelefoneDefinitivo(
            @PathVariable("telefoneId") Long telefoneId,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        perfilService.deletarTelefoneDefinitivo(email, telefoneId);
        return ResponseEntity.noContent().build();
    }

    // ==================== ADICIONAR ENDEREÇO ====================
    @PostMapping("/enderecos")
    @Operation(summary = "Adiciona um novo endereço ao perfil")
    public ResponseEntity<EnderecoDTO> adicionarEndereco(
            @Valid @RequestBody FrontBffAddenderecoRequestDTO frontRequest,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        EnderecoDTO addEndereco = perfilService.adicionarEndereco(email, frontRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(addEndereco);
    }

    // ==================== ADICIONAR TELEFONE ====================
    @PostMapping("/telefones")
    @Operation(summary = "Adiciona um novo telefone ao perfil")
    public ResponseEntity<TelefoneDTO> adicionarTelefone(
            @Valid @RequestBody FrontBffAddtelefoneRequestDTO frontRequest,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        TelefoneDTO addTelefone = perfilService.adicionarTelefone(email, frontRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(addTelefone);
    }
}