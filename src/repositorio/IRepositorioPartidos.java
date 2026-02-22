package repositorio;

import modelo.Partido;
import java.util.List;

public interface IRepositorioPartidos {
    void agregar(Partido partido);
    void eliminar(Partido partido);
    List<Partido> obtenerTodos();
    List<Partido> obtenerDisponibles();
}
