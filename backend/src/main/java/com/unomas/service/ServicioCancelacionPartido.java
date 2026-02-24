package com.unomas.service;

import com.unomas.model.Partido;
import com.unomas.model.Usuario;
import com.unomas.repository.PartidoRepository;
import org.springframework.stereotype.Service;

@Service
public class ServicioCancelacionPartido {

    private final PartidoRepository partidoRepository;

    public ServicioCancelacionPartido(PartidoRepository partidoRepository) {
        this.partidoRepository = partidoRepository;
    }

    public Partido cancelar(Partido partido, Usuario solicitante) {
        if (!solicitante.getId().equals(partido.getOrganizador().getId())) {
            throw new RuntimeException("Solo el organizador puede cancelar el partido");
        }
        String estado = partido.getEstadoNombre();
        if ("FINALIZADO".equals(estado) || "CANCELADO".equals(estado) || "EN_JUEGO".equals(estado)) {
            throw new RuntimeException("No se puede cancelar un partido en estado: " + estado);
        }
        partido.cancelar(solicitante);
        return partidoRepository.save(partido);
    }
}
