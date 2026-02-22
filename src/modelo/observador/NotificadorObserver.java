package modelo.observador;

import modelo.Notificacion;
import modelo.Partido;
import modelo.notificacion.ServicioNotificaciones;

// Observador concreto: cuando recibe una notificacion del sujeto,
// arma el mensaje y lo envia a traves del servicio de notificaciones
public class NotificadorObserver implements IObserver {
    private ServicioNotificaciones servicioNotificaciones;

    public NotificadorObserver(ServicioNotificaciones servicioNotificaciones) {
        this.servicioNotificaciones = servicioNotificaciones;
    }

    @Override
    public void notificar(ISujeto sujeto) {
        if (sujeto instanceof Partido) {
            Partido partido = (Partido) sujeto;
            String estado = partido.getEstado().getNombre();
            String mensaje = "El partido de " + partido.getDeporte().getNombre()
                    + " cambio a estado: " + estado;

            // Le avisa a cada jugador inscripto
            for (var jugador : partido.getCupo().getJugadores()) {
                Notificacion noti = new Notificacion(jugador.getMail(), mensaje);
                servicioNotificaciones.enviar(noti);
            }
        }
    }
}
