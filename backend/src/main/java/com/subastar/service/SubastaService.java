package com.subastar.service;

import com.subastar.config.EntityNotFoundException;
import com.subastar.dto.SubastaDTO;
import com.subastar.model.Subasta;
import com.subastar.repository.SubastaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubastaService {

    private final SubastaRepository subastaRepository;

    public SubastaService(SubastaRepository subastaRepository) {
        this.subastaRepository = subastaRepository;
    }

    public List<SubastaDTO> listarTodas() {
        return subastaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public SubastaDTO obtener(Long id) {
        return toDTO(subastaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró subasta con id " + id)));
    }

    public SubastaDTO toDTO(Subasta s) {
        SubastaDTO dto = new SubastaDTO();
        dto.setIdentificador(s.getIdentificador());
        dto.setFecha(s.getFecha());
        dto.setHora(s.getHora());
        dto.setEstado(s.getEstado());
        dto.setUbicacion(s.getUbicacion());
        dto.setCapacidadAsistentes(s.getCapacidadAsistentes());
        dto.setCategoria(s.getCategoria());
        if (s.getSubastador() != null) dto.setSubastador(s.getSubastador().getIdentificador());
        return dto;
    }
}
