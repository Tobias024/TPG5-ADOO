package patrones.decorator;

import modelo.IPartido;

public class BasquetDecorator extends DeporteDecorator {
    private static final int JUGADORES_POR_DEFECTO = 10;

    public BasquetDecorator(IPartido partidoDecorado) {
        super(partidoDecorado);
    }

    @Override
    public String getTipoDeDeporte() {
        return "Basquet";
    }

    @Override
    public int getCantJugadoresReq() {
        int cantBase = partidoDecorado.getCantJugadoresReq();
        return cantBase > 0 ? cantBase : JUGADORES_POR_DEFECTO;
    }

    @Override
    public String getDescripcion() {
        return partidoDecorado.getDescripcion() + " [Basquet - Reglas: 2 equipos, cancha cubierta]";
    }
}
