package com.subastar.controller;

import com.subastar.dto.FotoDTO;
import com.subastar.service.FotoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping
public class FotoController {
    private final FotoService fotoService;
    public FotoController(FotoService fotoService) { this.fotoService = fotoService; }

    @GetMapping("/productos/{idProducto}/fotos")
    public ResponseEntity<List<FotoDTO>> listar(@PathVariable Long idProducto) {
        return ResponseEntity.ok(fotoService.listarPorProducto(idProducto));
    }

    @PostMapping("/fotos")
    public ResponseEntity<Void> subir(@Valid @RequestBody FotoDTO dto) {
        fotoService.subir(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
