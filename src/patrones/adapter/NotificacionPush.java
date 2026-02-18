package patrones.adapter;

import modelo.Notificacion;

public class NotificacionPush implements IEstrategiaNotificacion {
    private IAdapterFirebase adapterFirebase;

    public NotificacionPush(IAdapterFirebase adapterFirebase) {
        this.adapterFirebase = adapterFirebase;
    }

    @Override
    public void enviarNotificacion(Notificacion notificacion) {
        adapterFirebase.enviar(notificacion);
    }
}
