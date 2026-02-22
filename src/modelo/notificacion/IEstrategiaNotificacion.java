package modelo.notificacion;

import modelo.Notificacion;

public interface IEstrategiaNotificacion {
    void enviar(Notificacion notificacion);
}
