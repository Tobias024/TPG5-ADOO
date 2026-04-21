package com.subastar.controller;

import com.subastar.dto.NotificacionDTO;
import com.subastar.service.NotificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {
    private final NotificacionService notificacionService;
    public NotificacionController(NotificacionService notificacionService) { this.notificacionService = notificacionService; }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<NotificacionDTO>> listar(@PathVariable Long idCliente) {
        return ResponseEntity.ok(notificacionService.listarPorCliente(idCliente));
    }
}
