package servicio;

import modelo.Partido;

// Avanza el estado del partido al siguiente
public class ServicioEstadoPartido {

    public void avanzar(Partido partido) {
        partido.avanzarEstado();
    }
}
