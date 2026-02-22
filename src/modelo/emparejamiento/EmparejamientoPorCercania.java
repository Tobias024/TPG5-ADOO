package modelo.emparejamiento;

import modelo.Usuario;
import modelo.Partido;

// Filtra jugadores por cercania geografica (simplificado para el TP)
public class EmparejamientoPorCercania implements IEstrategiaEmparejamiento {

    @Override
    public boolean esCompatible(Usuario jugador, Partido partido) {
        // Por ahora se acepta a todos, la logica real dependeria
        // de la ubicacion del jugador y del partido
        return true;
    }
}
