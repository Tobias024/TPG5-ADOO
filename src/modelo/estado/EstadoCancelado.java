package modelo.estado;

import modelo.Partido;

// El organizador cancelo el partido
public class EstadoCancelado implements IEstadoPartido {

    @Override
    public void avanzar(Partido partido) {
        // No hay transicion desde cancelado
    }

    @Override
    public String getNombre() {
        return "Cancelado";
    }
}
