package com.tfg.rinconmusica.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tfg.rinconmusica.dao.ValoracionDAO;
import com.tfg.rinconmusica.vo.Valoracion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class ValoracionDaoJPA implements ValoracionDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Valoracion guardar(Valoracion valoracion) {
        entityManager.persist(valoracion);
        entityManager.flush();
        entityManager.refresh(valoracion);
        return valoracion;
    }

    @Override
    public List<Valoracion> obtenerPorCancion(Integer cancionId) {
        return entityManager
                .createQuery(
                        "FROM Valoracion v WHERE v.cancion.id = :cancionId ORDER BY v.fecha DESC",
                        Valoracion.class)
                .setParameter("cancionId", cancionId)
                .getResultList();
    }

    @Override
    public List<Valoracion> obtenerPorUsuario(Integer usuarioId) {
        return entityManager
                .createQuery(
                        "FROM Valoracion v WHERE v.usuario.id = :usuarioId ORDER BY v.fecha DESC",
                        Valoracion.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }

    @Override
    public Valoracion obtenerPorUsuarioYCancion(Integer usuarioId, Integer cancionId) {
        try {
            return entityManager
                    .createQuery(
                            "FROM Valoracion v WHERE v.usuario.id = :usuarioId AND v.cancion.id = :cancionId",
                            Valoracion.class)
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("cancionId", cancionId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Double obtenerMediaPorCancion(Integer cancionId) {
        return entityManager
                .createQuery(
                        "SELECT AVG(v.puntuacion) FROM Valoracion v WHERE v.cancion.id = :cancionId",
                        Double.class)
                .setParameter("cancionId", cancionId)
                .getSingleResult();
    }
}