package com.rafael.bff.infrastructure.client;

import com.rafael.bff.infrastructure.clientDTO.*;
import com.rafael.bff.infrastructure.config.ServiceTokenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@FeignClient(
        name = "usuario-service",
        url = "${usuario.service.url:http://localhost:8084}",
        configuration = ServiceTokenFeignConfig.class
)
public interface UsuarioClient {

    // ====================== BUSCAR P/ DELETAR AGENDAMENTO  ======================
    @GetMapping("/usuarios/internal/id/{email}")
    Long buscarIdPorEmail(@PathVariable("email") String email);

    // ====================== BUSCAR ======================
    @GetMapping("/usuarios/internal/perfil/{email}")
    UsuarioBffPerfilResponseDTO buscarPerfil(@PathVariable("email") String email);

    // ====================== DELETAR USUÁRIO  ======================
    @DeleteMapping("/usuarios/internal/definitivo/{email}")
    void deletarUsuario(@PathVariable("email") String email);

    // ==================== ATUALIZAR NOME ====================
    @PatchMapping("/usuarios/internal/{email}/nome")
    void atualizarNome(@PathVariable("email") String email, @RequestBody BffUsuarioSetnameRequestDTO request);

    // ==================== ATUALIZAR SENHA ====================
    @PatchMapping("/usuarios/internal/{email}/senha")
    void atualizarSenha(@PathVariable("email") String email, @RequestBody BffUsuarioSetpassRequestDTO request);

    // ==================== ATUALIZAR ENDEREÇO ====================
    @PutMapping("/usuarios/internal/{email}/enderecos/{enderecoId}")
    void atualizarEndereco(
            @PathVariable("email") String email,
            @PathVariable("enderecoId") Long enderecoId,
            @RequestBody BffUsuarioEnderecoUpdateRequestDTO request);

    // ==================== ATUALIZAR TELEFONE ====================
    @PutMapping("/usuarios/internal/{email}/telefones/{telefoneId}")
    void atualizarTelefone(
            @PathVariable("email") String email,
            @PathVariable("telefoneId") Long telefoneId,
            @RequestBody BffUsuarioTelefoneUpdateRequestDTO request);

    // ==================== DELETAR ENDEREÇO ====================
    @DeleteMapping("/usuarios/internal/definitivo/{email}/enderecos/{enderecoId}")
    void deletarEnderecoDefinitivo(
            @PathVariable("email") String email,
            @PathVariable("enderecoId") Long enderecoId);

    // ==================== DELETAR TELEFONE ====================
    @DeleteMapping("/usuarios/internal/definitivo/{email}/telefones/{telefoneId}")
    void deletarTelefoneDefinitivo(
            @PathVariable("email") String email,
            @PathVariable("telefoneId") Long telefoneId);

    // ==================== ADICIONAR ENDEREÇO ====================
    @PostMapping("/usuarios/internal/{email}/enderecos")
    EnderecoDTO adicionarEndereco(
            @PathVariable("email") String email,
            @RequestBody BffUsuarioAddenderecoRequestDTO request);

    // ==================== ADICIONAR TELEFONE ====================
    @PostMapping("/usuarios/internal/{email}/telefones")
    TelefoneDTO adicionarTelefone(
            @PathVariable("email") String email,
            @RequestBody BffUsuarioAddtelefoneRequestDTO request);

    // ==================== LOGIN M2M ====================
    @PostMapping("/usuarios/login")
    UsuarioBffLoginResponseDTO fazerLogin(@RequestBody BffUsuarioLoginRequestDTO request);

    // ==================== BUSCAR USUARIO POR ID ====================
    @GetMapping("/usuarios/internal/{usuarioId}")
    UsuarioBffMailResponseDTO buscarUsuarioPorId(@PathVariable("usuarioId") Long usuarioId);
}