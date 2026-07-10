package com.rafael.bff.infrastructure.config;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ServiceTokenFeignConfig {

    private final GeradorTokenService geradorTokenService;

    @Bean
    public RequestInterceptor serviceTokenInterceptor() {
        return template -> {
            String serviceToken = geradorTokenService.gerarTokenService();
            template.header("Authorization", "Bearer " + serviceToken);
        };
    }
}