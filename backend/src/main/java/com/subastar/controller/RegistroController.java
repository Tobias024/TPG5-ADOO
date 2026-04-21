package com.subastar.controller;

import com.subastar.dto.RegistroSubastaDTO;
import com.subastar.service.RegistroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/registros-subasta")
public class RegistroController {
    private final RegistroService registroService;
    public RegistroController(RegistroService registroService) { this.registroService = registroService; }

    @GetMapping("/todos")
    public ResponseEntity<List<RegistroSubastaDTO>> todos() {
        return ResponseEntity.ok(registroService.listarTodos());
    }
}
