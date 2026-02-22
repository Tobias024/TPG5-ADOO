package modelo.estado;

import modelo.Partido;

public interface IEstadoPartido {
    void avanzar(Partido partido);
    String getNombre();
}
