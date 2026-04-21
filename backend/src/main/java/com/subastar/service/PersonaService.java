package com.subastar.service;

import com.subastar.config.EntityNotFoundException;
import com.subastar.dto.PersonaDTO;
import com.subastar.model.Persona;
import com.subastar.model.enums.EstadoPersona;
import com.subastar.repository.PersonaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonaService(PersonaRepository personaRepository, PasswordEncoder passwordEncoder) {
        this.personaRepository = personaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Persona registrar(PersonaDTO dto) {
        if (personaRepository.existsByDocumento(dto.getDocumento())) {
            throw new IllegalArgumentException("Ya existe una persona con ese documento");
        }
        Persona persona = new Persona();
        persona.setDocumento(dto.getDocumento());
        persona.setNombre(dto.getNombre());
        persona.setDireccion(dto.getDireccion());
        persona.setEmail(dto.getEmail());
        persona.setFechaNacimiento(dto.getFechaNacimiento());
        persona.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoPersona.activo);
        if (dto.getContrasenia() != null) {
            persona.setPasswordHash(passwordEncoder.encode(dto.getContrasenia()));
        }
        return personaRepository.save(persona);
    }

    public PersonaDTO obtener(Long id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró persona con id " + id));
        return toDTO(persona);
    }

    public PersonaDTO actualizar(Long id, PersonaDTO dto) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró persona con id " + id));
        if (dto.getNombre() != null) persona.setNombre(dto.getNombre());
        if (dto.getDireccion() != null) persona.setDireccion(dto.getDireccion());
        if (dto.getEmail() != null) persona.setEmail(dto.getEmail());
        if (dto.getFechaNacimiento() != null) persona.setFechaNacimiento(dto.getFechaNacimiento());
        if (dto.getEstado() != null) persona.setEstado(dto.getEstado());
        if (dto.getContrasenia() != null) persona.setPasswordHash(passwordEncoder.encode(dto.getContrasenia()));
        return toDTO(personaRepository.save(persona));
    }

    public PersonaDTO toDTO(Persona p) {
        PersonaDTO dto = new PersonaDTO();
        dto.setIdentificador(p.getIdentificador());
        dto.setDocumento(p.getDocumento());
        dto.setNombre(p.getNombre());
        dto.setDireccion(p.getDireccion());
        dto.setEstado(p.getEstado());
        dto.setEmail(p.getEmail());
        dto.setFechaNacimiento(p.getFechaNacimiento());
        return dto;
    }
}
