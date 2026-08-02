package com.rafael.bff.infrastructure.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeradorTokenService {

    private final JwtProperties jwtProperties;

    public String gerarTokenService() {
        // Converte a string do secret para bytes com UTF-8 garantido
        SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));

        long expirationMs = jwtProperties.getServiceExpirationMs();

        return Jwts.builder()
                .subject("bff-servico")
                .claim("tokentype", "SERVICE")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }
}