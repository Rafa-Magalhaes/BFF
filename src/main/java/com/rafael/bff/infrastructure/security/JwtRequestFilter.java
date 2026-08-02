package com.rafael.bff.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                String username = jwtUtil.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    if (jwtUtil.validateToken(token, username)) {

                        // Cria um objeto Jwt completo compatível com o Spring Security OAuth2 Resource Server
                        Jwt jwt = new Jwt(
                                token,
                                Instant.now(),
                                Instant.now().plusSeconds(3600),
                                Map.of("alg", "HS512"),
                                Map.of("sub", username)
                        );

                        // Injeta o JwtAuthenticationToken que o TarefaController espera
                        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt, Collections.emptyList());
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        log.info(">>> [BFF Filter] JwtAuthenticationToken injetado com sucesso para: {}", username);
                    }
                }
            } catch (Exception e) {
                log.error(">>> [BFF Filter] Erro ao processar token OAuth2: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}