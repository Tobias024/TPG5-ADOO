package controlador;

import datos.RepositorioPartidos;
import datos.RepositorioUsuarios;
import modelo.*;
import patrones.factory.FactoryGestorPartido;
import patrones.observer.Observer;
import patrones.strategy.IEstrategiaEmparejamiento;

import java.util.List;

public class FacadeOrganizadorDeporte {
    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioPartidos repositorioPartidos;
    private FactoryGestorPartido factoryGestorPartido;

    public FacadeOrganizadorDeporte() {
        this.repositorioUsuarios = new RepositorioUsuarios();
        this.repositorioPartidos = new RepositorioPartidos();
        this.factoryGestorPartido = new FactoryGestorPartido();
    }

    // --- Gestion de Usuarios ---
    public boolean registrarUsuario(String nombre, String mail, String contrasena) {
        Usuario nuevo = new Usuario(nombre, mail, contrasena);
        if (repositorioUsuarios.existe(nuevo)) {
            System.out.println("[Facade] El usuario con mail " + mail + " ya existe.");
            return false;
        }
        repositorioUsuarios.agregar(nuevo);
        System.out.println("[Facade] Usuario registrado: " + nombre);
        return true;
    }

    public boolean registrarUsuario(String nombre, String mail, String contrasena,
            Nivel nivel, String deporteFavorito) {
        Usuario nuevo = new Usuario(nombre, mail, contrasena, nivel, deporteFavorito);
        if (repositorioUsuarios.existe(nuevo)) {
            System.out.println("[Facade] El usuario con mail " + mail + " ya existe.");
            return false;
        }
        repositorioUsuarios.agregar(nuevo);
        System.out.println("[Facade] Usuario registrado: " + nombre +
                " (Nivel: " + nivel + ", Deporte favorito: " + deporteFavorito + ")");
        return true;
    }

    public Usuario buscarUsuario(String mail) {
        return repositorioUsuarios.buscar(mail);
    }

    // --- Gestion de Partidos ---
    public Partido crearPartido(Usuario organizador, String deporte, Nivel nivel,
            String ubiHora, String duracion, int cantJugadores) {
        Partido partido = factoryGestorPartido.crearPartido(deporte, nivel, ubiHora,
                duracion, cantJugadores);
        partido.setOrganizador(organizador);
        organizador.agregarPartidoCreado(partido);
        repositorioPartidos.agregar(partido);
        return partido;
    }

    public IPartido decorarPartido(Partido partido, String deporte) {
        return factoryGestorPartido.decorarConDeporte(partido, deporte);
    }

    public void cancelarPartido(Usuario organizador, Partido partido) {
        if (partido.getOrganizador().equals(organizador)) {
            partido.cancelar();
        } else {
            System.out.println("[Facade] Solo el organizador puede cancelar el partido.");
        }
    }

    /**
     * Orquesta la inscripcion de un jugador:
     * 1. Verifica compatibilidad con la estrategia
     * 2. Agrega al jugador
     * 3. Si se completa el cupo, avanza el estado
     */
    public boolean inscribirJugador(Partido partido, Usuario jugador) {
        // 1. Verificar compatibilidad con estrategia de emparejamiento
        IEstrategiaEmparejamiento estrategia = partido.getEstrategia();
        if (estrategia != null && !estrategia.esCompatible(jugador, partido)) {
            System.out.println("[Facade] El jugador " + jugador.getNombre() +
                    " no es compatible con este partido.");
            return false;
        }

        // 2. Agregar jugador
        boolean agregado = partido.agregarJugador(jugador);
        if (!agregado) {
            return false;
        }

        // 3. Si se completo el cupo, avanzar estado
        if (partido.estaCompleto()) {
            partido.avanzarEstado();
        }

        return true;
    }

    public void asignarEstrategia(Partido partido, IEstrategiaEmparejamiento estrategia) {
        partido.setEstrategiaEmparejamiento(estrategia);
        System.out.println("[Facade] Estrategia de emparejamiento asignada al partido.");
    }

    public void agregarObservador(Partido partido, Observer observador) {
        partido.agregarObservador(observador);
    }

    public List<Partido> listarPartidosDisponibles() {
        return repositorioPartidos.buscarDisponibles();
    }

    public List<Partido> listarTodosLosPartidos() {
        return repositorioPartidos.obtenerTodos();
    }

    // --- Getters ---
    public RepositorioUsuarios getRepositorioUsuarios() {
        return repositorioUsuarios;
    }

    public RepositorioPartidos getRepositorioPartidos() {
        return repositorioPartidos;
    }
}
