package com.subastar.service;

import com.subastar.dto.RegistroSubastaDTO;
import com.subastar.model.RegistroSubasta;
import com.subastar.repository.RegistroSubastaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistroService {
    private final RegistroSubastaRepository registroRepository;
    public RegistroService(RegistroSubastaRepository registroRepository) { this.registroRepository = registroRepository; }

    public List<RegistroSubastaDTO> listarTodos() {
        return registroRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public RegistroSubastaDTO toDTO(RegistroSubasta r) {
        RegistroSubastaDTO dto = new RegistroSubastaDTO();
        dto.setIdentificador(r.getIdentificador());
        dto.setImporte(r.getImporte());
        dto.setComision(r.getComision());
        if (r.getSubasta() != null) dto.setSubasta(r.getSubasta().getIdentificador());
        if (r.getDuenio() != null) dto.setDuenio(r.getDuenio().getIdentificador());
        if (r.getProducto() != null) dto.setProducto(r.getProducto().getIdentificador());
        if (r.getCliente() != null) dto.setCliente(r.getCliente().getIdentificador());
        return dto;
    }
}
