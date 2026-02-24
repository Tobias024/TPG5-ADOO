package com.unomas.service;

import com.unomas.model.Partido;
import com.unomas.model.notificacion.ServicioNotificaciones;
import com.unomas.model.observador.NotificadorObserver;
import com.unomas.repository.PartidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Runs periodically to start matches when their scheduled date/time is reached
 * and to end matches after their duration has elapsed.
 */
@Service
public class PartidoSchedulerService {

    private static final Logger log = LoggerFactory.getLogger(PartidoSchedulerService.class);
    private static final Pattern DURATION_MINUTES = Pattern.compile("(\\d+)\\s*min");

    private final PartidoRepository partidoRepository;
    private final ServicioEstadoPartido servicioEstadoPartido;
    private final GestorFlujoPartido gestorFlujoPartido;
    private final ServicioNotificaciones servicioNotificaciones;

    public PartidoSchedulerService(PartidoRepository partidoRepository,
                                    ServicioEstadoPartido servicioEstadoPartido,
                                    GestorFlujoPartido gestorFlujoPartido,
                                    ServicioNotificaciones servicioNotificaciones) {
        this.partidoRepository = partidoRepository;
        this.servicioEstadoPartido = servicioEstadoPartido;
        this.gestorFlujoPartido = gestorFlujoPartido;
        this.servicioNotificaciones = servicioNotificaciones;
    }

    @Scheduled(fixedRate = 30000) // every 30 seconds (good for 2-min test duration)
    @Transactional
    public void procesarPartidosProgramados() {
        LocalDateTime now = LocalDateTime.now();

        // Start games whose scheduled date/time has arrived (CONFIRMADO -> EN_JUEGO)
        List<Partido> paraIniciar = partidoRepository.findConfirmadosParaIniciar(now);
        for (Partido p : paraIniciar) {
            try {
                p.agregarObservador(new NotificadorObserver(servicioNotificaciones));
                Partido actualizado = servicioEstadoPartido.avanzar(p);
                partidoRepository.save(actualizado);
                log.info("Partido {} iniciado automáticamente (fecha/hora alcanzada)", p.getId());
            } catch (Exception e) {
                log.warn("Error al iniciar partido {} automáticamente: {}", p.getId(), e.getMessage());
            }
        }

        // End games whose duration has elapsed (EN_JUEGO -> FINALIZADO)
        List<Partido> enJuego = partidoRepository.findByEstadoNombre("EN_JUEGO");
        for (Partido p : enJuego) {
            try {
                int minutos = parseDuracionMinutos(p.getDuracion());
                LocalDateTime finPrevisto = p.getFechaHora().plusMinutes(minutos);
                if (!now.isBefore(finPrevisto)) {
                    gestorFlujoPartido.finalizarPorTiempo(p);
                    log.info("Partido {} finalizado automáticamente (duración cumplida)", p.getId());
                }
            } catch (Exception e) {
                log.warn("Error al finalizar partido {} por tiempo: {}", p.getId(), e.getMessage());
            }
        }
    }

    private static int parseDuracionMinutos(String duracion) {
        if (duracion == null || duracion.isBlank()) return 60;
        var m = DURATION_MINUTES.matcher(duracion.trim());
        return m.find() ? Integer.parseInt(m.group(1)) : 60;
    }
}
