package com.subastar.dto;
import com.subastar.model.enums.SiNo;
import com.subastar.model.enums.TipoNotificacion;
import java.time.LocalDateTime;
public class NotificacionDTO {
    private Long identificador;
    private Long cliente;
    private TipoNotificacion tipo;
    private String mensaje;
    private SiNo leida;
    private LocalDateTime fecha;
    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public Long getCliente() { return cliente; }
    public void setCliente(Long cliente) { this.cliente = cliente; }
    public TipoNotificacion getTipo() { return tipo; }
    public void setTipo(TipoNotificacion tipo) { this.tipo = tipo; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public SiNo getLeida() { return leida; }
    public void setLeida(SiNo leida) { this.leida = leida; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
