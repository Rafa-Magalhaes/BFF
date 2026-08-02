package com.rafael.bff.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BffFrontLoginResponseDTO {

    private String token;
    private String tipo; // Ex: "Bearer"
}