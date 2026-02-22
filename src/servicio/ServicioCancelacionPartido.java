package servicio;

import modelo.Partido;
import modelo.Usuario;
import modelo.estado.EstadoCancelado;
import modelo.estado.EstadoEnJuego;
import modelo.estado.EstadoFinalizado;

// Logica de cancelacion: solo el organizador puede cancelar,
// y solo si el partido no empezo ni termino
public class ServicioCancelacionPartido {

    public void cancelar(Partido partido, Usuario solicitante) {
        // Solo el organizador puede cancelar
        if (!partido.getOrganizador().equals(solicitante)) {
            return;
        }

        // No se puede cancelar un partido que ya arranco o termino
        if (partido.getEstado() instanceof EstadoEnJuego
                || partido.getEstado() instanceof EstadoFinalizado
                || partido.getEstado() instanceof EstadoCancelado) {
            return;
        }

        partido.setEstado(new EstadoCancelado());
        partido.notificarObservadores();
    }
}
