package com.tfg.rinconmusica.service;

import org.springframework.stereotype.Service;

import com.tfg.rinconmusica.dao.UsuarioDAO;
import com.tfg.rinconmusica.vo.Usuario;

@Service
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario obtenerById(Integer id) {
        return usuarioDAO.obtenerById(id);
    }

    public Usuario registrar(Usuario usuario) {
        Usuario usuarioEmail = usuarioDAO.obtenerPorEmail(usuario.getEmail());
        Usuario usuarioUsername = usuarioDAO.obtenerPorUsername(usuario.getUsername());

        if (usuarioEmail != null) {
            throw new RuntimeException("Ya existe un usuario con ese email");
        }

        if (usuarioUsername != null) {
            throw new RuntimeException("Ya existe un usuario con ese nombre de usuario");
        }

        return usuarioDAO.guardar(usuario);
    }

    public Usuario login(String email, String password) {
        Usuario usuario = usuarioDAO.obtenerPorEmail(email);

        if (usuario == null) {
            return null;
        }

        if (!usuario.getPassword().equals(password)) {
            return null;
        }

        return usuario;
    }
}