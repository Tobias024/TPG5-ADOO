package com.unomas.controller;

import com.unomas.dto.UsuarioResponse;
import com.unomas.model.Usuario;
import com.unomas.service.ServicioUsuarios;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final ServicioUsuarios servicioUsuarios;

    public UsuarioController(ServicioUsuarios servicioUsuarios) {
        this.servicioUsuarios = servicioUsuarios;
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> perfil(Authentication auth) {
        Usuario usuario = servicioUsuarios.obtenerPorMail(auth.getName());
        return ResponseEntity.ok(UsuarioResponse.from(usuario));
    }
}
