package com.auth_service.api.v1;

import com.auth_service.dto.usuario.AlterarSenhaRequestDTO;
import com.auth_service.dto.usuario.RecuperacaoRequestDTO;
import com.auth_service.dto.usuario.UsuarioRequestDTO;
import com.auth_service.dto.usuario.UsuarioResponseDTO;
import com.auth_service.entity.UsuarioEntity;
import com.auth_service.service.UsuarioService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioRest {

    private final UsuarioService usuarioService;


    public UsuarioRest(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioResponseDTO> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public Optional<UsuarioResponseDTO> getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id);
    }

    @PostMapping("/casdatro-usuario")
    public UsuarioEntity saveUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        return usuarioService.saveUsuario(usuarioRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
    }

    @PostMapping("/recuperar-senha")
    public String recuperarSenha(@RequestBody RecuperacaoRequestDTO recuperacaoRequestDTO) {
       return usuarioService.recuperarSenha(recuperacaoRequestDTO);
    }
    @PostMapping("/alterar-senha")
    public String alterarSenha(@RequestBody AlterarSenhaRequestDTO alterarSenhaRequestDTO) {
        return usuarioService.alterarSenha(alterarSenhaRequestDTO);


    }

}
