package com.subastar.service;

import com.subastar.config.EntityNotFoundException;
import com.subastar.dto.ConfirmacionProductoDTO;
import com.subastar.dto.ProductoDTO;
import com.subastar.model.Producto;
import com.subastar.model.enums.DecisionProducto;
import com.subastar.model.enums.SiNo;
import com.subastar.repository.PersonaRepository;
import com.subastar.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final PersonaRepository personaRepository;

    public ProductoService(ProductoRepository productoRepository, PersonaRepository personaRepository) {
        this.productoRepository = productoRepository;
        this.personaRepository = personaRepository;
    }

    public Producto crear(ProductoDTO dto) {
        Producto p = new Producto();
        p.setDescripcionCompleta(dto.getDescripcionCompleta());
        p.setDescripcionCatalogo(dto.getDescripcionCatalogo());
        p.setSeguro(dto.getSeguro());
        p.setFecha(LocalDate.now());
        p.setDisponible(SiNo.si);
        p.setDuenio(personaRepository.findById(dto.getDuenio())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró persona con id " + dto.getDuenio())));
        return productoRepository.save(p);
    }

    public List<ProductoDTO> listarPorDuenio(Long duenioId) {
        if (!personaRepository.existsById(duenioId)) {
            throw new EntityNotFoundException("No se encontró dueño con id " + duenioId);
        }
        return productoRepository.findByDuenio_Identificador(duenioId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void confirmar(Long id, ConfirmacionProductoDTO dto) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró producto con id " + id));
        if (DecisionProducto.rechazar.equals(dto.getDecision())) {
            p.setDisponible(SiNo.no);
        }
        productoRepository.save(p);
    }

    public ProductoDTO toDTO(Producto p) {
        ProductoDTO dto = new ProductoDTO();
        dto.setIdentificador(p.getIdentificador());
        dto.setFecha(p.getFecha());
        dto.setDisponible(p.getDisponible());
        dto.setDescripcionCatalogo(p.getDescripcionCatalogo());
        dto.setDescripcionCompleta(p.getDescripcionCompleta());
        dto.setSeguro(p.getSeguro());
        if (p.getDuenio() != null) dto.setDuenio(p.getDuenio().getIdentificador());
        return dto;
    }
}
