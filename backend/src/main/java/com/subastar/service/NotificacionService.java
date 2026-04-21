package com.subastar.service;

import com.subastar.config.EntityNotFoundException;
import com.subastar.dto.NotificacionDTO;
import com.subastar.model.Notificacion;
import com.subastar.repository.ClienteRepository;
import com.subastar.repository.NotificacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionService {
    private final NotificacionRepository notificacionRepository;
    private final ClienteRepository clienteRepository;

    public NotificacionService(NotificacionRepository notificacionRepository, ClienteRepository clienteRepository) {
        this.notificacionRepository = notificacionRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<NotificacionDTO> listarPorCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new EntityNotFoundException("No se encontró cliente con id " + clienteId);
        }
        return notificacionRepository.findByCliente_IdentificadorOrderByFechaDesc(clienteId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public NotificacionDTO toDTO(Notificacion n) {
        NotificacionDTO dto = new NotificacionDTO();
        dto.setIdentificador(n.getIdentificador());
        dto.setTipo(n.getTipo());
        dto.setMensaje(n.getMensaje());
        dto.setLeida(n.getLeida());
        dto.setFecha(n.getFecha());
        dto.setCliente(n.getCliente().getIdentificador());
        return dto;
    }
}
