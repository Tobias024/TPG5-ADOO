package patrones.adapter;

import modelo.Notificacion;

public interface IAdapterFirebase {
    void enviar(Notificacion notificacion);
}
