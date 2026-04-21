package com.subastar.controller;

import com.subastar.dto.MetodoPagoDTO;
import com.subastar.service.MetodoPagoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/metodos-pago")
public class MetodoPagoController {
    private final MetodoPagoService metodoPagoService;
    public MetodoPagoController(MetodoPagoService metodoPagoService) { this.metodoPagoService = metodoPagoService; }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<MetodoPagoDTO>> listar(@PathVariable Long idCliente) {
        return ResponseEntity.ok(metodoPagoService.listarPorCliente(idCliente));
    }

    @PostMapping
    public ResponseEntity<Void> agregar(@Valid @RequestBody MetodoPagoDTO dto) {
        metodoPagoService.agregar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{idMetodo}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idMetodo) {
        metodoPagoService.eliminar(idMetodo);
        return ResponseEntity.noContent().build();
    }
}
