package patrones.state;

import modelo.Partido;

public class Confirmado extends StatePartido {

    @Override
    public void avanzarEstado(Partido partido) {
        partido.setEstado(new EnJuego());
        System.out.println("[Estado] El partido paso a: En Juego");
        partido.notificarObservadores();
    }

    @Override
    public String getNombre() {
        return "Confirmado";
    }
}
