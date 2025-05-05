package com.auth_service.service;

import com.auth_service.dto.usuario.*;
import com.auth_service.entity.UsuarioEntity;
import com.auth_service.repository.UsuarioRepository;
import com.auth_service.service.builder.BuilderUtils;
import com.auth_service.utils.JwtTokenUtil;
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


    public UsuarioResponseDTO saveUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioEntity usuarioEntity = BuilderUtils.toUsuarioEntity(usuarioRequestDTO);
        UsuarioEntity savedEntity = usuarioRepository.save(usuarioEntity);
        return BuilderUtils.toUsuarioResponseDTO(savedEntity);
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
    public LoginResponseDTO login (LoginRequestDTO loginRequestDTO){
      Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findByEmail(loginRequestDTO.email());
      Optional<UsuarioEntity> usuarioEntity2 = usuarioRepository.findBySenha(loginRequestDTO.senha());

//      if(usuarioEntity.isEmpty()){
//          return "Usuario não Encontrado";
//      } else if (usuarioEntity2.isEmpty()) {
//          return "Senha Invalida";
//
//      }
        String token = JwtTokenUtil.generateToken(usuarioEntity.get().getEmail());
        return BuilderUtils.toLoginResponseDTO(token,"BearerToken",loginRequestDTO.email()) ;
    }

}
