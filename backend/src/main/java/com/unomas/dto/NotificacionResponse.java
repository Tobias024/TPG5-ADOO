package com.unomas.dto;

import com.unomas.model.Notificacion;
import java.time.format.DateTimeFormatter;

public class NotificacionResponse {
    private Long id;
    private String mensaje;
    private String tipo;
    private String fechaEnvio;
    private boolean leida;

    public static NotificacionResponse from(Notificacion n) {
        NotificacionResponse r = new NotificacionResponse();
        r.id = n.getId();
        r.mensaje = n.getMensaje();
        r.tipo = n.getTipo();
        r.fechaEnvio = n.getFechaEnvio().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        r.leida = n.isLeida();
        return r;
    }

    public Long getId() { return id; }
    public String getMensaje() { return mensaje; }
    public String getTipo() { return tipo; }
    public String getFechaEnvio() { return fechaEnvio; }
    public boolean isLeida() { return leida; }
}
