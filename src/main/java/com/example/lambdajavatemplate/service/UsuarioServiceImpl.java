package com.example.lambdajavatemplate.service;

import com.example.lambdajavatemplate.model.Usuario;
import com.example.lambdajavatemplate.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UsuarioServiceImpl implements UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}
