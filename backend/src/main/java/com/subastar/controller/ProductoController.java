package com.subastar.controller;

import com.subastar.dto.ConfirmacionProductoDTO;
import com.subastar.dto.ProductoDTO;
import com.subastar.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoService productoService;
    public ProductoController(ProductoService productoService) { this.productoService = productoService; }

    @PostMapping
    public ResponseEntity<Void> crear(@Valid @RequestBody ProductoDTO dto) {
        productoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/duenio/{idDuenio}")
    public ResponseEntity<List<ProductoDTO>> listarPorDuenio(@PathVariable Long idDuenio) {
        return ResponseEntity.ok(productoService.listarPorDuenio(idDuenio));
    }

    @PostMapping("/{idProducto}/confirmacion")
    public ResponseEntity<Void> confirmar(@PathVariable Long idProducto, @Valid @RequestBody ConfirmacionProductoDTO dto) {
        productoService.confirmar(idProducto, dto);
        return ResponseEntity.ok().build();
    }
}
