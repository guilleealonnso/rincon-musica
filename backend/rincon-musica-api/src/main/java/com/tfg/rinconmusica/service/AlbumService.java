package com.tfg.rinconmusica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tfg.rinconmusica.dao.AlbumDAO;
import com.tfg.rinconmusica.vo.Album;

@Service
public class AlbumService {

    private final AlbumDAO albumDAO;

    public AlbumService(AlbumDAO albumDAO) {
        this.albumDAO = albumDAO;
    }
    public Album actualizar(Album album) {
        return albumDAO.actualizar(album);
    }
    public List<Album> obtenerTodos() {
        return albumDAO.obtenerTodos();
    }

    public Album obtenerById(Integer id) {
        return albumDAO.obtenerById(id);
    }

    public List<Album> buscarPorTitulo(String titulo) {
        return albumDAO.buscarPorTitulo(titulo);
    }

    public List<Album> obtenerPorArtista(Integer artistaId) {
        return albumDAO.obtenerPorArtista(artistaId);
    }
}