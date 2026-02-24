package com.unomas.model.notificacion;

import com.unomas.model.Usuario;

public interface IEstrategiaNotificacion {
    void enviar(Usuario destino, String mensaje);
    String getTipo();
}
