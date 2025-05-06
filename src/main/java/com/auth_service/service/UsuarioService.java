package com.auth_service.service;

import com.auth_service.dto.usuario.*;
import com.auth_service.entity.UsuarioEntity;
import com.auth_service.repository.UsuarioRepository;
import com.auth_service.service.builder.BuilderUtils;
import com.auth_service.utils.JwtTokenUtil;
import com.auth_service.utils.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
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


    public UsuarioResponseDTO saveUsuario(UsuarioRequestDTO usuarioRequestDTO)
    {
        UsuarioEntity usuarioEntity = BuilderUtils.toUsuarioEntity(usuarioRequestDTO);
        // Encrypt the password before saving
        usuarioEntity.setSenha(passwordEncoder.encode(usuarioEntity.getSenha()));
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
        // Encrypt the new password
        usuarioEntity.setSenha(passwordEncoder.encode(alterarSenhaRequestDTO.senha()));
        usuarioEntity.setChaveRecuperacao(null);
        usuarioRepository.save(usuarioEntity);
        return "Alterado com sucesso";
    }
    public LoginResponseDTO login (LoginRequestDTO loginRequestDTO){
      Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findByEmail(loginRequestDTO.email());

      if (usuarioOptional.isEmpty()) {
          throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha incorretos");
      }

      UsuarioEntity usuario = usuarioOptional.get();

      if (!passwordEncoder.matches(loginRequestDTO.senha(), usuario.getSenha())) {
          throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha incorretos");
      }

      String token = jwtTokenUtil.generateToken(usuario.getEmail());
      return BuilderUtils.toLoginResponseDTO(token, "BearerToken", loginRequestDTO.email());
    }

}
