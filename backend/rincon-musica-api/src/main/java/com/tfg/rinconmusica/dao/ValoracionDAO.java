package com.tfg.rinconmusica.dao;

import java.util.List;

import com.tfg.rinconmusica.vo.Valoracion;

public interface ValoracionDAO {

    Valoracion guardar(Valoracion valoracion);

    List<Valoracion> obtenerPorCancion(Integer cancionId);

    List<Valoracion> obtenerPorUsuario(Integer usuarioId);

    Valoracion obtenerPorUsuarioYCancion(Integer usuarioId, Integer cancionId);

    Double obtenerMediaPorCancion(Integer cancionId);
}