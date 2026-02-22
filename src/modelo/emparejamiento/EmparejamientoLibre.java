package modelo.emparejamiento;

import modelo.Usuario;
import modelo.Partido;

// Permite que cualquier jugador entre sin restriccion de nivel
public class EmparejamientoLibre implements IEstrategiaEmparejamiento {

    @Override
    public boolean esCompatible(Usuario jugador, Partido partido) {
        return true;
    }
}
