package com.subastar.controller;

import com.subastar.dto.SubastaDTO;
import com.subastar.service.SubastaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/subastas")
public class SubastaController {
    private final SubastaService subastaService;
    public SubastaController(SubastaService subastaService) { this.subastaService = subastaService; }

    @GetMapping("/todos")
    public ResponseEntity<List<SubastaDTO>> todos() {
        return ResponseEntity.ok(subastaService.listarTodas());
    }

    @GetMapping("/{idSubasta}")
    public ResponseEntity<SubastaDTO> obtener(@PathVariable Long idSubasta) {
        return ResponseEntity.ok(subastaService.obtener(idSubasta));
    }
}
