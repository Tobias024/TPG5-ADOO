package com.subastar.service;

import com.subastar.config.EntityNotFoundException;
import com.subastar.dto.CatalogoDTO;
import com.subastar.model.Catalogo;
import com.subastar.repository.CatalogoRepository;
import org.springframework.stereotype.Service;

@Service
public class CatalogoService {
    private final CatalogoRepository catalogoRepository;
    public CatalogoService(CatalogoRepository catalogoRepository) { this.catalogoRepository = catalogoRepository; }

    public CatalogoDTO obtener(Long id) {
        return toDTO(catalogoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró catálogo con id " + id)));
    }

    public CatalogoDTO toDTO(Catalogo c) {
        CatalogoDTO dto = new CatalogoDTO();
        dto.setIdentificador(c.getIdentificador());
        dto.setDescripcion(c.getDescripcion());
        if (c.getSubasta() != null) dto.setSubasta(c.getSubasta().getIdentificador());
        if (c.getResponsable() != null) dto.setResponsable(c.getResponsable().getIdentificador());
        return dto;
    }
}
