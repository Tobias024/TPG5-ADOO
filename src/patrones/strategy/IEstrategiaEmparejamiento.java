package patrones.strategy;

import modelo.Partido;
import modelo.Usuario;

public interface IEstrategiaEmparejamiento {
    boolean esCompatible(Usuario jugador, Partido partido);
}
