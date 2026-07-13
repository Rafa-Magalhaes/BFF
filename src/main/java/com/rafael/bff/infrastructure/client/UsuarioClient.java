package com.rafael.bff.infrastructure.client;

import com.rafael.bff.infrastructure.clientDTO.UsuarioBffAgendamentoResponseDTO;
import com.rafael.bff.infrastructure.clientDTO.BffUsuarioLoginRequestDTO;
import com.rafael.bff.infrastructure.clientDTO.UsuarioBffLoginResponseDTO;
import com.rafael.bff.infrastructure.clientDTO.UsuarioBffMailResponseDTO;
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
    UsuarioBffLoginResponseDTO fazerLogin(@RequestBody BffUsuarioLoginRequestDTO request);

    @GetMapping("/usuarios/por-email")
    UsuarioBffAgendamentoResponseDTO buscarUsuarioPorEmail(@RequestParam("email") String email);

    @GetMapping("/usuarios/internal/{usuarioId}")
    UsuarioBffMailResponseDTO buscarUsuarioPorId(@PathVariable("usuarioId") Long usuarioId);
}