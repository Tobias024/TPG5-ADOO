package modelo.estado;

import modelo.Partido;

// Estado inicial: el partido necesita jugadores para completar el cupo
public class EstadoFaltanJugadores implements IEstadoPartido {

    @Override
    public void avanzar(Partido partido) {
        // Si se lleno el cupo, pasa a armado
        if (partido.getCupo().estaCompleto()) {
            partido.setEstado(new EstadoArmado());
        }
    }

    @Override
    public String getNombre() {
        return "Necesitamos jugadores";
    }
}
