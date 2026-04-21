package com.subastar.controller;

import com.subastar.dto.AsistenteDTO;
import com.subastar.service.AsistenteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/asistentes")
public class AsistenteController {
    private final AsistenteService asistenteService;
    public AsistenteController(AsistenteService asistenteService) { this.asistenteService = asistenteService; }

    @PostMapping
    public ResponseEntity<AsistenteDTO> inscribirse(@Valid @RequestBody AsistenteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(asistenteService.inscribirse(dto));
    }
}
