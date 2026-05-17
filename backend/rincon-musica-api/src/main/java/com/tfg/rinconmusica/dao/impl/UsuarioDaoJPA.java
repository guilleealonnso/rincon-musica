package com.tfg.rinconmusica.dao.impl;

import org.springframework.stereotype.Repository;

import com.tfg.rinconmusica.dao.UsuarioDAO;
import com.tfg.rinconmusica.vo.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class UsuarioDaoJPA implements UsuarioDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Usuario obtenerById(Integer id) {
        return entityManager.find(Usuario.class, id);
    }

    @Override
    public Usuario obtenerPorEmail(String email) {
        try {
            return entityManager
                    .createQuery("FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Usuario obtenerPorUsername(String username) {
        try {
            return entityManager
                    .createQuery("FROM Usuario u WHERE u.username = :username", Usuario.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        entityManager.persist(usuario);
        entityManager.flush();
        entityManager.refresh(usuario);
        return usuario;
    }
}