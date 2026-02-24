package com.unomas.service;

import com.unomas.dto.PartidoRequest;
import com.unomas.model.Deporte;
import com.unomas.model.Partido;
import com.unomas.model.Usuario;
import com.unomas.model.notificacion.ServicioNotificaciones;
import com.unomas.repository.DeporteRepository;
import com.unomas.repository.PartidoRepository;
import com.unomas.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicioPartidos {

    private final PartidoRepository partidoRepository;
    private final DeporteRepository deporteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ServicioNotificaciones servicioNotificaciones;

    public ServicioPartidos(PartidoRepository partidoRepository,
                            DeporteRepository deporteRepository,
                            UsuarioRepository usuarioRepository,
                            ServicioNotificaciones servicioNotificaciones) {
        this.partidoRepository = partidoRepository;
        this.deporteRepository = deporteRepository;
        this.usuarioRepository = usuarioRepository;
        this.servicioNotificaciones = servicioNotificaciones;
    }

    @Transactional
    public Partido crear(PartidoRequest req, Usuario organizador) {
        Deporte deporte = deporteRepository.findById(req.getDeporteId())
            .orElseThrow(() -> new RuntimeException("Deporte no encontrado"));

        Partido partido = new Partido();
        partido.setDeporte(deporte);
        partido.setOrganizador(organizador);
        partido.setUbicacion(req.getUbicacion());
        partido.setFechaHora(LocalDateTime.parse(req.getFechaHora()));
        partido.setDuracion(req.getDuracion());
        partido.setCantidadJugadores(req.getCantidadJugadores());
        partido.setNivelMinimo(req.getNivelMinimo());
        partido.setNivelMaximo(req.getNivelMaximo());
        partido.setEstrategiaEmparejamiento(req.getEstrategiaEmparejamiento());

        partido = partidoRepository.save(partido);

        notificarNuevoPartido(deporte, partido);
        return partido;
    }

    private void notificarNuevoPartido(Deporte deporte, Partido partido) {
        List<Usuario> interesados = usuarioRepository.findAll().stream()
            .filter(u -> u.getDeporteFavorito() != null &&
                         u.getDeporteFavorito().getId().equals(deporte.getId()) &&
                         !u.getId().equals(partido.getOrganizador().getId()))
            .toList();

        String mensaje = "Nuevo partido de " + deporte.getNombre() + " en " + partido.getUbicacion();
        for (Usuario u : interesados) {
            servicioNotificaciones.enviarATodos(u, mensaje);
        }
    }

    public List<Partido> listar() {
        return partidoRepository.findAll();
    }

    public List<Partido> buscarPorDeporte(Long deporteId) {
        return partidoRepository.findByDeporteId(deporteId);
    }

    public List<Partido> buscarDisponibles(Long deporteId) {
        return partidoRepository.findDisponiblesPorDeporte(deporteId);
    }

    public Partido obtenerPorId(Long id) {
        return partidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Partido no encontrado"));
    }

    public List<Partido> obtenerPorUsuario(Long userId) {
        return partidoRepository.findByJugadorId(userId);
    }

    public List<Partido> obtenerOrganizadosPor(Long userId) {
        return partidoRepository.findByOrganizadorId(userId);
    }
}
