package com.unomas.service;

import com.unomas.dto.FinalizarPartidoRequest;
import com.unomas.model.Partido;
import com.unomas.model.Usuario;
import com.unomas.model.estado.EstadoFinalizado;
import com.unomas.model.notificacion.ServicioNotificaciones;
import com.unomas.model.observador.NotificadorObserver;
import com.unomas.repository.PartidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
        if ("CONFIRMADO".equals(partido.getEstadoNombre())) {
            partido.setFechaHoraInicio(LocalDateTime.now());
        }
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

    /** Auto-finalize a match when its scheduled duration has elapsed. Assigns random result and winner if none set. */
    public Partido finalizarPorTiempo(Partido partido) {
        if (!"EN_JUEGO".equals(partido.getEstadoNombre())) {
            throw new IllegalStateException("Solo se puede finalizar por tiempo un partido en juego");
        }

        // If no result was set by the user, generate a random result
        if (partido.getResultadoEquipo1() == null || partido.getResultadoEquipo2() == null) {
            int score1 = ThreadLocalRandom.current().nextInt(0, 11);
            int score2 = ThreadLocalRandom.current().nextInt(0, 11);
            partido.setResultadoEquipo1(score1);
            partido.setResultadoEquipo2(score2);

            List<Usuario> jugadores = partido.getJugadores();
            if (jugadores != null && !jugadores.isEmpty() && partido.getGanador() == null) {
                Usuario ganador = jugadores.get(ThreadLocalRandom.current().nextInt(jugadores.size()));
                partido.setGanador(ganador);
                ganador.setVictorias(ganador.getVictorias() + 1);
            }
        }

        partido.agregarObservador(new NotificadorObserver(servicioNotificaciones));
        partido.setEstado(new EstadoFinalizado());
        partido.notificarObservadores();
        return partidoRepository.save(partido);
    }
}
