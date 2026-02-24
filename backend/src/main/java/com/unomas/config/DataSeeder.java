package com.unomas.config;

import com.unomas.model.*;
import com.unomas.model.estado.*;
import com.unomas.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final DeporteRepository deporteRepo;
    private final UsuarioRepository usuarioRepo;
    private final PartidoRepository partidoRepo;
    private final NotificacionRepository notifRepo;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(DeporteRepository deporteRepo, UsuarioRepository usuarioRepo,
                      PartidoRepository partidoRepo, NotificacionRepository notifRepo,
                      PasswordEncoder passwordEncoder) {
        this.deporteRepo = deporteRepo;
        this.usuarioRepo = usuarioRepo;
        this.partidoRepo = partidoRepo;
        this.notifRepo = notifRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (deporteRepo.count() > 0) {
            log.info("Base de datos ya tiene datos, salteando seeder");
            return;
        }
        log.info("Cargando datos de demostración...");

        Deporte futbol = deporteRepo.save(new Deporte("Futbol"));
        Deporte basquet = deporteRepo.save(new Deporte("Basquet"));
        Deporte voley = deporteRepo.save(new Deporte("Voley"));
        Deporte tenis = deporteRepo.save(new Deporte("Tenis"));

        String pass = passwordEncoder.encode("password123");

        Usuario juan = crearUsuario("Juan Perez", "juan@test.com", pass, "AVANZADO", futbol, 12, -34.6037, -58.3816);
        Usuario maria = crearUsuario("Maria Garcia", "maria@test.com", pass, "INTERMEDIO", basquet, 7, -34.6100, -58.3900);
        Usuario carlos = crearUsuario("Carlos Lopez", "carlos@test.com", pass, "PRINCIPIANTE", futbol, 2, -34.5900, -58.3700);
        Usuario ana = crearUsuario("Ana Martinez", "ana@test.com", pass, "AVANZADO", voley, 20, -34.6200, -58.4000);
        Usuario pedro = crearUsuario("Pedro Gomez", "pedro@test.com", pass, "INTERMEDIO", futbol, 5, -34.6050, -58.3850);
        Usuario lucia = crearUsuario("Lucia Fernandez", "lucia@test.com", pass, "PRINCIPIANTE", tenis, 1, -34.6150, -58.3950);
        Usuario diego = crearUsuario("Diego Torres", "diego@test.com", pass, "AVANZADO", basquet, 15, -34.5950, -58.3750);
        Usuario sol = crearUsuario("Sol Ramirez", "sol@test.com", pass, "INTERMEDIO", voley, 9, -34.6250, -58.4050);

        // Partido 1: Futbol - FALTAN_JUGADORES (needs 6, has 3)
        Partido p1 = crearPartido(futbol, juan, "Parque Centenario", LocalDateTime.now().plusDays(2), "90 min", 6, "PRINCIPIANTE", "AVANZADO", "LIBRE");
        p1.getJugadores().addAll(List.of(juan, carlos, pedro));
        partidoRepo.save(p1);

        // Partido 2: Basquet - ARMADO (full, 4/4)
        Partido p2 = crearPartido(basquet, maria, "Club San Lorenzo", LocalDateTime.now().plusDays(1), "60 min", 4, "INTERMEDIO", "AVANZADO", "POR_NIVEL");
        p2.getJugadores().addAll(List.of(maria, diego, ana, juan));
        p2.setEstadoNombre("ARMADO");
        partidoRepo.save(p2);

        // Partido 3: Voley - CONFIRMADO
        Partido p3 = crearPartido(voley, ana, "Playa Bristol", LocalDateTime.now().plusHours(5), "120 min", 6, "PRINCIPIANTE", "AVANZADO", "LIBRE");
        p3.getJugadores().addAll(List.of(ana, sol, maria, lucia, juan, pedro));
        p3.setEstadoNombre("CONFIRMADO");
        partidoRepo.save(p3);

        // Partido 4: Futbol - EN_JUEGO
        Partido p4 = crearPartido(futbol, pedro, "Cancha Boca", LocalDateTime.now().minusHours(1), "90 min", 4, "PRINCIPIANTE", "INTERMEDIO", "POR_CERCANIA");
        p4.getJugadores().addAll(List.of(pedro, carlos, lucia, sol));
        p4.setEstadoNombre("EN_JUEGO");
        partidoRepo.save(p4);

        // Partido 5: Tenis - FINALIZADO
        Partido p5 = crearPartido(tenis, lucia, "Club Tenis Central", LocalDateTime.now().minusDays(1), "60 min", 2, "PRINCIPIANTE", "AVANZADO", "LIBRE");
        p5.getJugadores().addAll(List.of(lucia, ana));
        p5.setEstadoNombre("FINALIZADO");
        partidoRepo.save(p5);

        // Partido 6: Demo scheduler - CONFIRMADO, starts in 3 min, duration 2 min (auto-start then auto-end)
        Partido p6 = crearPartido(tenis, diego, "Cancha Demo Scheduler", LocalDateTime.now().plusMinutes(3), "2 min", 2, "PRINCIPIANTE", "AVANZADO", "LIBRE");
        p6.getJugadores().addAll(List.of(diego, ana));
        p6.setEstadoNombre("CONFIRMADO");
        partidoRepo.save(p6);

        // Sample notifications
        notifRepo.save(new Notificacion(carlos, "Nuevo partido de Futbol en Parque Centenario", "PUSH"));
        notifRepo.save(new Notificacion(carlos, "Nuevo partido de Futbol en Parque Centenario", "EMAIL"));
        notifRepo.save(new Notificacion(maria, "El partido de Basquet cambió a estado: ARMADO", "PUSH"));
        notifRepo.save(new Notificacion(ana, "El partido de Voley cambió a estado: CONFIRMADO", "EMAIL"));
        notifRepo.save(new Notificacion(pedro, "El partido de Futbol cambió a estado: EN_JUEGO", "PUSH"));

        log.info("Datos de demostración cargados exitosamente");
    }

    private Usuario crearUsuario(String nombre, String mail, String pass, String nivel,
                                  Deporte deporte, int victorias, double lat, double lon) {
        Usuario u = new Usuario(nombre, mail, pass);
        u.setNivel(nivel);
        u.setDeporteFavorito(deporte);
        u.setVictorias(victorias);
        u.setLatitud(lat);
        u.setLongitud(lon);
        return usuarioRepo.save(u);
    }

    private Partido crearPartido(Deporte deporte, Usuario org, String ubicacion,
                                  LocalDateTime fechaHora, String duracion, int cant,
                                  String nivelMin, String nivelMax, String estrategia) {
        Partido p = new Partido();
        p.setDeporte(deporte);
        p.setOrganizador(org);
        p.setUbicacion(ubicacion);
        p.setFechaHora(fechaHora);
        p.setDuracion(duracion);
        p.setCantidadJugadores(cant);
        p.setNivelMinimo(nivelMin);
        p.setNivelMaximo(nivelMax);
        p.setEstrategiaEmparejamiento(estrategia);
        return partidoRepo.save(p);
    }
}
