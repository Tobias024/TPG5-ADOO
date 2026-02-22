package modelo.emparejamiento;

import modelo.Usuario;
import modelo.Partido;

// Solo deja entrar jugadores que tengan el nivel minimo requerido
public class EmparejamientoPorNivel implements IEstrategiaEmparejamiento {

    @Override
    public boolean esCompatible(Usuario jugador, Partido partido) {
        if (jugador.getNivel() == null || partido.getNivelMinimo() == null) {
            return true;
        }
        return jugador.getNivel().getValor() >= partido.getNivelMinimo().getValor();
    }
}
