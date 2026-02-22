package vista;

import modelo.Deporte;
import modelo.Partido;
import modelo.nivel.INivel;
import modelo.nivel.NivelAvanzado;
import modelo.nivel.NivelIntermedio;
import modelo.nivel.NivelPrincipiante;
import vista.dto.CredencialesLogin;
import vista.dto.DatosCreacionPartido;
import vista.dto.DatosRegistroUsuario;

import java.util.List;
import java.util.Scanner;

public class VistaConsola implements IVista {
    private Scanner scanner;

    public VistaConsola() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void mostrarPartidosDisponibles(List<Partido> partidos) {
        if (partidos.isEmpty()) {
            System.out.println("No hay partidos disponibles en este momento.");
            return;
        }
        System.out.println("\n--- Partidos disponibles ---");
        for (int i = 0; i < partidos.size(); i++) {
            System.out.println((i + 1) + ". " + partidos.get(i));
        }
        System.out.println("----------------------------");
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    @Override
    public void mostrarEstadoPartido(Partido partido) {
        System.out.println("Partido: " + partido.getDeporte().getNombre());
        System.out.println("  Estado: " + partido.getEstado().getNombre());
        System.out.println("  Jugadores: " + partido.getCupo().getJugadores().size()
                + "/" + partido.getCupo().getCantRequerida());
        System.out.println("  Ubicacion/Horario: " + partido.getUbiHora());
        System.out.println("  Duracion: " + partido.getDuracion());
    }

    public DatosCreacionPartido pedirDatosPartido() {
        System.out.println("\n--- Crear nuevo partido ---");

        System.out.print("Deporte: ");
        String nombreDeporte = scanner.nextLine().trim();
        Deporte deporte = new Deporte(nombreDeporte);

        INivel nivelMinimo = pedirNivel("Nivel minimo (1=Principiante, 2=Intermedio, 3=Avanzado, 0=Sin minimo): ");

        System.out.print("Ubicacion y horario: ");
        String ubiHora = scanner.nextLine().trim();

        System.out.print("Duracion: ");
        String duracion = scanner.nextLine().trim();

        System.out.print("Cantidad de jugadores: ");
        int cantJugadores = Integer.parseInt(scanner.nextLine().trim());

        return new DatosCreacionPartido(deporte, nivelMinimo, ubiHora, duracion, cantJugadores);
    }

    public CredencialesLogin pedirCredenciales() {
        System.out.println("\n--- Login ---");
        System.out.print("Mail: ");
        String mail = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        return new CredencialesLogin(mail, password);
    }

    public DatosRegistroUsuario pedirDatosRegistro() {
        System.out.println("\n--- Registro ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Mail: ");
        String mail = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Deporte favorito (opcional, dejar vacio para saltar): ");
        String depFav = scanner.nextLine().trim();
        Deporte deporteFavorito = depFav.isEmpty() ? null : new Deporte(depFav);

        INivel nivelInicial = pedirNivel("Nivel de juego (1=Principiante, 2=Intermedio, 3=Avanzado, 0=No indicar): ");

        return new DatosRegistroUsuario(nombre, mail, password, deporteFavorito, nivelInicial);
    }

    // Pide al usuario que elija un nivel
    private INivel pedirNivel(String prompt) {
        System.out.print(prompt);
        String opcion = scanner.nextLine().trim();
        switch (opcion) {
            case "1":
                // Los niveles se encadenan: principiante -> intermedio -> avanzado
                NivelAvanzado avanzado = new NivelAvanzado();
                NivelIntermedio intermedio = new NivelIntermedio(avanzado);
                return new NivelPrincipiante(intermedio);
            case "2":
                NivelAvanzado av2 = new NivelAvanzado();
                return new NivelIntermedio(av2);
            case "3":
                return new NivelAvanzado();
            default:
                return null;
        }
    }

    // Muestra un menu y devuelve la opcion elegida
    public int mostrarMenu() {
        System.out.println("\n===== Menu Principal =====");
        System.out.println("1. Registrarse");
        System.out.println("2. Iniciar sesion");
        System.out.println("3. Crear partido");
        System.out.println("4. Ver partidos disponibles");
        System.out.println("5. Inscribirse a un partido");
        System.out.println("6. Cancelar un partido");
        System.out.println("7. Avanzar estado de un partido");
        System.out.println("8. Ver estado de un partido");
        System.out.println("0. Salir");
        System.out.print("Opcion: ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Pide al usuario que elija un partido de la lista
    public int elegirPartido(List<Partido> partidos) {
        mostrarPartidosDisponibles(partidos);
        if (partidos.isEmpty()) return -1;
        System.out.print("Elegir partido (numero): ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx >= 0 && idx < partidos.size()) {
                return idx;
            }
        } catch (NumberFormatException e) {
            // nada
        }
        return -1;
    }
}
