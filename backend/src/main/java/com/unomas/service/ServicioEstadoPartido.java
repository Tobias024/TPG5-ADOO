package com.unomas.service;

import com.unomas.model.Partido;
import com.unomas.repository.PartidoRepository;
import org.springframework.stereotype.Service;

@Service
public class ServicioEstadoPartido {

    private final PartidoRepository partidoRepository;

    public ServicioEstadoPartido(PartidoRepository partidoRepository) {
        this.partidoRepository = partidoRepository;
    }

    public Partido avanzar(Partido partido) {
        partido.avanzarEstado();
        return partidoRepository.save(partido);
    }
}
