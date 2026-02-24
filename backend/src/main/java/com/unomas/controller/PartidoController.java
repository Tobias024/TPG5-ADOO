package com.unomas.controller;

import com.unomas.dto.FinalizarPartidoRequest;
import com.unomas.dto.PartidoRequest;
import com.unomas.dto.PartidoResponse;
import com.unomas.model.Partido;
import com.unomas.model.Usuario;
import com.unomas.service.GestorFlujoPartido;
import com.unomas.service.ServicioInscripcionPartido;
import com.unomas.service.ServicioPartidos;
import com.unomas.service.ServicioUsuarios;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {

    private final ServicioPartidos servicioPartidos;
    private final ServicioInscripcionPartido servicioInscripcion;
    private final GestorFlujoPartido gestorFlujoPartido;
    private final ServicioUsuarios servicioUsuarios;

    public PartidoController(ServicioPartidos servicioPartidos,
                              ServicioInscripcionPartido servicioInscripcion,
                              GestorFlujoPartido gestorFlujoPartido,
                              ServicioUsuarios servicioUsuarios) {
        this.servicioPartidos = servicioPartidos;
        this.servicioInscripcion = servicioInscripcion;
        this.gestorFlujoPartido = gestorFlujoPartido;
        this.servicioUsuarios = servicioUsuarios;
    }

    @GetMapping
    public ResponseEntity<List<PartidoResponse>> listar(
            @RequestParam(required = false) Long deporteId,
            @RequestParam(required = false) String estado) {
        List<Partido> partidos;
        if (deporteId != null) {
            partidos = servicioPartidos.buscarPorDeporte(deporteId);
        } else {
            partidos = servicioPartidos.listar();
        }
        if (estado != null && !estado.isBlank()) {
            partidos = partidos.stream()
                .filter(p -> p.getEstadoNombre().equals(estado))
                .toList();
        }
        return ResponseEntity.ok(partidos.stream().map(PartidoResponse::from).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(PartidoResponse.from(servicioPartidos.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<PartidoResponse> crear(@Valid @RequestBody PartidoRequest request,
                                                  Authentication auth) {
        Usuario usuario = servicioUsuarios.obtenerPorMail(auth.getName());
        Partido partido = servicioPartidos.crear(request, usuario);
        return ResponseEntity.ok(PartidoResponse.from(partido));
    }

    @PostMapping("/{id}/inscribir")
    public ResponseEntity<PartidoResponse> inscribir(@PathVariable Long id, Authentication auth) {
        Usuario usuario = servicioUsuarios.obtenerPorMail(auth.getName());
        Partido partido = servicioPartidos.obtenerPorId(id);
        partido = servicioInscripcion.inscribir(partido, usuario);
        return ResponseEntity.ok(PartidoResponse.from(partido));
    }

    @PostMapping("/{id}/avanzar")
    public ResponseEntity<PartidoResponse> avanzar(@PathVariable Long id, Authentication auth) {
        Usuario usuario = servicioUsuarios.obtenerPorMail(auth.getName());
        Partido partido = servicioPartidos.obtenerPorId(id);
        if (!usuario.getId().equals(partido.getOrganizador().getId())) {
            return ResponseEntity.status(403).build();
        }
        partido = gestorFlujoPartido.avanzarEstado(partido);
        return ResponseEntity.ok(PartidoResponse.from(partido));
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<PartidoResponse> cancelar(@PathVariable Long id, Authentication auth) {
        Usuario usuario = servicioUsuarios.obtenerPorMail(auth.getName());
        Partido partido = servicioPartidos.obtenerPorId(id);
        partido = gestorFlujoPartido.cancelarPartido(partido, usuario);
        return ResponseEntity.ok(PartidoResponse.from(partido));
    }

    @GetMapping("/mis-partidos")
    public ResponseEntity<List<PartidoResponse>> misPartidos(Authentication auth) {
        Usuario usuario = servicioUsuarios.obtenerPorMail(auth.getName());
        List<Partido> partidos = servicioPartidos.obtenerPorUsuario(usuario.getId());
        return ResponseEntity.ok(partidos.stream().map(PartidoResponse::from).toList());
    }

    @GetMapping("/organizados")
    public ResponseEntity<List<PartidoResponse>> organizados(Authentication auth) {
        Usuario usuario = servicioUsuarios.obtenerPorMail(auth.getName());
        List<Partido> partidos = servicioPartidos.obtenerOrganizadosPor(usuario.getId());
        return ResponseEntity.ok(partidos.stream().map(PartidoResponse::from).toList());
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<PartidoResponse> finalizar(@PathVariable Long id,
                                                      @Valid @RequestBody FinalizarPartidoRequest request,
                                                      Authentication auth) {
        Usuario usuario = servicioUsuarios.obtenerPorMail(auth.getName());
        Partido partido = servicioPartidos.obtenerPorId(id);
        if (!usuario.getId().equals(partido.getOrganizador().getId())) {
            return ResponseEntity.status(403).build();
        }
        partido = gestorFlujoPartido.finalizarConResultado(partido, request);
        return ResponseEntity.ok(PartidoResponse.from(partido));
    }
}
