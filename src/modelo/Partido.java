package modelo;

import patrones.observer.ISujeto;
import patrones.observer.Observer;
import patrones.state.FaltanJugadores;
import patrones.state.StatePartido;
import patrones.state.Cancelado;
import patrones.strategy.IEstrategiaEmparejamiento;

import java.util.ArrayList;
import java.util.List;

public class Partido implements IPartido, ISujeto {
    private String tipoDeDeporte;
    private int cantJugadoresReq;
    private String duracion;
    private String ubiHora;
    private Nivel nivel;
    private IEstrategiaEmparejamiento estrategia;
    private StatePartido state;
    private List<Observer> observadores;
    private List<Usuario> jugadores;
    private Usuario organizador;

    public Partido(int cantJugadoresReq, String duracion, String ubiHora, Nivel nivel) {
        this.tipoDeDeporte = "Sin deporte asignado";
        this.cantJugadoresReq = cantJugadoresReq;
        this.duracion = duracion;
        this.ubiHora = ubiHora;
        this.nivel = nivel;
        this.state = new FaltanJugadores();
        this.observadores = new ArrayList<>();
        this.jugadores = new ArrayList<>();
    }

    // --- IPartido (Componente base del Decorator) ---
    @Override
    public String getTipoDeDeporte() {
        return tipoDeDeporte;
    }

    public void setTipoDeDeporte(String tipoDeDeporte) {
        this.tipoDeDeporte = tipoDeDeporte;
    }

    @Override
    public int getCantJugadoresReq() {
        return cantJugadoresReq;
    }

    @Override
    public String getDuracion() {
        return duracion;
    }

    @Override
    public String getUbiHora() {
        return ubiHora;
    }

    @Override
    public Nivel getNivel() {
        return nivel;
    }

    @Override
    public String getDescripcion() {
        return "Partido en " + ubiHora + ", duracion: " + duracion;
    }

    // --- Strategy ---
    public void setEstrategiaEmparejamiento(IEstrategiaEmparejamiento estrategia) {
        this.estrategia = estrategia;
    }

    public IEstrategiaEmparejamiento getEstrategia() {
        return estrategia;
    }

    // --- Gestion de jugadores (solo agregar/verificar, sin orquestar estado) ---
    public boolean agregarJugador(Usuario jugador) {
        if (jugadores.contains(jugador)) {
            System.out.println("[Partido] El jugador " + jugador.getNombre() +
                    " ya esta inscripto en este partido.");
            return false;
        }
        jugadores.add(jugador);
        jugador.agregarPartidoInscripto(this);
        System.out.println("[Partido] Jugador " + jugador.getNombre() +
                " se unio al partido. (" + jugadores.size() + "/" + cantJugadoresReq + ")");
        return true;
    }

    public boolean estaCompleto() {
        return jugadores.size() >= cantJugadoresReq;
    }

    // --- State ---
    public void setEstado(StatePartido state) {
        this.state = state;
    }

    public StatePartido getEstado() {
        return state;
    }

    public void avanzarEstado() {
        state.avanzarEstado(this);
    }

    public void cancelar() {
        this.state = new Cancelado();
        System.out.println("[Estado] El partido fue cancelado.");
        notificarObservadores();
    }

    // --- Observer (ISujeto) ---
    @Override
    public void agregarObservador(Observer obs) {
        observadores.add(obs);
    }

    @Override
    public void eliminarObservador(Observer obs) {
        observadores.remove(obs);
    }

    @Override
    public void notificarObservadores() {
        for (Observer obs : observadores) {
            obs.notificar(this);
        }
    }

    // --- Getters adicionales ---
    public List<Usuario> getJugadores() {
        return jugadores;
    }

    public Usuario getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Usuario organizador) {
        this.organizador = organizador;
    }

    public void setCantJugadoresReq(int cantJugadoresReq) {
        this.cantJugadoresReq = cantJugadoresReq;
    }

    @Override
    public String toString() {
        return "Partido{" +
                "deporte=" + getTipoDeDeporte() +
                ", jugadores=" + jugadores.size() + "/" + cantJugadoresReq +
                ", estado=" + state.getNombre() +
                ", ubicacion='" + ubiHora + '\'' +
                ", nivel=" + nivel +
                '}';
    }
}
