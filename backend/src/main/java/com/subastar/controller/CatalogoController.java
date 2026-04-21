package com.subastar.controller;

import com.subastar.dto.CatalogoDTO;
import com.subastar.service.CatalogoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalogos")
public class CatalogoController {
    private final CatalogoService catalogoService;
    public CatalogoController(CatalogoService catalogoService) { this.catalogoService = catalogoService; }

    @GetMapping("/{idCatalogo}")
    public ResponseEntity<CatalogoDTO> obtener(@PathVariable Long idCatalogo) {
        return ResponseEntity.ok(catalogoService.obtener(idCatalogo));
    }
}
