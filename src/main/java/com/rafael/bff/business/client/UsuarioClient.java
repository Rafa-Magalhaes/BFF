package com.rafael.bff.business.client;

import com.rafael.bff.business.dto.request.LoginRequestDTO;
import com.rafael.bff.business.dto.usuario.UsuarioResponseDTO;
import com.rafael.bff.infrastructure.config.ServiceTokenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.annotation.Validated;

@Validated
@FeignClient(
        name = "usuario-service",
        url = "${usuario.service.url:http://localhost:8080}",
        configuration = ServiceTokenFeignConfig.class
)
public interface UsuarioClient {

    @PostMapping("/usuarios/login")
    String fazerLogin(@RequestBody LoginRequestDTO request);

    @GetMapping("/usuarios/por-email")
    UsuarioResponseDTO buscarUsuarioPorEmail(@RequestParam("email") String email);

    @GetMapping("/usuarios/internal/{id}")
    UsuarioResponseDTO buscarUsuarioPorId(@PathVariable("id") Long id);
}