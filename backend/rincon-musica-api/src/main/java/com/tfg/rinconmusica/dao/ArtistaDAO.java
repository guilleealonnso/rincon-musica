package com.tfg.rinconmusica.dao;

import java.util.List;

import com.tfg.rinconmusica.vo.Artista;

public interface ArtistaDAO {

    List<Artista> obtenerTodos();

    Artista obtenerById(Integer id);

    List<Artista> buscarPorNombre(String nombre);
}