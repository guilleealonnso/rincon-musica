package com.tfg.rinconmusica.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tfg.rinconmusica.dao.ArtistaDAO;
import com.tfg.rinconmusica.vo.Artista;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ArtistaDaoJPA implements ArtistaDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Artista> obtenerTodos() {
        return entityManager
                .createQuery("FROM Artista", Artista.class)
                .getResultList();
    }

    @Override
    public Artista obtenerById(Integer id) {
        return entityManager.find(Artista.class, id);
    }

    @Override
    public List<Artista> buscarPorNombre(String nombre) {
        return entityManager
                .createQuery("FROM Artista a WHERE LOWER(a.nombre) LIKE LOWER(:nombre)", Artista.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
    }
}