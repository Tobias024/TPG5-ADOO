package patrones.observer;

import modelo.Notificacion;
import modelo.Partido;
import modelo.Usuario;
import patrones.adapter.IEstrategiaNotificacion;

import java.util.List;

public class Notificador implements Observer {
    private IEstrategiaNotificacion estrategiaNotificacion;

    public Notificador(IEstrategiaNotificacion estrategiaNotificacion) {
        this.estrategiaNotificacion = estrategiaNotificacion;
    }

    public void cambiarEstrategia(IEstrategiaNotificacion estrategiaNotificacion) {
        this.estrategiaNotificacion = estrategiaNotificacion;
    }

    @Override
    public void notificar(ISujeto sujeto) {
        if (sujeto instanceof Partido) {
            Partido partido = (Partido) sujeto;
            String mensaje = "El partido de " + partido.getTipoDeDeporte() +
                    " cambio de estado a: " + partido.getEstado().getNombre();

            List<Usuario> jugadores = partido.getJugadores();
            for (Usuario jugador : jugadores) {
                Notificacion notificacion = new Notificacion(
                        "sistema@deportes.com",
                        jugador.getMail(),
                        mensaje);
                enviarNotificacion(notificacion);
            }
        }
    }

    public void enviarNotificacion(Notificacion notificacion) {
        estrategiaNotificacion.enviarNotificacion(notificacion);
    }
}
