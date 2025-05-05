package com.auth_service.repository;

import com.auth_service.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository <UsuarioEntity,Long> {
    Optional <UsuarioEntity> findByEmail(String email);

    Optional<UsuarioEntity> findByEmailAndChaveRecuperacao(String email, String chaveRecuperacao);

    Optional <UsuarioEntity> findBySenha (String Senha);

    String email(String email);
}
