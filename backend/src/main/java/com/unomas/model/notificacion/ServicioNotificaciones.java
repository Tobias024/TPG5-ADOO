package com.unomas.model.notificacion;

import com.unomas.model.Notificacion;
import com.unomas.model.Usuario;
import com.unomas.repository.NotificacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioNotificaciones {

    private final List<IEstrategiaNotificacion> estrategias;
    private final NotificacionRepository notificacionRepository;

    public ServicioNotificaciones(List<IEstrategiaNotificacion> estrategias,
                                  NotificacionRepository notificacionRepository) {
        this.estrategias = estrategias;
        this.notificacionRepository = notificacionRepository;
    }

    public void enviarATodos(Usuario destino, String mensaje) {
        for (IEstrategiaNotificacion estrategia : estrategias) {
            estrategia.enviar(destino, mensaje);
            Notificacion notif = new Notificacion(destino, mensaje, estrategia.getTipo());
            notificacionRepository.save(notif);
        }
    }
}
