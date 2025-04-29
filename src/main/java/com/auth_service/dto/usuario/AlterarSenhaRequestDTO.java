package com.auth_service.dto.usuario;

public record AlterarSenhaRequestDTO(String senha, String chaveRecuperacao, String email) {
}
