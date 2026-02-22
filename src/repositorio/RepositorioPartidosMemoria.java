package repositorio;

import modelo.Partido;
import modelo.estado.EstadoFaltanJugadores;
import java.util.ArrayList;
import java.util.List;

// Repositorio en memoria, sin base de datos
public class RepositorioPartidosMemoria implements IRepositorioPartidos {
    private List<Partido> partidos;

    public RepositorioPartidosMemoria() {
        this.partidos = new ArrayList<>();
    }

    @Override
    public void agregar(Partido partido) {
        partidos.add(partido);
    }

    @Override
    public void eliminar(Partido partido) {
        partidos.remove(partido);
    }

    @Override
    public List<Partido> obtenerTodos() {
        return new ArrayList<>(partidos);
    }

    @Override
    public List<Partido> obtenerDisponibles() {
        // Solo los que todavia necesitan jugadores
        List<Partido> disponibles = new ArrayList<>();
        for (Partido p : partidos) {
            if (p.getEstado() instanceof EstadoFaltanJugadores) {
                disponibles.add(p);
            }
        }
        return disponibles;
    }
}
