package com.subastar.service;

import com.subastar.config.EntityNotFoundException;
import com.subastar.dto.MetodoPagoDTO;
import com.subastar.model.MetodoPago;
import com.subastar.repository.ClienteRepository;
import com.subastar.repository.MetodoPagoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetodoPagoService {
    private final MetodoPagoRepository metodoPagoRepository;
    private final ClienteRepository clienteRepository;

    public MetodoPagoService(MetodoPagoRepository metodoPagoRepository, ClienteRepository clienteRepository) {
        this.metodoPagoRepository = metodoPagoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<MetodoPagoDTO> listarPorCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new EntityNotFoundException("No se encontró cliente con id " + clienteId);
        }
        return metodoPagoRepository.findByCliente_Identificador(clienteId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public MetodoPago agregar(MetodoPagoDTO dto) {
        MetodoPago m = new MetodoPago();
        m.setProveedor(dto.getProveedor());
        m.setUltimosDigitos(dto.getUltimosDigitos());
        m.setCliente(clienteRepository.findById(dto.getCliente())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró cliente con id " + dto.getCliente())));
        return metodoPagoRepository.save(m);
    }

    public void eliminar(Long id) {
        if (!metodoPagoRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró método de pago con id " + id);
        }
        metodoPagoRepository.deleteById(id);
    }

    public MetodoPagoDTO toDTO(MetodoPago m) {
        MetodoPagoDTO dto = new MetodoPagoDTO();
        dto.setIdentificador(m.getIdentificador());
        dto.setProveedor(m.getProveedor());
        dto.setUltimosDigitos(m.getUltimosDigitos());
        dto.setCliente(m.getCliente().getIdentificador());
        return dto;
    }
}
