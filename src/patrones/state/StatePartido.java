package patrones.state;

import modelo.Partido;

public abstract class StatePartido {

    public abstract void avanzarEstado(Partido partido);

    public abstract String getNombre();

    @Override
    public String toString() {
        return getNombre();
    }
}
