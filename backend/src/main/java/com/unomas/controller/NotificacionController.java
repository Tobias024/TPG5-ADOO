package com.unomas.controller;

import com.unomas.dto.NotificacionResponse;
import com.unomas.model.Notificacion;
import com.unomas.model.Usuario;
import com.unomas.repository.NotificacionRepository;
import com.unomas.service.ServicioUsuarios;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionRepository notificacionRepository;
    private final ServicioUsuarios servicioUsuarios;

    public NotificacionController(NotificacionRepository notificacionRepository,
                                   ServicioUsuarios servicioUsuarios) {
        this.notificacionRepository = notificacionRepository;
        this.servicioUsuarios = servicioUsuarios;
    }

    @GetMapping
    public ResponseEntity<List<NotificacionResponse>> listar(Authentication auth) {
        Usuario usuario = servicioUsuarios.obtenerPorMail(auth.getName());
        List<Notificacion> notifs = notificacionRepository.findByDestinoIdOrderByFechaEnvioDesc(usuario.getId());
        return ResponseEntity.ok(notifs.stream().map(NotificacionResponse::from).toList());
    }

    @GetMapping("/no-leidas")
    public ResponseEntity<Map<String, Long>> contarNoLeidas(Authentication auth) {
        Usuario usuario = servicioUsuarios.obtenerPorMail(auth.getName());
        long count = notificacionRepository.countByDestinoIdAndLeidaFalse(usuario.getId());
        return ResponseEntity.ok(Map.of("count", count));
    }

    @PostMapping("/{id}/leer")
    public ResponseEntity<Void> marcarLeida(@PathVariable Long id) {
        notificacionRepository.findById(id).ifPresent(n -> {
            n.setLeida(true);
            notificacionRepository.save(n);
        });
        return ResponseEntity.ok().build();
    }
}
