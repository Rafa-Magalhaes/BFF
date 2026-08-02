package com.rafael.bff.api.controller;

import com.rafael.bff.api.dto.FrontBffLoginRequestDTO;
import com.rafael.bff.infrastructure.client.UsuarioClient;
import com.rafael.bff.api.dto.BffFrontLoginResponseDTO;
import com.rafael.bff.infrastructure.clientDTO.BffUsuarioLoginRequestDTO;
import com.rafael.bff.infrastructure.clientDTO.UsuarioBffLoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Autenticação", description = "Endpoints para login e gerenciamento de tokens via orquestração BFF")
public class AuthController {

    private final UsuarioClient usuarioClient;

    // ==================== LOGIN ====================
    @PostMapping("/login")
    @Operation(summary = "Realiza o login de usuário", description = "Delega a validação para a API de Usuários e retorna o JWT oficial da aplicação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido e token retornado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos campos (Bean Validation)"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado ou senha inválida")
    })
    public ResponseEntity<BffFrontLoginResponseDTO> login(@Valid @RequestBody FrontBffLoginRequestDTO request) {

        String emailTratado = request.getEmail() != null ? request.getEmail().trim().toLowerCase() : "";

        BffUsuarioLoginRequestDTO loginInterno = BffUsuarioLoginRequestDTO.builder()
                .email(emailTratado)
                .senha(request.getSenha())
                .build();

        UsuarioBffLoginResponseDTO tokenDaApi = usuarioClient.fazerLogin(loginInterno);

        BffFrontLoginResponseDTO response = BffFrontLoginResponseDTO.builder()
                .token(tokenDaApi.getToken())
                .tipo("Bearer")
                .build();

        return ResponseEntity.ok(response);
    }
}