package patrones.state;

import modelo.Partido;

public class PartidoArmado extends StatePartido {

    @Override
    public void avanzarEstado(Partido partido) {
        partido.setEstado(new Confirmado());
        System.out.println("[Estado] El partido paso a: Confirmado");
        partido.notificarObservadores();
    }

    @Override
    public String getNombre() {
        return "Partido armado";
    }
}
