package vista;

import controlador.FacadeOrganizadorDeporte;
import modelo.*;
import patrones.adapter.*;
import patrones.decorator.*;
import patrones.observer.Notificador;
import patrones.strategy.*;

public class VistaSistema {
    private FacadeOrganizadorDeporte organizador;

    public VistaSistema() {
        this.organizador = new FacadeOrganizadorDeporte();
    }

    public void ejecutarDemo() {
        System.out.println("=============================================================");
        System.out.println("   SISTEMA DE GESTION DE ENCUENTROS DEPORTIVOS");
        System.out.println("=============================================================\n");

        // -------------------------------------------------------
        // 1. REGISTRO DE USUARIOS
        // -------------------------------------------------------
        System.out.println("--- 1. REGISTRO DE USUARIOS ---\n");

        organizador.registrarUsuario("Juan", "juan@mail.com", "pass123",
                Nivel.AVANZADO, "Futbol");
        organizador.registrarUsuario("Maria", "maria@mail.com", "pass456",
                Nivel.INTERMEDIO, "Futbol");
        organizador.registrarUsuario("Carlos", "carlos@mail.com", "pass789",
                Nivel.AVANZADO, "Basquet");
        organizador.registrarUsuario("Ana", "ana@mail.com", "pass101",
                Nivel.PRINCIPIANTE, "Voley");
        organizador.registrarUsuario("Pedro", "pedro@mail.com", "pass202",
                Nivel.AVANZADO, "Futbol");

        // Intento de registro duplicado
        organizador.registrarUsuario("Juan Duplicado", "juan@mail.com", "otro");

        Usuario juan = organizador.buscarUsuario("juan@mail.com");
        Usuario maria = organizador.buscarUsuario("maria@mail.com");
        Usuario carlos = organizador.buscarUsuario("carlos@mail.com");
        Usuario ana = organizador.buscarUsuario("ana@mail.com");
        Usuario pedro = organizador.buscarUsuario("pedro@mail.com");

        // -------------------------------------------------------
        // 2. CONFIGURAR NOTIFICACIONES (Observer + Adapter)
        // -------------------------------------------------------
        System.out.println("\n--- 2. CONFIGURANDO NOTIFICACIONES ---\n");

        // Crear adaptadores
        IAdapterMail adapterMail = new AdapterJavaMail();
        IAdapterFirebase adapterFirebase = new AdapterFirebase();

        // Crear estrategias de notificacion
        IEstrategiaNotificacion estrategiaEmail = new NotificacionEmail(adapterMail);
        IEstrategiaNotificacion estrategiaPush = new NotificacionPush(adapterFirebase);

        // Crear notificadores (Observers)
        Notificador notificadorEmail = new Notificador(estrategiaEmail);
        Notificador notificadorPush = new Notificador(estrategiaPush);

        System.out.println("Notificadores configurados: Email (JavaMail) y Push (Firebase)");

        // -------------------------------------------------------
        // 3. CREACION DE UN PARTIDO (Factory + Decorator)
        // -------------------------------------------------------
        System.out.println("\n--- 3. CREACION DE PARTIDO ---\n");

        Partido partidoFutbol = organizador.crearPartido(
                juan, "Futbol", Nivel.AVANZADO,
                "Club Deportivo - Sabado 16hs", "90 minutos", 3);

        // Mostrar decoracion del partido
        IPartido partidoDecorado = organizador.decorarPartido(partidoFutbol, "Futbol");
        System.out.println("Descripcion decorada: " + partidoDecorado.getDescripcion());
        System.out.println("Deporte (via Decorator): " + partidoDecorado.getTipoDeDeporte());

        // Agregar observadores al partido
        organizador.agregarObservador(partidoFutbol, notificadorEmail);
        organizador.agregarObservador(partidoFutbol, notificadorPush);
        System.out.println("Observadores agregados al partido.");

        // Asignar estrategia de emparejamiento
        organizador.asignarEstrategia(partidoFutbol, new EmparejamientoPorNivel());

        System.out.println("\nEstado actual: " + partidoFutbol.getEstado().getNombre());

        // -------------------------------------------------------
        // 4. INSCRIPCION DE JUGADORES (Strategy + State transitions)
        // -------------------------------------------------------
        System.out.println("\n--- 4. INSCRIPCION DE JUGADORES ---\n");

        // Ana es PRINCIPIANTE, el partido requiere AVANZADO → debe rechazarse
        System.out.println(">> Intentando inscribir a Ana (Principiante)...");
        organizador.inscribirJugador(partidoFutbol, ana);

        // Maria es INTERMEDIO → debe rechazarse (estrategia por nivel exacto)
        System.out.println("\n>> Intentando inscribir a Maria (Intermedio)...");
        organizador.inscribirJugador(partidoFutbol, maria);

        // Pedro es AVANZADO → debe aceptarse
        System.out.println("\n>> Inscribiendo a Pedro (Avanzado)...");
        organizador.inscribirJugador(partidoFutbol, pedro);

        // Carlos es AVANZADO → debe aceptarse y completar el cupo (2/3 → 3/3)
        System.out.println("\n>> Inscribiendo a Carlos (Avanzado)...");

        // Cambiar estrategia a cercania para demostrar que se puede cambiar
        organizador.asignarEstrategia(partidoFutbol, new EmparejamientoPorCercania());
        organizador.inscribirJugador(partidoFutbol, carlos);

        // Juan (organizador) tambien se inscribe
        System.out.println("\n>> Inscribiendo a Juan (Organizador, Avanzado)...");
        organizador.asignarEstrategia(partidoFutbol, new EmparejamientoPorNivel());
        organizador.inscribirJugador(partidoFutbol, juan);

        // -------------------------------------------------------
        // 5. TRANSICIONES DE ESTADO
        // -------------------------------------------------------
        System.out.println("\n--- 5. TRANSICIONES DE ESTADO ---\n");
        System.out.println("Estado actual: " + partidoFutbol.getEstado().getNombre());

        // Avanzar: Partido Armado → Confirmado
        System.out.println("\n>> Confirmando partido...");
        partidoFutbol.avanzarEstado();

        // Avanzar: Confirmado → En Juego
        System.out.println("\n>> Iniciando partido (simulando hora de inicio)...");
        partidoFutbol.avanzarEstado();

        // Avanzar: En Juego → Finalizado
        System.out.println("\n>> Finalizando partido...");
        partidoFutbol.avanzarEstado();

        // Intentar avanzar despues de Finalizado
        System.out.println("\n>> Intentando avanzar partido finalizado...");
        partidoFutbol.avanzarEstado();

        // -------------------------------------------------------
        // 6. BUSQUEDA DE PARTIDOS DISPONIBLES
        // -------------------------------------------------------
        System.out.println("\n--- 6. BUSQUEDA DE PARTIDOS ---\n");

        // Crear otro partido para demostrar la busqueda
        Partido partidoBasquet = organizador.crearPartido(
                carlos, "Basquet", Nivel.INTERMEDIO,
                "Gimnasio Central - Domingo 10hs", "60 minutos", 4);
        organizador.agregarObservador(partidoBasquet, notificadorEmail);

        System.out.println("\nPartidos disponibles (estado 'Necesitamos jugadores'):");
        for (Partido p : organizador.listarPartidosDisponibles()) {
            System.out.println("  -> " + p);
        }

        System.out.println("\nTodos los partidos:");
        for (Partido p : organizador.listarTodosLosPartidos()) {
            System.out.println("  -> " + p);
        }

        // -------------------------------------------------------
        // 7. CANCELACION DE PARTIDO
        // -------------------------------------------------------
        System.out.println("\n--- 7. CANCELACION DE PARTIDO ---\n");

        // Ana intenta cancelar el partido de Carlos → debe rechazarse
        organizador.cancelarPartido(ana, partidoBasquet);

        // Carlos cancela su propio partido
        organizador.cancelarPartido(carlos, partidoBasquet);

        // -------------------------------------------------------
        // 8. CAMBIO DE ESTRATEGIA DE NOTIFICACION
        // -------------------------------------------------------
        System.out.println("\n--- 8. CAMBIO DE ESTRATEGIA DE NOTIFICACION ---\n");

        System.out.println("Cambiando notificador de Email a Push...");
        notificadorEmail.cambiarEstrategia(estrategiaPush);
        System.out.println("Ahora el notificador que era de Email usa Push notifications.");

        System.out.println("\n=============================================================");
        System.out.println("   FIN DE LA DEMO");
        System.out.println("=============================================================");
    }

    public static void main(String[] args) {
        VistaSistema vista = new VistaSistema();
        vista.ejecutarDemo();
    }
}
