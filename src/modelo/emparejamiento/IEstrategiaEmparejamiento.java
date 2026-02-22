package modelo.emparejamiento;

import modelo.Usuario;
import modelo.Partido;

public interface IEstrategiaEmparejamiento {
    boolean esCompatible(Usuario jugador, Partido partido);
}
