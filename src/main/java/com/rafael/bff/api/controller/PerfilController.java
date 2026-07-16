package com.rafael.bff.api.controller;

import com.rafael.bff.api.dto.BffFrontPerfilResponseDTO;
import com.rafael.bff.domain.service.PerfilService;
import com.rafael.bff.domain.service.TarefaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    @GetMapping
    public ResponseEntity<BffFrontPerfilResponseDTO> buscarMeuPerfil() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        BffFrontPerfilResponseDTO response = perfilService.buscarPerfil(email);

        return ResponseEntity.ok(response);
    }


}