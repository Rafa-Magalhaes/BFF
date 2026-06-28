package com.rafael.bff.business.client;

import com.rafael.bff.business.dto.usuario.UsuarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {

    @GetMapping("/internal/usuarios/{id}")
    UsuarioResponseDTO buscarUsuarioPorId(@PathVariable Long id);

}