package com.auth_service.service.builder;


import com.auth_service.dto.usuario.UsuarioRequestDTO;
import com.auth_service.dto.usuario.UsuarioResponseDTO;
import com.auth_service.entity.UsuarioEntity;

public class BuilderUtils {

    public static UsuarioEntity toUsuarioEntity(UsuarioRequestDTO dto) {
        return UsuarioEntity.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(dto.senha())
                .role(dto.role())
                .ativo(dto.ativo())
                .build();
    }

    public static UsuarioResponseDTO toUsuarioResponseDTO(UsuarioEntity entity) {
        return UsuarioResponseDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .senha(entity.getSenha())
                .role(entity.getRole())
                .ativo(entity.getAtivo())
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .build();
    }
}
