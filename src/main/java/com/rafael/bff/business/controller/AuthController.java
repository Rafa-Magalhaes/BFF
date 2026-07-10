package com.rafael.bff.business.controller;

import com.rafael.bff.business.dto.request.LoginRequestDTO;
import com.rafael.bff.business.client.UsuarioClient;
import com.rafael.bff.business.dto.response.TokenResponseDTO;
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

    private final UsuarioClient usuarioClient;

    @PostMapping("/login")
    @Operation(summary = "Realiza o login", description = "Retorna o Token JWT.")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {

        String tokenJwt = usuarioClient.fazerLogin(request);

        TokenResponseDTO response = TokenResponseDTO.builder()
                .token(tokenJwt)
                .tipo("Bearer")
                .build();

        return ResponseEntity.ok(response);
    }
}