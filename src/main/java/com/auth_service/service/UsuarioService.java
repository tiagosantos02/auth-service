package com.auth_service.service;

import com.auth_service.dto.usuario.AlterarSenhaRequestDTO;
import com.auth_service.dto.usuario.RecuperacaoRequestDTO;
import com.auth_service.dto.usuario.UsuarioRequestDTO;
import com.auth_service.dto.usuario.UsuarioResponseDTO;
import com.auth_service.entity.UsuarioEntity;
import com.auth_service.repository.UsuarioRepository;
import com.auth_service.service.builder.BuilderUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioResponseDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(BuilderUtils::toUsuarioResponseDTO)
                .toList();
    }

    public Optional<UsuarioResponseDTO> getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .map(BuilderUtils::toUsuarioResponseDTO);
    }


    public UsuarioEntity saveUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioEntity usuarioEntity = BuilderUtils.toUsuarioEntity(usuarioRequestDTO);
        return usuarioRepository.save(usuarioEntity);

    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public String recuperarSenha(RecuperacaoRequestDTO recuperacaoRequestDTO) {
//        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(recuperacaoRequestDTO.email());
//        if (usuarioEntity == null) {
//            throw new RuntimeException("Usuario Nao encontrado");
//        }
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(recuperacaoRequestDTO.email()).orElseThrow(()->new RuntimeException("usuario Nao encontrado"));
        usuarioEntity.setChaveRecuperacao(UUID.randomUUID().toString());
        usuarioRepository.save(usuarioEntity);
        return "Recuperado: " + usuarioEntity.getChaveRecuperacao();
    }
    public String  alterarSenha(AlterarSenhaRequestDTO alterarSenhaRequestDTO) {
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmailAndChaveRecuperacao(alterarSenhaRequestDTO.email(), alterarSenhaRequestDTO.chaveRecuperacao()).orElseThrow(()->new RuntimeException("usuario Nao encontrado"));
        usuarioEntity.setSenha(alterarSenhaRequestDTO.senha());
        usuarioEntity.setChaveRecuperacao(null);
        usuarioRepository.save(usuarioEntity);
        return "Alterado com sucesso";
    }

}
