package servicio;

import modelo.Partido;
import modelo.Usuario;

// Valida que un jugador pueda inscribirse a un partido
public class ValidadorInscripcion {

    public boolean esValida(Partido partido, Usuario jugador) {
        // No puede inscribirse si el cupo esta lleno
        if (partido.getCupo().estaCompleto()) {
            return false;
        }

        // No puede inscribirse si ya esta inscripto
        if (partido.getCupo().getJugadores().contains(jugador)) {
            return false;
        }

        // Tiene que cumplir la estrategia de emparejamiento del partido
        if (partido.getEstrategia() != null) {
            return partido.getEstrategia().esCompatible(jugador, partido);
        }

        return true;
    }
}
