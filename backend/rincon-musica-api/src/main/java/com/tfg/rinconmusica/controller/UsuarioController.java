package com.tfg.rinconmusica.controller;

import org.springframework.web.bind.annotation.*;

import com.tfg.rinconmusica.service.UsuarioService;
import com.tfg.rinconmusica.vo.LoginRequest;
import com.tfg.rinconmusica.vo.Usuario;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public Usuario obtenerById(@PathVariable Integer id) {
        return usuarioService.obtenerById(id);
    }

    @PostMapping("/registro")
    public Usuario registrar(@RequestBody Usuario usuario) {
        return usuarioService.registrar(usuario);
    }	

    @PostMapping("/login")
    public Usuario login(@RequestBody LoginRequest loginRequest) {
        return usuarioService.login(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );
    }
}