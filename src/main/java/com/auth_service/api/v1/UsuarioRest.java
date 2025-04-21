package com.auth_service.api.v1;

import com.auth_service.entity.usuario.UsuarioRequestDTO;

import com.auth_service.model.UsuarioModel;
import com.auth_service.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping ("api/usuario")
public class UsuarioRest {

    @Autowired
    private UsuarioService usuarioService;

    public ResponseEntity<List<UsuarioModel>> getAllUsuario ( UsuarioRequestDTO usuarioRequestDTO) {
        List<UsuarioModel> usuarioModelList = usuarioService.getAllUsuarios();
        return ResponseEntity.ok(usuarioModelList);
    }

}
