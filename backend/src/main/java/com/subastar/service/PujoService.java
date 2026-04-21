package com.subastar.service;

import com.subastar.config.EntityNotFoundException;
import com.subastar.dto.PujoDTO;
import com.subastar.model.Pujo;
import com.subastar.repository.AsistenteRepository;
import com.subastar.repository.ItemCatalogoRepository;
import com.subastar.repository.PujoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PujoService {
    private final PujoRepository pujoRepository;
    private final AsistenteRepository asistenteRepository;
    private final ItemCatalogoRepository itemRepo;

    public PujoService(PujoRepository pujoRepository, AsistenteRepository asistenteRepository, ItemCatalogoRepository itemRepo) {
        this.pujoRepository = pujoRepository;
        this.asistenteRepository = asistenteRepository;
        this.itemRepo = itemRepo;
    }

    public List<PujoDTO> listarTodos() {
        return pujoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PujoDTO crear(PujoDTO dto) {
        List<Pujo> existentes = pujoRepository.findByItem_IdentificadorOrderByImporteDesc(dto.getItem());
        if (!existentes.isEmpty() && dto.getImporte() <= existentes.get(0).getImporte()) {
            throw new IllegalArgumentException("El importe debe ser mayor a la puja actual: " + existentes.get(0).getImporte());
        }
        Pujo p = new Pujo();
        p.setAsistente(asistenteRepository.findById(dto.getAsistente())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró asistente con id " + dto.getAsistente())));
        p.setItem(itemRepo.findById(dto.getItem())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró ítem con id " + dto.getItem())));
        p.setImporte(dto.getImporte());
        return toDTO(pujoRepository.save(p));
    }

    public PujoDTO toDTO(Pujo p) {
        PujoDTO dto = new PujoDTO();
        dto.setIdentificador(p.getIdentificador());
        dto.setImporte(p.getImporte());
        dto.setGanador(p.getGanador());
        dto.setAsistente(p.getAsistente().getIdentificador());
        dto.setItem(p.getItem().getIdentificador());
        return dto;
    }
}
