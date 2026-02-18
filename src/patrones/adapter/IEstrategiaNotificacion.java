package patrones.adapter;

import modelo.Notificacion;

public interface IEstrategiaNotificacion {
    void enviarNotificacion(Notificacion notificacion);
}
