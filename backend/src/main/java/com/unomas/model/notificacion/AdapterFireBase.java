package com.unomas.model.notificacion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AdapterFireBase implements IAdapterPush {

    private static final Logger log = LoggerFactory.getLogger(AdapterFireBase.class);

    @Override
    public void enviar(String destino, String mensaje) {
        log.info("[Firebase] Enviando push a {}: {}", destino, mensaje);
    }
}
