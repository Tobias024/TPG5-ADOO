package controlador;

import modelo.Deporte;
import modelo.Partido;
import modelo.Usuario;
import modelo.emparejamiento.EmparejamientoLibre;
import modelo.emparejamiento.EmparejamientoPorNivel;
import modelo.emparejamiento.IEstrategiaEmparejamiento;
import modelo.nivel.INivel;
import servicio.GestorFlujoPartido;
import servicio.ServicioPartidos;
import servicio.ServicioUsuarios;
import vista.IVista;
import vista.dto.DatosCreacionPartido;
import vista.dto.DatosRegistroUsuario;
import vista.dto.CredencialesLogin;

import java.util.List;

public class OrganizadorDeporteController {
    private IVista vista;
    private ServicioUsuarios servicioUsuarios;
    private ServicioPartidos servicioPartidos;
    private GestorFlujoPartido gestorFlujo;

    public OrganizadorDeporteController(IVista vista, ServicioUsuarios servicioUsuarios,
                                        ServicioPartidos servicioPartidos,
                                        GestorFlujoPartido gestorFlujo) {
        this.vista = vista;
        this.servicioUsuarios = servicioUsuarios;
        this.servicioPartidos = servicioPartidos;
        this.gestorFlujo = gestorFlujo;
    }

    public Partido crearPartido(Deporte deporte, INivel nivelMinimo,
                                String ubiHora, String duracion, int cantJugadores) {
        // Si hay nivel minimo, usa emparejamiento por nivel; si no, libre
        IEstrategiaEmparejamiento estrategia;
        if (nivelMinimo != null) {
            estrategia = new EmparejamientoPorNivel();
        } else {
            estrategia = new EmparejamientoLibre();
        }

        // Necesitamos un organizador logueado para crear el partido
        // pero lo recibimos indirectamente desde el main
        return null;
    }

    // Version completa que recibe al organizador
    public Partido crearPartido(DatosCreacionPartido datos, Usuario organizador) {
        IEstrategiaEmparejamiento estrategia;
        if (datos.getNivelMinimo() != null) {
            estrategia = new EmparejamientoPorNivel();
        } else {
            estrategia = new EmparejamientoLibre();
        }

        Partido partido = new Partido(datos.getDeporte(), datos.getNivelMinimo(),
                datos.getUbiHora(), datos.getDuracion(),
                datos.getCantJugadores(), organizador, estrategia);
        partido.setFlujo(gestorFlujo);

        servicioPartidos.guardar(partido);
        vista.mostrarMensaje("Partido creado: " + partido);
        return partido;
    }

    public boolean inscribirJugador(Partido partido, Usuario jugador) {
        boolean resultado = gestorFlujo.inscribir(partido, jugador);
        if (resultado) {
            vista.mostrarMensaje("Jugador " + jugador.getNombre() + " inscripto correctamente.");
            vista.mostrarEstadoPartido(partido);
        } else {
            vista.mostrarMensaje("No se pudo inscribir al jugador.");
        }
        return resultado;
    }

    public Usuario registrarUsuario(String nombre, String mail, String password) {
        Usuario usuario = servicioUsuarios.registrar(nombre, mail, password);
        if (usuario != null) {
            vista.mostrarMensaje("Usuario registrado: " + usuario);
        } else {
            vista.mostrarMensaje("No se pudo registrar. El mail ya existe.");
        }
        return usuario;
    }

    public Usuario registrarUsuario(DatosRegistroUsuario datos) {
        Usuario usuario = servicioUsuarios.registrar(datos.getNombre(),
                datos.getMail(), datos.getPassword());
        if (usuario != null) {
            if (datos.getDeporteFavorito() != null) {
                usuario.setDeporteFavorito(datos.getDeporteFavorito());
            }
            if (datos.getNivelInicial() != null) {
                usuario.setNivel(datos.getNivelInicial());
            }
            vista.mostrarMensaje("Usuario registrado: " + usuario);
        } else {
            vista.mostrarMensaje("No se pudo registrar. El mail ya existe.");
        }
        return usuario;
    }

    public void cancelarPartido(Partido partido, Usuario solicitante) {
        gestorFlujo.cancelar(partido, solicitante);
        vista.mostrarMensaje("Partido cancelado.");
        vista.mostrarEstadoPartido(partido);
    }

    public Usuario login(String mail, String password) {
        Usuario usuario = servicioUsuarios.login(mail, password);
        if (usuario != null) {
            vista.mostrarMensaje("Bienvenido, " + usuario.getNombre() + "!");
        } else {
            vista.mostrarMensaje("Credenciales incorrectas.");
        }
        return usuario;
    }

    public void listarPartidosDisponibles() {
        List<Partido> disponibles = servicioPartidos.listarDisponibles();
        vista.mostrarPartidosDisponibles(disponibles);
    }

    public List<Partido> obtenerTodosLosPartidos() {
        return servicioPartidos.listar();
    }

    public void avanzarEstado(Partido partido) {
        gestorFlujo.avanzarEstado(partido);
        vista.mostrarEstadoPartido(partido);
    }
}
