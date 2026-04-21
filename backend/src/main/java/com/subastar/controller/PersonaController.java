package com.subastar.controller;

import com.subastar.dto.PersonaDTO;
import com.subastar.model.Persona;
import com.subastar.service.PersonaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personas")
public class PersonaController {
    private final PersonaService personaService;
    public PersonaController(PersonaService personaService) { this.personaService = personaService; }

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody PersonaDTO dto) {
        personaService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{idPersona}")
    public ResponseEntity<PersonaDTO> obtener(@PathVariable Long idPersona) {
        return ResponseEntity.ok(personaService.obtener(idPersona));
    }

    @PutMapping("/{idPersona}")
    public ResponseEntity<PersonaDTO> actualizar(@PathVariable Long idPersona, @Valid @RequestBody PersonaDTO dto) {
        return ResponseEntity.ok(personaService.actualizar(idPersona, dto));
    }
}
