package com.tfg.rinconmusica.controller;

import org.springframework.web.bind.annotation.*;

import com.tfg.rinconmusica.service.ActualizacionSpotifyService;

@RestController
@RequestMapping("/api/spotify")
@CrossOrigin(origins = "*")
public class SpotifyController {

    private final ActualizacionSpotifyService actualizacionSpotifyService;

    public SpotifyController(ActualizacionSpotifyService actualizacionSpotifyService) {
        this.actualizacionSpotifyService = actualizacionSpotifyService;
    }

    @PostMapping("/rellenar-ids")
    public String rellenarIdsSpotify() {
        return actualizacionSpotifyService.rellenarIdsSpotify();
    }

    @PostMapping("/actualizar-popularidad")
    public String actualizarPopularidad() {
        actualizacionSpotifyService.actualizarPopularidadCanciones();
        return "Actualización de popularidad ejecutada correctamente.";
    }
}