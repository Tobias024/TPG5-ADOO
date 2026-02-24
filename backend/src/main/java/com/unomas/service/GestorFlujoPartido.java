package com.unomas.service;

import com.unomas.dto.FinalizarPartidoRequest;
import com.unomas.model.Partido;
import com.unomas.model.Usuario;
import com.unomas.model.estado.EstadoFinalizado;
import com.unomas.model.notificacion.ServicioNotificaciones;
import com.unomas.model.observador.NotificadorObserver;
import com.unomas.repository.PartidoRepository;
import org.springframework.stereotype.Service;

@Service
public class GestorFlujoPartido {

    private final ServicioEstadoPartido servicioEstadoPartido;
    private final ServicioCancelacionPartido servicioCancelacionPartido;
    private final ServicioNotificaciones servicioNotificaciones;
    private final PartidoRepository partidoRepository;
    private final ServicioUsuarios servicioUsuarios;

    public GestorFlujoPartido(ServicioEstadoPartido servicioEstadoPartido,
                               ServicioCancelacionPartido servicioCancelacionPartido,
                               ServicioNotificaciones servicioNotificaciones,
                               PartidoRepository partidoRepository,
                               ServicioUsuarios servicioUsuarios) {
        this.servicioEstadoPartido = servicioEstadoPartido;
        this.servicioCancelacionPartido = servicioCancelacionPartido;
        this.servicioNotificaciones = servicioNotificaciones;
        this.partidoRepository = partidoRepository;
        this.servicioUsuarios = servicioUsuarios;
    }

    public Partido avanzarEstado(Partido partido) {
        partido.agregarObservador(new NotificadorObserver(servicioNotificaciones));
        return servicioEstadoPartido.avanzar(partido);
    }

    public Partido cancelarPartido(Partido partido, Usuario solicitante) {
        partido.agregarObservador(new NotificadorObserver(servicioNotificaciones));
        return servicioCancelacionPartido.cancelar(partido, solicitante);
    }

    public Partido finalizarConResultado(Partido partido, FinalizarPartidoRequest request) {
        if (!"EN_JUEGO".equals(partido.getEstadoNombre())) {
            throw new IllegalStateException("Solo se puede finalizar un partido en juego");
        }

        partido.setResultadoEquipo1(request.getResultadoEquipo1());
        partido.setResultadoEquipo2(request.getResultadoEquipo2());

        if (request.getGanadorId() != null) {
            Usuario ganador = servicioUsuarios.obtenerPorId(request.getGanadorId());
            partido.setGanador(ganador);
            ganador.setVictorias(ganador.getVictorias() + 1);
        }

        partido.agregarObservador(new NotificadorObserver(servicioNotificaciones));
        partido.setEstado(new EstadoFinalizado());
        partido.notificarObservadores();

        return partidoRepository.save(partido);
    }

    /** Auto-finalize a match when its scheduled duration has elapsed (no result/winner). */
    public Partido finalizarPorTiempo(Partido partido) {
        if (!"EN_JUEGO".equals(partido.getEstadoNombre())) {
            throw new IllegalStateException("Solo se puede finalizar por tiempo un partido en juego");
        }
        partido.agregarObservador(new NotificadorObserver(servicioNotificaciones));
        partido.setEstado(new EstadoFinalizado());
        partido.notificarObservadores();
        return partidoRepository.save(partido);
    }
}
