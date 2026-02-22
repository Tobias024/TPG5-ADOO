package modelo;

import modelo.emparejamiento.IEstrategiaEmparejamiento;
import modelo.estado.EstadoFaltanJugadores;
import modelo.estado.IEstadoPartido;
import modelo.nivel.INivel;
import modelo.observador.GestorObservadores;
import modelo.observador.IObserver;
import modelo.observador.ISujeto;
import servicio.GestorFlujoPartido;

public class Partido implements ISujeto {
    private Deporte deporte;
    private INivel nivelMinimo;
    private String ubiHora;
    private String duracion;
    private Usuario organizador;
    private CupoPartido cupo;
    private IEstadoPartido estado;
    private IEstrategiaEmparejamiento estrategia;
    private GestorObservadores observadores;
    private GestorFlujoPartido flujo;

    public Partido(Deporte deporte, INivel nivelMinimo, String ubiHora,
                   String duracion, int cantJugadores, Usuario organizador,
                   IEstrategiaEmparejamiento estrategia) {
        this.deporte = deporte;
        this.nivelMinimo = nivelMinimo;
        this.ubiHora = ubiHora;
        this.duracion = duracion;
        this.organizador = organizador;
        this.cupo = new CupoPartido(cantJugadores);
        this.estado = new EstadoFaltanJugadores();
        this.estrategia = estrategia;
        this.observadores = new GestorObservadores();
    }

    // Intenta inscribir un jugador, delega al flujo
    public boolean inscribirJugador(Usuario jugador) {
        if (flujo != null) {
            return flujo.inscribir(this, jugador);
        }
        return false;
    }

    public void cancelar(Usuario solicitante) {
        if (flujo != null) {
            flujo.cancelar(this, solicitante);
        }
    }

    public void avanzarEstado() {
        estado.avanzar(this);
        notificarObservadores();
    }

    // -- ISujeto --

    @Override
    public void agregarObservador(IObserver obs) {
        observadores.agregar(obs);
    }

    @Override
    public void eliminarObservador(IObserver obs) {
        observadores.eliminar(obs);
    }

    @Override
    public void notificarObservadores() {
        observadores.notificar(this);
    }

    // -- Getters y setters --

    public Deporte getDeporte() {
        return deporte;
    }

    public INivel getNivelMinimo() {
        return nivelMinimo;
    }

    public String getUbiHora() {
        return ubiHora;
    }

    public String getDuracion() {
        return duracion;
    }

    public Usuario getOrganizador() {
        return organizador;
    }

    public CupoPartido getCupo() {
        return cupo;
    }

    public IEstadoPartido getEstado() {
        return estado;
    }

    public void setEstado(IEstadoPartido estado) {
        this.estado = estado;
    }

    public IEstrategiaEmparejamiento getEstrategia() {
        return estrategia;
    }

    public GestorFlujoPartido getFlujo() {
        return flujo;
    }

    public void setFlujo(GestorFlujoPartido flujo) {
        this.flujo = flujo;
    }

    @Override
    public String toString() {
        return deporte.getNombre() + " - " + ubiHora
                + " [" + estado.getNombre() + "]"
                + " (" + cupo.getJugadores().size() + "/" + cupo.getCantRequerida() + ")";
    }
}
