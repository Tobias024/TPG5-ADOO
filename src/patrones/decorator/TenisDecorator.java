package patrones.decorator;

import modelo.IPartido;

public class TenisDecorator extends DeporteDecorator {
    private static final int JUGADORES_POR_DEFECTO = 2;

    public TenisDecorator(IPartido partidoDecorado) {
        super(partidoDecorado);
    }

    @Override
    public String getTipoDeDeporte() {
        return "Tenis";
    }

    @Override
    public int getCantJugadoresReq() {
        int cantBase = partidoDecorado.getCantJugadoresReq();
        return cantBase > 0 ? cantBase : JUGADORES_POR_DEFECTO;
    }

    @Override
    public String getDescripcion() {
        return partidoDecorado.getDescripcion() + " [Tenis - Reglas: singles o dobles]";
    }
}
