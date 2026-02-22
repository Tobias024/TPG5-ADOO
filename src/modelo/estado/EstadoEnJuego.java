package modelo.estado;

import modelo.Partido;

// El partido esta en curso
public class EstadoEnJuego implements IEstadoPartido {

    @Override
    public void avanzar(Partido partido) {
        partido.setEstado(new EstadoFinalizado());
    }

    @Override
    public String getNombre() {
        return "En juego";
    }
}
