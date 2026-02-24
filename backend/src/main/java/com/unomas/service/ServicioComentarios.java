package com.unomas.service;

import com.unomas.dto.ComentarioRequest;
import com.unomas.model.Comentario;
import com.unomas.model.Partido;
import com.unomas.model.Usuario;
import com.unomas.repository.ComentarioRepository;
import com.unomas.repository.PartidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicioComentarios {

    private final ComentarioRepository comentarioRepository;
    private final PartidoRepository partidoRepository;
    private final ServicioUsuarios servicioUsuarios;

    public ServicioComentarios(ComentarioRepository comentarioRepository,
                               PartidoRepository partidoRepository,
                               ServicioUsuarios servicioUsuarios) {
        this.comentarioRepository = comentarioRepository;
        this.partidoRepository = partidoRepository;
        this.servicioUsuarios = servicioUsuarios;
    }

    public List<Comentario> listarPorPartido(Long partidoId) {
        return comentarioRepository.findByPartidoIdOrderByFechaCreacionAsc(partidoId);
    }

    @Transactional
    public Comentario crear(Long partidoId, ComentarioRequest request, Usuario usuario) {
        Partido partido = partidoRepository.findById(partidoId)
            .orElseThrow(() -> new RuntimeException("Partido no encontrado"));
        if (!"FINALIZADO".equals(partido.getEstadoNombre())) {
            throw new RuntimeException("Solo se pueden comentar partidos finalizados");
        }
        String texto = request.getTexto() != null ? request.getTexto().trim() : "";
        if (texto.isEmpty()) {
            throw new RuntimeException("El comentario no puede estar vacío");
        }
        Comentario comentario = new Comentario(partido, usuario, texto);
        return comentarioRepository.save(comentario);
    }
}
