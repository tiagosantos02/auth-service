package com.auth_service.dto.usuario;

import com.auth_service.Enums.RoleEnum;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UsuarioRequestDTO(Long id, String nome, String email, String senha, RoleEnum role, Boolean ativo,
                                LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
}
