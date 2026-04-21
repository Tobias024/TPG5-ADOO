package com.subastar.service;

import com.subastar.config.EntityNotFoundException;
import com.subastar.dto.ItemCatalogoDTO;
import com.subastar.model.ItemCatalogo;
import com.subastar.repository.ItemCatalogoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemCatalogoService {
    private final ItemCatalogoRepository itemRepo;
    public ItemCatalogoService(ItemCatalogoRepository itemRepo) { this.itemRepo = itemRepo; }

    public List<ItemCatalogoDTO> listarTodos() {
        return itemRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ItemCatalogoDTO obtener(Long id) {
        return toDTO(itemRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró ítem con id " + id)));
    }

    public ItemCatalogoDTO toDTO(ItemCatalogo i) {
        ItemCatalogoDTO dto = new ItemCatalogoDTO();
        dto.setIdentificador(i.getIdentificador());
        dto.setPrecioBase(i.getPrecioBase());
        dto.setComision(i.getComision());
        dto.setSubastado(i.getSubastado());
        if (i.getCatalogo() != null) dto.setCatalogo(i.getCatalogo().getIdentificador());
        if (i.getProducto() != null) dto.setProducto(i.getProducto().getIdentificador());
        return dto;
    }
}
