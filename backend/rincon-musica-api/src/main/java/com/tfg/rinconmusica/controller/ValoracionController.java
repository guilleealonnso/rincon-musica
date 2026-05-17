package com.tfg.rinconmusica.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.tfg.rinconmusica.service.ValoracionService;
import com.tfg.rinconmusica.vo.Valoracion;
import com.tfg.rinconmusica.vo.ValoracionRequest;

@RestController
@RequestMapping("/api/valoraciones")
@CrossOrigin(origins = "*")
public class ValoracionController {

    private final ValoracionService valoracionService;

    public ValoracionController(ValoracionService valoracionService) {
        this.valoracionService = valoracionService;
    }

    @PostMapping
    public Valoracion crearValoracion(@RequestBody ValoracionRequest request) {
        return valoracionService.crearValoracion(request);
    }

    @GetMapping("/cancion/{id}")
    public List<Valoracion> obtenerPorCancion(@PathVariable Integer id) {
        return valoracionService.obtenerPorCancion(id);
    }

    @GetMapping("/usuario/{id}")
    public List<Valoracion> obtenerPorUsuario(@PathVariable Integer id) {
        return valoracionService.obtenerPorUsuario(id);
    }

    @GetMapping("/cancion/{id}/media")
    public Double obtenerMediaPorCancion(@PathVariable Integer id) {
        return valoracionService.obtenerMediaPorCancion(id);
    }
}