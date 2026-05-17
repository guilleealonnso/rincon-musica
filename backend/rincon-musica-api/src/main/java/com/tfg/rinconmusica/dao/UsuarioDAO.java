package com.tfg.rinconmusica.dao;

import com.tfg.rinconmusica.vo.Usuario;

public interface UsuarioDAO {

    Usuario obtenerById(Integer id);

    Usuario obtenerPorEmail(String email);

    Usuario obtenerPorUsername(String username);

    Usuario guardar(Usuario usuario);
}