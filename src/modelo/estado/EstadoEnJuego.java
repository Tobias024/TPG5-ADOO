package modelo.estado;

import modelo.Partido;
import modelo.Usuario;

// El partido esta en curso
public class EstadoEnJuego implements IEstadoPartido {

    @Override
    public void avanzar(Partido partido) {
        // Al terminar el partido, todos los jugadores suman experiencia
        for (Usuario jugador : partido.getCupo().getJugadores()) {
            jugador.registrarVictoria();
        }
        partido.setEstado(new EstadoFinalizado());
    }

    @Override
    public String getNombre() {
        return "En juego";
    }
}
