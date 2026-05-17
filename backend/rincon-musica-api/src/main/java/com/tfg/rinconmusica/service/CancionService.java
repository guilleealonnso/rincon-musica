package com.tfg.rinconmusica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tfg.rinconmusica.dao.CancionDAO;
import com.tfg.rinconmusica.vo.Cancion;

@Service
public class CancionService {

    private final CancionDAO cancionDAO;

    public CancionService(CancionDAO cancionDAO) {
        this.cancionDAO = cancionDAO;
    }

    public List<Cancion> obtenerTodas() {
        return cancionDAO.obtenerTodas();
    }

    public Cancion obtenerById(Integer id) {
        return cancionDAO.obtenerById(id);
    }

    public List<Cancion> obtenerPopulares() {
        return cancionDAO.obtenerPopulares();
    }

    public List<Cancion> buscarPorTitulo(String titulo) {
        return cancionDAO.buscarPorTitulo(titulo);
    }

    public List<Cancion> obtenerPorGenero(String genero) {
        return cancionDAO.obtenerPorGenero(genero);
    }

    public Cancion actualizar(Cancion cancion) {
        return cancionDAO.actualizar(cancion);
    }
}