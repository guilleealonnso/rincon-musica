package com.tfg.rinconmusica.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tfg.rinconmusica.dao.CancionDAO;
import com.tfg.rinconmusica.vo.Cancion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class CancionDaoJPA implements CancionDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Cancion obtenerById(Integer id) {
        return entityManager.find(Cancion.class, id);
    }

    @Override
    public List<Cancion> obtenerTodas() {
        return entityManager
                .createQuery("FROM Cancion", Cancion.class)
                .getResultList();
    }

    @Override
    public List<Cancion> obtenerPopulares() {
        return entityManager
                .createQuery("FROM Cancion c ORDER BY c.popularidad DESC", Cancion.class)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public List<Cancion> buscarPorTitulo(String titulo) {
        return entityManager
                .createQuery("FROM Cancion c WHERE LOWER(c.titulo) LIKE LOWER(:titulo)", Cancion.class)
                .setParameter("titulo", "%" + titulo + "%")
                .getResultList();
    }

    @Override
    public List<Cancion> obtenerPorGenero(String genero) {
        return entityManager
                .createQuery("FROM Cancion c WHERE LOWER(c.genero) = LOWER(:genero)", Cancion.class)
                .setParameter("genero", genero)
                .getResultList();
    }

    @Override
    @Transactional
    public Cancion actualizar(Cancion cancion) {
        return entityManager.merge(cancion);
    }
}