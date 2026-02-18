package patrones.adapter;

import modelo.Notificacion;

public interface IAdapterMail {
    void enviar(Notificacion notificacion);
}
