package com.rafael.bff.infrastructure.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@SuppressWarnings("deprecation")
@Service
@RequiredArgsConstructor
@Slf4j
public class GeradorTokenService {

    private final JwtProperties jwtProperties;

    /**
     * Gera um token JWT do tipo SERVICE para comunicação entre serviços.
     */
    public String gerarTokenService() {
        SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());

        return Jwts.builder()
                .setSubject("bff-servico")
                .claim("tokentype", "SERVICE")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}