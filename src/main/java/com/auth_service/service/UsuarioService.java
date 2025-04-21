package com.auth_service.service;

import com.auth_service.model.UsuarioModel;
import com.auth_service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioModel> getAllUsuarios () {
        return usuarioRepository.findAll();
    }

}
