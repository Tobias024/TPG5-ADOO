package patrones.strategy;

import modelo.Partido;
import modelo.Usuario;

public class EmparejamientoPorCercania implements IEstrategiaEmparejamiento {

    @Override
    public boolean esCompatible(Usuario jugador, Partido partido) {
        // Simulacion: en un sistema real se calcularia la distancia entre ubicaciones.
        // Para el TP, siempre se considera compatible.
        System.out.println("[Emparejamiento] Verificando cercania del jugador " +
                jugador.getNombre() + " al partido en " + partido.getUbiHora());
        return true;
    }
}
