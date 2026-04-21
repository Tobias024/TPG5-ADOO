package com.subastar.controller;

import com.subastar.dto.PujoDTO;
import com.subastar.service.PujoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pujos")
public class PujoController {
    private final PujoService pujoService;
    public PujoController(PujoService pujoService) { this.pujoService = pujoService; }

    @GetMapping("/todos")
    public ResponseEntity<List<PujoDTO>> todos() {
        return ResponseEntity.ok(pujoService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<PujoDTO> crear(@Valid @RequestBody PujoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pujoService.crear(dto));
    }
}
