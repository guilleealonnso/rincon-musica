package com.tfg.rinconmusica.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.rinconmusica.service.ArtistaService;
import com.tfg.rinconmusica.vo.Artista;

@RestController
@RequestMapping("/api/artistas")
@CrossOrigin(origins = "*")
public class ArtistaController {

    private final ArtistaService artistaService;

    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @GetMapping
    public List<Artista> obtenerTodos() {
        return artistaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Artista obtenerById(@PathVariable Integer id) {
        return artistaService.obtenerById(id);
    }

    @GetMapping("/buscar")
    public List<Artista> buscarPorNombre(@RequestParam String nombre) {
        return artistaService.buscarPorNombre(nombre);
    }
}