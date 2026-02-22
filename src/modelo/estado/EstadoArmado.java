package modelo.estado;

import modelo.Partido;

// El cupo esta completo, se espera confirmacion
public class EstadoArmado implements IEstadoPartido {

    @Override
    public void avanzar(Partido partido) {
        partido.setEstado(new EstadoConfirmado());
    }

    @Override
    public String getNombre() {
        return "Partido armado";
    }
}
