package com.tfg.rinconmusica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tfg.rinconmusica.dao.ArtistaDAO;
import com.tfg.rinconmusica.vo.Artista;

@Service
public class ArtistaService {

    private final ArtistaDAO artistaDAO;

    public ArtistaService(ArtistaDAO artistaDAO) {
        this.artistaDAO = artistaDAO;
    }

    public List<Artista> obtenerTodos() {
        return artistaDAO.obtenerTodos();
    }

    public Artista obtenerById(Integer id) {
        return artistaDAO.obtenerById(id);
    }

    public List<Artista> buscarPorNombre(String nombre) {
        return artistaDAO.buscarPorNombre(nombre);
    }
    public Artista actualizar(Artista artista) {
        return artistaDAO.actualizar(artista);
    }
}