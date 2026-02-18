package datos;

import modelo.Partido;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioPartidos {
    private List<Partido> listaPartidos;

    public RepositorioPartidos() {
        this.listaPartidos = new ArrayList<>();
    }

    public void agregar(Partido partido) {
        listaPartidos.add(partido);
    }

    public void eliminar(Partido partido) {
        listaPartidos.remove(partido);
    }

    public List<Partido> obtenerTodos() {
        return new ArrayList<>(listaPartidos);
    }

    public List<Partido> buscarDisponibles() {
        return listaPartidos.stream()
                .filter(p -> p.getEstado().getNombre().equals("Necesitamos jugadores"))
                .collect(Collectors.toList());
    }
}
