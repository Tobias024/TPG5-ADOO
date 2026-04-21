package com.subastar.controller;

import com.subastar.dto.ItemCatalogoDTO;
import com.subastar.service.ItemCatalogoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/items-catalogo")
public class ItemCatalogoController {
    private final ItemCatalogoService itemService;
    public ItemCatalogoController(ItemCatalogoService itemService) { this.itemService = itemService; }

    @GetMapping("/todos")
    public ResponseEntity<List<ItemCatalogoDTO>> todos() {
        return ResponseEntity.ok(itemService.listarTodos());
    }

    @GetMapping("/{idItem}")
    public ResponseEntity<ItemCatalogoDTO> obtener(@PathVariable Long idItem) {
        return ResponseEntity.ok(itemService.obtener(idItem));
    }
}
