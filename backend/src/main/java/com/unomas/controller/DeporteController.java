package com.unomas.controller;

import com.unomas.model.Deporte;
import com.unomas.repository.DeporteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/deportes")
public class DeporteController {

    private final DeporteRepository deporteRepository;

    public DeporteController(DeporteRepository deporteRepository) {
        this.deporteRepository = deporteRepository;
    }

    @GetMapping
    public ResponseEntity<List<Deporte>> listar() {
        return ResponseEntity.ok(deporteRepository.findAll());
    }
}
