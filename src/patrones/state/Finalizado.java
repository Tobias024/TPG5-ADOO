package patrones.state;

import modelo.Partido;

public class Finalizado extends StatePartido {

    @Override
    public void avanzarEstado(Partido partido) {
        System.out.println("[Estado] El partido ya esta finalizado, no se puede avanzar.");
    }

    @Override
    public String getNombre() {
        return "Finalizado";
    }
}
