package com.subastar.config;

import com.subastar.model.*;
import com.subastar.model.enums.*;
import com.subastar.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataSeeder implements CommandLineRunner {

    private final PaisRepository paisRepo;
    private final PersonaRepository personaRepo;
    private final ClienteRepository clienteRepo;
    private final SubastaRepository subastaRepo;
    private final CatalogoRepository catalogoRepo;
    private final ProductoRepository productoRepo;
    private final ItemCatalogoRepository itemRepo;
    private final MetodoPagoRepository metodoPagoRepo;
    private final NotificacionRepository notificacionRepo;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(PaisRepository paisRepo, PersonaRepository personaRepo, ClienteRepository clienteRepo,
                      SubastaRepository subastaRepo, CatalogoRepository catalogoRepo, ProductoRepository productoRepo,
                      ItemCatalogoRepository itemRepo, MetodoPagoRepository metodoPagoRepo,
                      NotificacionRepository notificacionRepo, PasswordEncoder passwordEncoder) {
        this.paisRepo = paisRepo;
        this.personaRepo = personaRepo;
        this.clienteRepo = clienteRepo;
        this.subastaRepo = subastaRepo;
        this.catalogoRepo = catalogoRepo;
        this.productoRepo = productoRepo;
        this.itemRepo = itemRepo;
        this.metodoPagoRepo = metodoPagoRepo;
        this.notificacionRepo = notificacionRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (paisRepo.count() > 0) return;

        // Países
        Pais argentina = pais("Argentina", "AR", "Buenos Aires", "Argentina", "Español");
        Pais uruguay = pais("Uruguay", "UY", "Montevideo", "Uruguayo/a", "Español");
        pais("Chile", "CL", "Santiago", "Chileno/a", "Español");
        pais("Brasil", "BR", "Brasilia", "Brasileño/a", "Portugués");
        pais("Estados Unidos", "US", "Washington D.C.", "Estadounidense", "Inglés");
        pais("España", "ES", "Madrid", "Español/a", "Español");
        pais("Francia", "FR", "París", "Francés/a", "Francés");
        pais("Italia", "IT", "Roma", "Italiano/a", "Italiano");
        pais("Alemania", "DE", "Berlín", "Alemán/a", "Alemán");
        pais("Japón", "JP", "Tokio", "Japonés/a", "Japonés");

        // Personas demo
        Persona p1 = persona("12345678", "Juan Pérez", "Av. Corrientes 1234", "juan@mail.com", "demo1234", LocalDate.of(1990, 5, 15));
        Persona p2 = persona("87654321", "María García", "Av. Santa Fe 5678", "maria@mail.com", "demo1234", LocalDate.of(1985, 8, 22));
        Persona p3 = persona("11223344", "Carlos López", "Calle Falsa 123", "carlos@mail.com", "demo1234", LocalDate.of(1995, 3, 10));

        // Clientes
        Cliente c1 = cliente(p1, argentina);
        Cliente c2 = cliente(p2, uruguay);
        Cliente c3 = cliente(p3, argentina);

        // Subastas
        Subasta s1 = subasta(LocalDate.now().plusDays(7), "18:00:00", EstadoSubasta.abierta, p1, "Puerto Madero, CABA", 100, CategoriaSubasta.oro);
        Subasta s2 = subasta(LocalDate.now().plusDays(14), "15:30:00", EstadoSubasta.abierta, p2, "Palermo, CABA", 50, CategoriaSubasta.platino);
        Subasta s3 = subasta(LocalDate.now().minusDays(10), "10:00:00", EstadoSubasta.cerrada, p1, "San Telmo, CABA", 80, CategoriaSubasta.comun);
        Subasta s4 = subasta(LocalDate.now().minusDays(20), "16:00:00", EstadoSubasta.cerrada, p2, "Recoleta, CABA", 60, CategoriaSubasta.especial);
        Subasta s5 = subasta(LocalDate.now().minusDays(5), "12:00:00", EstadoSubasta.cerrada, p3, "Belgrano, CABA", 40, CategoriaSubasta.plata);

        // Catálogos e ítems
        Producto prod1 = producto("Reloj Omega Seamaster 1965 original, funcionando perfecto", p2);
        Producto prod2 = producto("Pintura al óleo firmada por artista reconocido, 80x60cm", p3);
        Producto prod3 = producto("Guitarra Gibson Les Paul 1959 reissue, excelente estado", p1);

        Catalogo cat1 = catalogo("Catálogo Subasta Relojes y Joyería", s1, p1);
        Catalogo cat2 = catalogo("Catálogo Arte Contemporáneo", s2, p2);

        item(cat1, prod1, 50000.0, 0.05);
        item(cat1, prod3, 120000.0, 0.08);
        item(cat2, prod2, 80000.0, 0.06);

        // Métodos de pago
        metodoPago(c1, "Mercado Pago", "4521");
        metodoPago(c1, "MasterCard", "7890");
        metodoPago(c2, "Visa", "3344");

        // Notificaciones
        notificacion(c1, TipoNotificacion.nueva_subasta, "Nueva subasta disponible: Arte Contemporáneo el " + LocalDate.now().plusDays(14));
        notificacion(c1, TipoNotificacion.solicitud_aceptada, "Tu artículo 'Guitarra Gibson' fue aceptado para subasta. Precio base: $120.000");
        notificacion(c2, TipoNotificacion.solicitud_rechazada, "Tu artículo no fue seleccionado. Los gastos de devolución son de $500.");
        notificacion(c2, TipoNotificacion.subasta_adquirida, "¡Ganaste! Adquiriste 'Reloj Omega Seamaster' por $65.000");
        notificacion(c3, TipoNotificacion.nueva_subasta, "Nueva subasta disponible: Relojes y Joyería el " + LocalDate.now().plusDays(7));
    }

    private Pais pais(String nombre, String corto, String capital, String nacionalidad, String idiomas) {
        Pais p = new Pais();
        p.setNombre(nombre);
        p.setNombreCorto(corto);
        p.setCapital(capital);
        p.setNacionalidad(nacionalidad);
        p.setIdiomas(idiomas);
        return paisRepo.save(p);
    }

    private Persona persona(String doc, String nombre, String dir, String email, String pass, LocalDate nac) {
        Persona p = new Persona();
        p.setDocumento(doc);
        p.setNombre(nombre);
        p.setDireccion(dir);
        p.setEmail(email);
        p.setPasswordHash(passwordEncoder.encode(pass));
        p.setFechaNacimiento(nac);
        p.setEstado(EstadoPersona.activo);
        return personaRepo.save(p);
    }

    private Cliente cliente(Persona persona, Pais pais) {
        Cliente c = new Cliente();
        c.setPersona(persona);
        c.setPais(pais);
        c.setAdmitido(SiNo.si);
        c.setCategoria(CategoriaSubasta.comun);
        return clienteRepo.save(c);
    }

    private Subasta subasta(LocalDate fecha, String hora, EstadoSubasta estado, Persona subastador,
                             String ubicacion, int capacidad, CategoriaSubasta cat) {
        Subasta s = new Subasta();
        s.setFecha(fecha);
        s.setHora(hora);
        s.setEstado(estado);
        s.setSubastador(subastador);
        s.setUbicacion(ubicacion);
        s.setCapacidadAsistentes(capacidad);
        s.setCategoria(cat);
        return subastaRepo.save(s);
    }

    private Catalogo catalogo(String desc, Subasta subasta, Persona responsable) {
        Catalogo c = new Catalogo();
        c.setDescripcion(desc);
        c.setSubasta(subasta);
        c.setResponsable(responsable);
        return catalogoRepo.save(c);
    }

    private Producto producto(String desc, Persona duenio) {
        Producto p = new Producto();
        p.setDescripcionCompleta(desc);
        p.setDescripcionCatalogo(desc.length() > 100 ? desc.substring(0, 100) : desc);
        p.setDuenio(duenio);
        p.setFecha(LocalDate.now().minusDays(30));
        p.setDisponible(SiNo.si);
        p.setSeguro("Póliza básica");
        return productoRepo.save(p);
    }

    private void item(Catalogo catalogo, Producto producto, double precioBase, double comision) {
        ItemCatalogo i = new ItemCatalogo();
        i.setCatalogo(catalogo);
        i.setProducto(producto);
        i.setPrecioBase(precioBase);
        i.setComision(comision);
        i.setSubastado(SiNo.no);
        itemRepo.save(i);
    }

    private void metodoPago(Cliente cliente, String proveedor, String digitos) {
        MetodoPago m = new MetodoPago();
        m.setCliente(cliente);
        m.setProveedor(proveedor);
        m.setUltimosDigitos(digitos);
        metodoPagoRepo.save(m);
    }

    private void notificacion(Cliente cliente, TipoNotificacion tipo, String mensaje) {
        Notificacion n = new Notificacion();
        n.setCliente(cliente);
        n.setTipo(tipo);
        n.setMensaje(mensaje);
        n.setLeida(SiNo.no);
        n.setFecha(LocalDateTime.now().minusHours((long)(Math.random() * 72)));
        notificacionRepo.save(n);
    }
}
