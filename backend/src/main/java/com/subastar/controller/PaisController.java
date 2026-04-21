package com.subastar.controller;

import com.subastar.dto.PaisDTO;
import com.subastar.service.PaisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/paises")
public class PaisController {
    private final PaisService paisService;
    public PaisController(PaisService paisService) { this.paisService = paisService; }

    @GetMapping("/todos")
    public ResponseEntity<List<PaisDTO>> todos() {
        return ResponseEntity.ok(paisService.listarTodos());
    }
}
