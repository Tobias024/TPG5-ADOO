package com.subastar.service;

import com.subastar.dto.PaisDTO;
import com.subastar.model.Pais;
import com.subastar.repository.PaisRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaisService {
    private final PaisRepository paisRepository;
    public PaisService(PaisRepository paisRepository) { this.paisRepository = paisRepository; }

    public List<PaisDTO> listarTodos() {
        return paisRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PaisDTO toDTO(Pais p) {
        PaisDTO dto = new PaisDTO();
        dto.setNumero(p.getNumero());
        dto.setNombre(p.getNombre());
        dto.setNombreCorto(p.getNombreCorto());
        dto.setCapital(p.getCapital());
        dto.setNacionalidad(p.getNacionalidad());
        dto.setIdiomas(p.getIdiomas());
        return dto;
    }
}
