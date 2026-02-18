package modelo;

public class Notificacion {
    private String emailOrigen;
    private String emailDestino;
    private String mensaje;

    public Notificacion(String emailOrigen, String emailDestino, String mensaje) {
        this.emailOrigen = emailOrigen;
        this.emailDestino = emailDestino;
        this.mensaje = mensaje;
    }

    public String getEmailOrigen() {
        return emailOrigen;
    }

    public void setEmailOrigen(String emailOrigen) {
        this.emailOrigen = emailOrigen;
    }

    public String getEmailDestino() {
        return emailDestino;
    }

    public void setEmailDestino(String emailDestino) {
        this.emailDestino = emailDestino;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Notificacion{" +
                "de='" + emailOrigen + '\'' +
                ", para='" + emailDestino + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
