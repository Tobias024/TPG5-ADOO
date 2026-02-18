package patrones.state;

import modelo.Partido;

public class FaltanJugadores extends StatePartido {

    @Override
    public void avanzarEstado(Partido partido) {
        if (partido.getJugadores().size() >= partido.getCantJugadoresReq()) {
            partido.setEstado(new PartidoArmado());
            System.out.println("[Estado] El partido paso a: Partido Armado");
            partido.notificarObservadores();
        } else {
            System.out.println("[Estado] Aun faltan jugadores (" +
                    partido.getJugadores().size() + "/" + partido.getCantJugadoresReq() + ")");
        }
    }

    @Override
    public String getNombre() {
        return "Necesitamos jugadores";
    }
}
