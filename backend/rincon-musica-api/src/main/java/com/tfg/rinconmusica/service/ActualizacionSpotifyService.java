package com.tfg.rinconmusica.service;

import java.time.LocalDateTime;
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

        int intentadas = 0;
        int actualizadas = 0;
        int limite = 10;

        for (Cancion cancion : canciones) {

            if (cancion.getIdSpotify() == null || cancion.getIdSpotify().isBlank()) {
                continue;
            }

            if (intentadas >= limite) {
                break;
            }

            intentadas++;


            Integer popularidadSpotify = spotifyService.obtenerPopularidadCancion(
                    cancion.getIdSpotify(),
                    accessToken
            );

            if (popularidadSpotify == null) {
                System.out.println("No se pudo actualizar: " + cancion.getTitulo());
                pausarPeticion();
                continue;
            }

            if (!popularidadSpotify.equals(cancion.getPopularidad())) {
                cancion.setPopularidad(popularidadSpotify);
                actualizadas++;
            }

            cancion.setUltAct(LocalDateTime.now());
            cancionService.actualizar(cancion);

            pausarPeticion();
        }

        System.out.println("Actualización finalizada. Canciones intentadas: "
                + intentadas + ". Popularidades modificadas: " + actualizadas);
    }

    private void pausarPeticion() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}