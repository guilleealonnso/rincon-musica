package com.tfg.rinconmusica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tfg.rinconmusica.dao.ValoracionDAO;
import com.tfg.rinconmusica.vo.Cancion;
import com.tfg.rinconmusica.vo.Usuario;
import com.tfg.rinconmusica.vo.Valoracion;
import com.tfg.rinconmusica.vo.ValoracionRequest;

@Service
public class ValoracionService {

    private final ValoracionDAO valoracionDAO;
    private final UsuarioService usuarioService;
    private final CancionService cancionService;

    public ValoracionService(
            ValoracionDAO valoracionDAO,
            UsuarioService usuarioService,
            CancionService cancionService) {
        this.valoracionDAO = valoracionDAO;
        this.usuarioService = usuarioService;
        this.cancionService = cancionService;
    }

    public Valoracion crearValoracion(ValoracionRequest request) {

        if (request.getPuntuacion() == null || request.getPuntuacion() < 1 || request.getPuntuacion() > 5) {
            throw new RuntimeException("La puntuación debe estar entre 1 y 5");
        }

        Valoracion existente = valoracionDAO.obtenerPorUsuarioYCancion(
                request.getUsuarioId(),
                request.getCancionId()
        );

        if (existente != null) {
            throw new RuntimeException("El usuario ya ha valorado esta canción");
        }

        Usuario usuario = usuarioService.obtenerById(request.getUsuarioId());
        Cancion cancion = cancionService.obtenerById(request.getCancionId());

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (cancion == null) {
            throw new RuntimeException("Canción no encontrada");
        }

        Valoracion valoracion = new Valoracion();
        valoracion.setUsuario(usuario);
        valoracion.setCancion(cancion);
        valoracion.setPuntuacion(request.getPuntuacion());
        valoracion.setComentario(request.getComentario());

        return valoracionDAO.guardar(valoracion);
    }

    public List<Valoracion> obtenerPorCancion(Integer cancionId) {
        return valoracionDAO.obtenerPorCancion(cancionId);
    }

    public List<Valoracion> obtenerPorUsuario(Integer usuarioId) {
        return valoracionDAO.obtenerPorUsuario(usuarioId);
    }

    public Double obtenerMediaPorCancion(Integer cancionId) {
        Double media = valoracionDAO.obtenerMediaPorCancion(cancionId);

        if (media == null) {
            return 0.0;
        }

        return media;
    }
}