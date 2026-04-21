package com.subastar.controller;

import com.subastar.dto.ClienteDTO;
import com.subastar.dto.ClienteRegistroDTO;
import com.subastar.dto.MetricasClienteDTO;
import com.subastar.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    public ClienteController(ClienteService clienteService) { this.clienteService = clienteService; }

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody ClienteRegistroDTO dto) {
        clienteService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> obtener(@PathVariable Long idCliente) {
        return ResponseEntity.ok(clienteService.obtener(idCliente));
    }

    @GetMapping("/{idCliente}/metricas")
    public ResponseEntity<MetricasClienteDTO> metricas(@PathVariable Long idCliente) {
        return ResponseEntity.ok(clienteService.obtenerMetricas(idCliente));
    }
}
