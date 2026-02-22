package modelo.emparejamiento;

import modelo.Usuario;
import modelo.Partido;

// Filtra en base al historial de partidos previos del jugador
public class EmparejamientoPorHistorial implements IEstrategiaEmparejamiento {

    @Override
    public boolean esCompatible(Usuario jugador, Partido partido) {
        // Simplificado: se acepta a todos. En una version completa
        // se analizaria si el jugador ya jugo con los demas
        return true;
    }
}
