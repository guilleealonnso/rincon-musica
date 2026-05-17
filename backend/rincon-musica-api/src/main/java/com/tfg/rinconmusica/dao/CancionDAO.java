package com.tfg.rinconmusica.dao;

import java.util.List;

import com.tfg.rinconmusica.vo.Cancion;

public interface CancionDAO {

    List<Cancion> obtenerTodas();

    Cancion obtenerById(Integer id);

    List<Cancion> obtenerPopulares();

    List<Cancion> buscarPorTitulo(String titulo);

    List<Cancion> obtenerPorGenero(String genero);

    Cancion actualizar(Cancion cancion);
}