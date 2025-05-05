package com.auth_service.dto.usuario;

import lombok.Builder;

@Builder
public record LoginResponseDTO(String token, String messagem, String email, Integer amarelinha) {

}
