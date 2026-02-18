package patrones.decorator;

import modelo.IPartido;
import modelo.Nivel;

public abstract class DeporteDecorator implements IPartido {
    protected IPartido partidoDecorado;

    public DeporteDecorator(IPartido partidoDecorado) {
        this.partidoDecorado = partidoDecorado;
    }

    @Override
    public String getTipoDeDeporte() {
        return partidoDecorado.getTipoDeDeporte();
    }

    @Override
    public int getCantJugadoresReq() {
        return partidoDecorado.getCantJugadoresReq();
    }

    @Override
    public String getDuracion() {
        return partidoDecorado.getDuracion();
    }

    @Override
    public String getUbiHora() {
        return partidoDecorado.getUbiHora();
    }

    @Override
    public Nivel getNivel() {
        return partidoDecorado.getNivel();
    }

    @Override
    public String getDescripcion() {
        return partidoDecorado.getDescripcion();
    }
}
