package com.tfg.rinconmusica.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.rinconmusica.service.CancionService;
import com.tfg.rinconmusica.vo.Cancion;

@RestController
@RequestMapping("/api/canciones")
@CrossOrigin(origins = "*")
public class CancionController {

    private final CancionService cancionService;

    public CancionController(CancionService cancionService) {
        this.cancionService = cancionService;
    }

    @GetMapping
    public List<Cancion> obtenerTodas() {
        return cancionService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Cancion obtenerById(@PathVariable Integer id) {
        return cancionService.obtenerById(id);
    }

    @GetMapping("/populares")
    public List<Cancion> obtenerPopulares() {
        return cancionService.obtenerPopulares();
    }

    @GetMapping("/buscar")
    public List<Cancion> buscarPorTitulo(@RequestParam String titulo) {
        return cancionService.buscarPorTitulo(titulo);
    }

    @GetMapping("/genero/{genero}")
    public List<Cancion> obtenerPorGenero(@PathVariable String genero) {
        return cancionService.obtenerPorGenero(genero);
    }
}