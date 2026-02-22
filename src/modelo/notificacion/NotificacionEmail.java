package modelo.notificacion;

import modelo.Notificacion;

// Envio de emails usando el adapter de mail
public class NotificacionEmail implements IEstrategiaNotificacion {
    private IAdapterMail adapter;

    public NotificacionEmail(IAdapterMail adapter) {
        this.adapter = adapter;
    }

    @Override
    public void enviar(Notificacion notificacion) {
        adapter.enviar(notificacion);
    }
}
