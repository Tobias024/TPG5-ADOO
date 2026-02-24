package com.unomas.controller;

import com.unomas.dto.AuthResponse;
import com.unomas.dto.LoginRequest;
import com.unomas.dto.RegisterRequest;
import com.unomas.model.Usuario;
import com.unomas.security.JwtTokenProvider;
import com.unomas.service.ServicioUsuarios;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ServicioUsuarios servicioUsuarios;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(ServicioUsuarios servicioUsuarios, JwtTokenProvider jwtTokenProvider) {
        this.servicioUsuarios = servicioUsuarios;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        Usuario usuario = servicioUsuarios.registrar(request);
        String token = jwtTokenProvider.generarToken(usuario.getMail());
        return ResponseEntity.ok(new AuthResponse(token, usuario.getNombre(), usuario.getMail(), usuario.getId()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Usuario usuario = servicioUsuarios.login(request.getMail(), request.getPassword());
        String token = jwtTokenProvider.generarToken(usuario.getMail());
        return ResponseEntity.ok(new AuthResponse(token, usuario.getNombre(), usuario.getMail(), usuario.getId()));
    }
}
