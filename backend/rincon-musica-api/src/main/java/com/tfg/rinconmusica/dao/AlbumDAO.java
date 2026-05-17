package com.tfg.rinconmusica.dao;

import java.util.List;

import com.tfg.rinconmusica.vo.Album;

public interface AlbumDAO {

    List<Album> obtenerTodos();

    Album obtenerById(Integer id);

    List<Album> buscarPorTitulo(String titulo);

    List<Album> obtenerPorArtista(Integer artistaId);
}