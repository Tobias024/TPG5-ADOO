package modelo;

import java.util.ArrayList;
import java.util.List;

public class CupoPartido {
    private int cantRequerida;
    private List<Usuario> jugadores;

    public CupoPartido(int cantRequerida) {
        this.cantRequerida = cantRequerida;
        this.jugadores = new ArrayList<>();
    }

    // Agrega un jugador si todavia hay lugar
    public boolean agregar(Usuario jugador) {
        if (estaCompleto()) {
            return false;
        }
        jugadores.add(jugador);
        return true;
    }

    public boolean estaCompleto() {
        return jugadores.size() >= cantRequerida;
    }

    public int getCantRequerida() {
        return cantRequerida;
    }

    public List<Usuario> getJugadores() {
        return jugadores;
    }
}
