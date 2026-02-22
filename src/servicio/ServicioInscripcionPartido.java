package servicio;

import modelo.Partido;
import modelo.Usuario;

// Se encarga de la logica de inscripcion: valida y agrega al jugador
public class ServicioInscripcionPartido {
    private ValidadorInscripcion validador;

    public ServicioInscripcionPartido(ValidadorInscripcion validador) {
        this.validador = validador;
    }

    public boolean inscribir(Partido partido, Usuario jugador) {
        if (!validador.esValida(partido, jugador)) {
            return false;
        }

        boolean agregado = partido.getCupo().agregar(jugador);
        if (agregado) {
            // Si con este jugador se completo el cupo, avanza el estado
            partido.avanzarEstado();
        }
        return agregado;
    }
}
