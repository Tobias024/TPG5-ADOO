package patrones.adapter;

import modelo.Notificacion;

public class NotificacionEmail implements IEstrategiaNotificacion {
    private IAdapterMail adapterMail;

    public NotificacionEmail(IAdapterMail adapterMail) {
        this.adapterMail = adapterMail;
    }

    @Override
    public void enviarNotificacion(Notificacion notificacion) {
        adapterMail.enviar(notificacion);
    }
}
