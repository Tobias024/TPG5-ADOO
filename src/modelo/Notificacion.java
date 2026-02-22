package modelo;

// Representa el contenido de una notificacion (datos puros)
public class Notificacion {
    private String destino;
    private String mensaje;

    public Notificacion(String destino, String mensaje) {
        this.destino = destino;
        this.mensaje = mensaje;
    }

    public String getDestino() {
        return destino;
    }

    public String getMensaje() {
        return mensaje;
    }
}
