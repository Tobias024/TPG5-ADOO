package com.unomas.service;

import com.unomas.model.Partido;
import com.unomas.model.Usuario;
import com.unomas.model.notificacion.ServicioNotificaciones;
import com.unomas.model.observador.NotificadorObserver;
import com.unomas.repository.PartidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioInscripcionPartido {

    private final PartidoRepository partidoRepository;
    private final ValidadorInscripcion validadorInscripcion;
    private final ServicioEstadoPartido servicioEstadoPartido;
    private final ServicioNotificaciones servicioNotificaciones;

    public ServicioInscripcionPartido(PartidoRepository partidoRepository,
                                      ValidadorInscripcion validadorInscripcion,
                                      ServicioEstadoPartido servicioEstadoPartido,
                                      ServicioNotificaciones servicioNotificaciones) {
        this.partidoRepository = partidoRepository;
        this.validadorInscripcion = validadorInscripcion;
        this.servicioEstadoPartido = servicioEstadoPartido;
        this.servicioNotificaciones = servicioNotificaciones;
    }

    @Transactional
    public Partido inscribir(Partido partido, Usuario jugador) {
        if (!validadorInscripcion.esValida(partido, jugador)) {
            throw new RuntimeException("El jugador no cumple los requisitos de inscripción");
        }

        partido.agregarObservador(new NotificadorObserver(servicioNotificaciones));
        partido.inscribirJugador(jugador);
        partido = partidoRepository.save(partido);

        if (partido.cupoCompleto()) {
            partido = servicioEstadoPartido.avanzar(partido);
        }
        return partido;
    }
}
