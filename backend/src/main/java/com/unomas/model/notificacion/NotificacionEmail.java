package com.unomas.model.notificacion;

import com.unomas.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class NotificacionEmail implements IEstrategiaNotificacion {

    private final IAdapterMail adapterMail;

    public NotificacionEmail(IAdapterMail adapterMail) {
        this.adapterMail = adapterMail;
    }

    @Override
    public void enviar(Usuario destino, String mensaje) {
        adapterMail.enviar(destino.getMail(), mensaje);
    }

    @Override
    public String getTipo() {
        return "EMAIL";
    }
}
