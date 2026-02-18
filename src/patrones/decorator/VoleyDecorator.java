package patrones.decorator;

import modelo.IPartido;

public class VoleyDecorator extends DeporteDecorator {
    private static final int JUGADORES_POR_DEFECTO = 12;

    public VoleyDecorator(IPartido partidoDecorado) {
        super(partidoDecorado);
    }

    @Override
    public String getTipoDeDeporte() {
        return "Voley";
    }

    @Override
    public int getCantJugadoresReq() {
        int cantBase = partidoDecorado.getCantJugadoresReq();
        return cantBase > 0 ? cantBase : JUGADORES_POR_DEFECTO;
    }

    @Override
    public String getDescripcion() {
        return partidoDecorado.getDescripcion() + " [Voley - Reglas: 2 equipos, red divisoria]";
    }
}
