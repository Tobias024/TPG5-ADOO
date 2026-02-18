package patrones.state;

import modelo.Partido;

public class Cancelado extends StatePartido {

    @Override
    public void avanzarEstado(Partido partido) {
        System.out.println("[Estado] El partido esta cancelado, no se puede avanzar.");
    }

    @Override
    public String getNombre() {
        return "Cancelado";
    }
}
