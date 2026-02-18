package patrones.state;

import modelo.Partido;

public class EnJuego extends StatePartido {

    @Override
    public void avanzarEstado(Partido partido) {
        partido.setEstado(new Finalizado());
        System.out.println("[Estado] El partido paso a: Finalizado");
        partido.notificarObservadores();
    }

    @Override
    public String getNombre() {
        return "En juego";
    }
}
