package com.auth_service.entity.usuario;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UsuarioResponseDTO (Long id, String nome, String email, String senha, String role, Boolean ativo,
                                  LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {

}
