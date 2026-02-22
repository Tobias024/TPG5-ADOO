package modelo.notificacion;

import modelo.Notificacion;

// Envio de notificaciones push usando el adapter de push
public class NotificacionPush implements IEstrategiaNotificacion {
    private IAdapterPush adapter;

    public NotificacionPush(IAdapterPush adapter) {
        this.adapter = adapter;
    }

    @Override
    public void enviar(Notificacion notificacion) {
        adapter.enviar(notificacion);
    }
}
