package com.tfg.rinconmusica.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tfg.rinconmusica.dao.AlbumDAO;
import com.tfg.rinconmusica.vo.Album;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class AlbumDaoJPA implements AlbumDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Album> obtenerTodos() {
        return entityManager
                .createQuery("FROM Album", Album.class)
                .getResultList();
    }

    @Override
    public Album obtenerById(Integer id) {
        return entityManager.find(Album.class, id);
    }

    @Override
    public List<Album> buscarPorTitulo(String titulo) {
        return entityManager
                .createQuery("FROM Album a WHERE LOWER(a.titulo) LIKE LOWER(:titulo)", Album.class)
                .setParameter("titulo", "%" + titulo + "%")
                .getResultList();
    }

    @Override
    public List<Album> obtenerPorArtista(Integer artistaId) {
        return entityManager
                .createQuery("FROM Album a WHERE a.artista.id = :artistaId", Album.class)
                .setParameter("artistaId", artistaId)
                .getResultList();
    }
    @Override
    @Transactional
    public Album actualizar(Album album) {
        return entityManager.merge(album);
    }
}