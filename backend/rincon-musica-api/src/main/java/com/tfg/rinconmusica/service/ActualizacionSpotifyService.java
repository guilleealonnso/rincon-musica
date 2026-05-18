package com.tfg.rinconmusica.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tfg.rinconmusica.vo.Cancion;

@Service
public class ActualizacionSpotifyService {

    private final CancionService cancionService;
    private final SpotifyService spotifyService;

    public ActualizacionSpotifyService(CancionService cancionService,
                                       SpotifyService spotifyService) {
        this.cancionService = cancionService;
        this.spotifyService = spotifyService;
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void actualizarPopularidadCanciones() {
        String accessToken = spotifyService.obtenerAccessToken();

        if (accessToken == null) {
            System.out.println("No se pudo obtener token de Spotify.");
            return;
        }

        List<Cancion> canciones = cancionService.obtenerTodas();

        int actualizadas = 0;

        for (Cancion cancion : canciones) {

            if (cancion.getIdSpotify() == null || cancion.getIdSpotify().isBlank()) {
                continue;
            }

            Integer popularidadSpotify = spotifyService.obtenerPopularidadCancion(
                    cancion.getIdSpotify(),
                    accessToken
            );

            if (popularidadSpotify == null) {
                continue;
            }

            if (!popularidadSpotify.equals(cancion.getPopularidad())) {
                cancion.setPopularidad(popularidadSpotify);
                cancionService.actualizar(cancion);
                actualizadas++;

                System.out.println("Popularidad actualizada: "
                        + cancion.getTitulo()
                        + " → "
                        + popularidadSpotify);
            }

            pausarPeticion();
        }

        System.out.println("Actualización finalizada. Canciones actualizadas: " + actualizadas);
    }

    private void pausarPeticion() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}