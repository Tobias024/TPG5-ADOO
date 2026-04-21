package com.subastar.service;

import com.subastar.config.EntityNotFoundException;
import com.subastar.dto.FotoDTO;
import com.subastar.model.Foto;
import com.subastar.repository.FotoRepository;
import com.subastar.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FotoService {
    private final FotoRepository fotoRepository;
    private final ProductoRepository productoRepository;

    public FotoService(FotoRepository fotoRepository, ProductoRepository productoRepository) {
        this.fotoRepository = fotoRepository;
        this.productoRepository = productoRepository;
    }

    public List<FotoDTO> listarPorProducto(Long productoId) {
        if (!productoRepository.existsById(productoId)) {
            throw new EntityNotFoundException("No se encontró producto con id " + productoId);
        }
        return fotoRepository.findByProducto_Identificador(productoId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Foto subir(FotoDTO dto) {
        Foto foto = new Foto();
        foto.setFoto(dto.getFoto());
        foto.setProducto(productoRepository.findById(dto.getProducto())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró producto con id " + dto.getProducto())));
        return fotoRepository.save(foto);
    }

    public FotoDTO toDTO(Foto f) {
        FotoDTO dto = new FotoDTO();
        dto.setIdentificador(f.getIdentificador());
        dto.setFoto(f.getFoto());
        dto.setProducto(f.getProducto().getIdentificador());
        return dto;
    }
}
