package com.unomas.model.notificacion;

import com.unomas.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class NotificacionPush implements IEstrategiaNotificacion {

    private final IAdapterPush adapterPush;

    public NotificacionPush(IAdapterPush adapterPush) {
        this.adapterPush = adapterPush;
    }

    @Override
    public void enviar(Usuario destino, String mensaje) {
        adapterPush.enviar(destino.getNombre(), mensaje);
    }

    @Override
    public String getTipo() {
        return "PUSH";
    }
}
