package com.subastar.service;

import com.subastar.config.EntityNotFoundException;
import com.subastar.dto.AsistenteDTO;
import com.subastar.model.Asistente;
import com.subastar.repository.AsistenteRepository;
import com.subastar.repository.ClienteRepository;
import com.subastar.repository.SubastaRepository;
import org.springframework.stereotype.Service;

@Service
public class AsistenteService {
    private final AsistenteRepository asistenteRepository;
    private final ClienteRepository clienteRepository;
    private final SubastaRepository subastaRepository;

    public AsistenteService(AsistenteRepository asistenteRepository, ClienteRepository clienteRepository, SubastaRepository subastaRepository) {
        this.asistenteRepository = asistenteRepository;
        this.clienteRepository = clienteRepository;
        this.subastaRepository = subastaRepository;
    }

    public AsistenteDTO inscribirse(AsistenteDTO dto) {
        var existing = asistenteRepository.findByCliente_IdentificadorAndSubasta_Identificador(dto.getCliente(), dto.getSubasta());
        if (existing.isPresent()) {
            return toDTO(existing.get());
        }
        Asistente a = new Asistente();
        a.setCliente(clienteRepository.findById(dto.getCliente())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró cliente con id " + dto.getCliente())));
        a.setSubasta(subastaRepository.findById(dto.getSubasta())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró subasta con id " + dto.getSubasta())));
        int count = asistenteRepository.findBySubasta_Identificador(dto.getSubasta()).size();
        a.setNumeroPostor(count + 1);
        return toDTO(asistenteRepository.save(a));
    }

    public AsistenteDTO toDTO(Asistente a) {
        AsistenteDTO dto = new AsistenteDTO();
        dto.setIdentificador(a.getIdentificador());
        dto.setNumeroPostor(a.getNumeroPostor());
        dto.setCliente(a.getCliente().getIdentificador());
        dto.setSubasta(a.getSubasta().getIdentificador());
        return dto;
    }
}
