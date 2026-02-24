package com.unomas.model.observador;

import com.unomas.model.Partido;
import com.unomas.model.Usuario;
import com.unomas.model.notificacion.ServicioNotificaciones;

public class NotificadorObserver implements IObserver {

    private final ServicioNotificaciones servicioNotificaciones;

    public NotificadorObserver(ServicioNotificaciones servicioNotificaciones) {
        this.servicioNotificaciones = servicioNotificaciones;
    }

    @Override
    public void notificar(ISujeto sujeto) {
        if (sujeto instanceof Partido partido) {
            String mensaje = "El partido de " + partido.getDeporte().getNombre() +
                " cambió a estado: " + partido.getEstadoNombre();
            for (Usuario jugador : partido.getJugadores()) {
                servicioNotificaciones.enviarATodos(jugador, mensaje);
            }
        }
    }
}
