package com.unomas.dto;

import com.unomas.model.Comentario;
import java.time.format.DateTimeFormatter;

public class ComentarioResponse {
    private Long id;
    private Long partidoId;
    private Long usuarioId;
    private String usuarioNombre;
    private String texto;
    private String fechaCreacion;

    public static ComentarioResponse from(Comentario c) {
        ComentarioResponse r = new ComentarioResponse();
        r.id = c.getId();
        r.partidoId = c.getPartido().getId();
        r.usuarioId = c.getUsuario().getId();
        r.usuarioNombre = c.getUsuario().getNombre();
        r.texto = c.getTexto();
        r.fechaCreacion = c.getFechaCreacion().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return r;
    }

    public Long getId() { return id; }
    public Long getPartidoId() { return partidoId; }
    public Long getUsuarioId() { return usuarioId; }
    public String getUsuarioNombre() { return usuarioNombre; }
    public String getTexto() { return texto; }
    public String getFechaCreacion() { return fechaCreacion; }
}
