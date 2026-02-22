package vista;

import modelo.Partido;
import java.util.List;

public interface IVista {
    void mostrarPartidosDisponibles(List<Partido> partidos);
    void mostrarMensaje(String mensaje);
    void mostrarEstadoPartido(Partido partido);
}
