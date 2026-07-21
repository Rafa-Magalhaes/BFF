package com.rafael.bff.api.controller;

import com.rafael.bff.api.dto.FrontBffLoginRequestDTO;
import com.rafael.bff.infrastructure.client.UsuarioClient;
import com.rafael.bff.api.dto.BffFrontLoginResponseDTO;
import com.rafael.bff.infrastructure.clientDTO.BffUsuarioLoginRequestDTO;
import com.rafael.bff.infrastructure.clientDTO.UsuarioBffLoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para login e gerenciamento de tokens")
public class AuthController {

    private final UsuarioClient UsuarioClient;

    // ==================== LOGIN ====================
    @PostMapping("/login")
    @Operation(summary = "Realiza o login", description = "Retorna o Token JWT.")
    public ResponseEntity<BffFrontLoginResponseDTO> login(@Valid @RequestBody FrontBffLoginRequestDTO request) {

        BffUsuarioLoginRequestDTO loginInterno = BffUsuarioLoginRequestDTO.builder()
                .email(request.getEmail())
                .senha(request.getSenha())
                .build();

        UsuarioBffLoginResponseDTO tokenDaApi = UsuarioClient.fazerLogin(loginInterno);

        BffFrontLoginResponseDTO response = BffFrontLoginResponseDTO.builder()
                .token(tokenDaApi.getToken())
                .tipo("Bearer")
                .build();

        return ResponseEntity.ok(response);
    }
}