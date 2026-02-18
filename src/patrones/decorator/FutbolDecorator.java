package patrones.decorator;

import modelo.IPartido;

public class FutbolDecorator extends DeporteDecorator {
    private static final int JUGADORES_POR_DEFECTO = 22;

    public FutbolDecorator(IPartido partidoDecorado) {
        super(partidoDecorado);
    }

    @Override
    public String getTipoDeDeporte() {
        return "Futbol";
    }

    @Override
    public int getCantJugadoresReq() {
        int cantBase = partidoDecorado.getCantJugadoresReq();
        // Si no se especifico una cantidad, usar el default del deporte
        return cantBase > 0 ? cantBase : JUGADORES_POR_DEFECTO;
    }

    @Override
    public String getDescripcion() {
        return partidoDecorado.getDescripcion() + " [Futbol - Reglas: 2 equipos, cancha de cesped]";
    }
}
