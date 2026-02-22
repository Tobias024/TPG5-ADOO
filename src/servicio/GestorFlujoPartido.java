package servicio;

import modelo.Partido;
import modelo.Usuario;

// Fachada que coordina los tres servicios de flujo del partido:
// inscripcion, avance de estado y cancelacion
public class GestorFlujoPartido {
    private ServicioInscripcionPartido servicioInscripcion;
    private ServicioEstadoPartido servicioEstado;
    private ServicioCancelacionPartido servicioCancelacion;

    public GestorFlujoPartido(ServicioInscripcionPartido servicioInscripcion,
                              ServicioEstadoPartido servicioEstado,
                              ServicioCancelacionPartido servicioCancelacion) {
        this.servicioInscripcion = servicioInscripcion;
        this.servicioEstado = servicioEstado;
        this.servicioCancelacion = servicioCancelacion;
    }

    public boolean inscribir(Partido partido, Usuario jugador) {
        return servicioInscripcion.inscribir(partido, jugador);
    }

    public void cancelar(Partido partido, Usuario solicitante) {
        servicioCancelacion.cancelar(partido, solicitante);
    }

    public void avanzarEstado(Partido partido) {
        servicioEstado.avanzar(partido);
    }
}
