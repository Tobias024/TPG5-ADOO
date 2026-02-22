package modelo.notificacion;

import modelo.Notificacion;

// Servicio central de notificaciones, delega al estrategia activa
public class ServicioNotificaciones {
    private IEstrategiaNotificacion estrategia;

    public ServicioNotificaciones(IEstrategiaNotificacion estrategia) {
        this.estrategia = estrategia;
    }

    public void cambiarEstrategia(IEstrategiaNotificacion estrategia) {
        this.estrategia = estrategia;
    }

    public void enviar(Notificacion notificacion) {
        estrategia.enviar(notificacion);
    }
}
