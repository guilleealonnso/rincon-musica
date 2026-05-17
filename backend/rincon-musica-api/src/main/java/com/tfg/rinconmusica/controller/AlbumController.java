package com.tfg.rinconmusica.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.tfg.rinconmusica.service.AlbumService;
import com.tfg.rinconmusica.vo.Album;

@RestController
@RequestMapping("/api/albumes")
@CrossOrigin(origins = "*")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public List<Album> obtenerTodos() {
        return albumService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Album obtenerById(@PathVariable Integer id) {
        return albumService.obtenerById(id);
    }

    @GetMapping("/buscar")
    public List<Album> buscarPorTitulo(@RequestParam String titulo) {
        return albumService.buscarPorTitulo(titulo);
    }

    @GetMapping("/artista/{id}")
    public List<Album> obtenerPorArtista(@PathVariable Integer id) {
        return albumService.obtenerPorArtista(id);
    }
}