package modelo.estado;

import modelo.Partido;

// Todos los jugadores confirmaron, listo para jugarse
public class EstadoConfirmado implements IEstadoPartido {

    @Override
    public void avanzar(Partido partido) {
        partido.setEstado(new EstadoEnJuego());
    }

    @Override
    public String getNombre() {
        return "Confirmado";
    }
}
