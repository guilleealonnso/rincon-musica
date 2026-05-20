package com.tfg.rinconmusica.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.rinconmusica.service.ActualizacionSpotifyService;

@RestController
@RequestMapping("/api/spotify")
@CrossOrigin(origins = "*")
public class SpotifyController {

    private final ActualizacionSpotifyService actualizacionSpotifyService;

    public SpotifyController(ActualizacionSpotifyService actualizacionSpotifyService) {
        this.actualizacionSpotifyService = actualizacionSpotifyService;
    }

    @PostMapping("/actualizar-popularidad")
    public String actualizarPopularidad() {
    	System.out.println("Endpoint llamado");
        actualizacionSpotifyService.actualizarPopularidadCanciones();
        return "Actualización de popularidad ejecutada correctamente.";
    }
}