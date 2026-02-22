package servicio;

import modelo.Partido;
import repositorio.IRepositorioPartidos;
import java.util.List;

// Servicio de partidos: guardar, listar y filtrar disponibles
public class ServicioPartidos {
    private IRepositorioPartidos repositorioPartidos;

    public ServicioPartidos(IRepositorioPartidos repositorioPartidos) {
        this.repositorioPartidos = repositorioPartidos;
    }

    public void guardar(Partido partido) {
        repositorioPartidos.agregar(partido);
    }

    public List<Partido> listar() {
        return repositorioPartidos.obtenerTodos();
    }

    public List<Partido> listarDisponibles() {
        return repositorioPartidos.obtenerDisponibles();
    }
}
