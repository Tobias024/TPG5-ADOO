package patrones.strategy;

import modelo.Partido;
import modelo.Usuario;

public class EmparejamientoPorNivel implements IEstrategiaEmparejamiento {

    @Override
    public boolean esCompatible(Usuario jugador, Partido partido) {
        if (partido.getNivel() == null) {
            // Si el partido no tiene nivel requerido, cualquier jugador es compatible
            return true;
        }
        // Compatible si el nivel del jugador coincide con el del partido
        return jugador.getNivel() == partido.getNivel();
    }
}
