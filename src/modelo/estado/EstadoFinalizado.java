package modelo.estado;

import modelo.Partido;

// El partido termino, ya no se puede hacer nada
public class EstadoFinalizado implements IEstadoPartido {

    @Override
    public void avanzar(Partido partido) {
        // No hay transicion desde finalizado
    }

    @Override
    public String getNombre() {
        return "Finalizado";
    }
}
