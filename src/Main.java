import controlador.OrganizadorDeporteController;
import modelo.Partido;
import modelo.Usuario;
import modelo.notificacion.*;
import modelo.observador.NotificadorObserver;
import repositorio.*;
import servicio.*;
import vista.VistaConsola;
import vista.dto.CredencialesLogin;
import vista.dto.DatosCreacionPartido;
import vista.dto.DatosRegistroUsuario;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Armar las dependencias del sistema
        VistaConsola vista = new VistaConsola();

        IRepositorioUsuarios repoUsuarios = new RepositorioUsuariosMemoria();
        IRepositorioPartidos repoPartidos = new RepositorioPartidosMemoria();

        ServicioUsuarios servicioUsuarios = new ServicioUsuarios(repoUsuarios);
        ServicioPartidos servicioPartidos = new ServicioPartidos(repoPartidos);

        ValidadorInscripcion validador = new ValidadorInscripcion();
        ServicioInscripcionPartido servicioInscripcion = new ServicioInscripcionPartido(validador);
        ServicioEstadoPartido servicioEstado = new ServicioEstadoPartido();
        ServicioCancelacionPartido servicioCancelacion = new ServicioCancelacionPartido();

        GestorFlujoPartido gestorFlujo = new GestorFlujoPartido(
                servicioInscripcion, servicioEstado, servicioCancelacion);

        OrganizadorDeporteController controller = new OrganizadorDeporteController(
                vista, servicioUsuarios, servicioPartidos, gestorFlujo);

        // Servicio de notificaciones con email por defecto
        IAdapterMail adapterMail = new AdapterJavaMail();
        IEstrategiaNotificacion estrategiaEmail = new NotificacionEmail(adapterMail);
        ServicioNotificaciones servicioNoti = new ServicioNotificaciones(estrategiaEmail);
        NotificadorObserver notificador = new NotificadorObserver(servicioNoti);

        // El usuario logueado actual
        Usuario usuarioActual = null;

        // Loop principal de la aplicacion
        boolean salir = false;
        while (!salir) {
            int opcion = vista.mostrarMenu();
            switch (opcion) {
                case 1: // Registrarse
                    DatosRegistroUsuario datosReg = vista.pedirDatosRegistro();
                    usuarioActual = controller.registrarUsuario(datosReg);
                    break;

                case 2: // Login
                    CredencialesLogin creds = vista.pedirCredenciales();
                    usuarioActual = controller.login(creds.getMail(), creds.getPassword());
                    break;

                case 3: // Crear partido
                    if (usuarioActual == null) {
                        vista.mostrarMensaje("Tenes que iniciar sesion primero.");
                        break;
                    }
                    DatosCreacionPartido datosPartido = vista.pedirDatosPartido();
                    Partido nuevoPartido = controller.crearPartido(datosPartido, usuarioActual);
                    if (nuevoPartido != null) {
                        // Le agregamos el notificador como observador
                        nuevoPartido.agregarObservador(notificador);
                    }
                    break;

                case 4: // Ver partidos disponibles
                    controller.listarPartidosDisponibles();
                    break;

                case 5: // Inscribirse a un partido
                    if (usuarioActual == null) {
                        vista.mostrarMensaje("Tenes que iniciar sesion primero.");
                        break;
                    }
                    List<Partido> disponibles = servicioPartidos.listarDisponibles();
                    int idxInsc = vista.elegirPartido(disponibles);
                    if (idxInsc >= 0) {
                        controller.inscribirJugador(disponibles.get(idxInsc), usuarioActual);
                    } else {
                        vista.mostrarMensaje("Seleccion invalida.");
                    }
                    break;

                case 6: // Cancelar partido
                    if (usuarioActual == null) {
                        vista.mostrarMensaje("Tenes que iniciar sesion primero.");
                        break;
                    }
                    List<Partido> todos = controller.obtenerTodosLosPartidos();
                    int idxCancel = vista.elegirPartido(todos);
                    if (idxCancel >= 0) {
                        controller.cancelarPartido(todos.get(idxCancel), usuarioActual);
                    }
                    break;

                case 7: // Avanzar estado
                    List<Partido> todosAv = controller.obtenerTodosLosPartidos();
                    int idxAv = vista.elegirPartido(todosAv);
                    if (idxAv >= 0) {
                        controller.avanzarEstado(todosAv.get(idxAv));
                    }
                    break;

                case 8: // Ver estado
                    List<Partido> todosVer = controller.obtenerTodosLosPartidos();
                    int idxVer = vista.elegirPartido(todosVer);
                    if (idxVer >= 0) {
                        vista.mostrarEstadoPartido(todosVer.get(idxVer));
                    }
                    break;

                case 0:
                    vista.mostrarMensaje("Hasta la proxima!");
                    salir = true;
                    break;

                default:
                    vista.mostrarMensaje("Opcion invalida.");
            }
        }
    }
}
