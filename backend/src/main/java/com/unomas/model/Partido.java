package com.unomas.model;

import com.unomas.model.estado.*;
import com.unomas.model.observador.GestorObservadores;
import com.unomas.model.observador.IObserver;
import com.unomas.model.observador.ISujeto;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "partidos")
public class Partido implements ISujeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deporte_id", nullable = false)
    private Deporte deporte;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organizador_id", nullable = false)
    private Usuario organizador;

    @Column(nullable = false)
    private String nivelMinimo = "PRINCIPIANTE";

    @Column(nullable = false)
    private String nivelMaximo = "AVANZADO";

    @Column(nullable = false)
    private String ubicacion;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private String duracion;

    @Column(nullable = false)
    private int cantidadJugadores;

    @Column(nullable = false)
    private String estadoNombre = "FALTAN_JUGADORES";

    @Column(nullable = false)
    private String estrategiaEmparejamiento = "LIBRE";

    @Column
    private Integer resultadoEquipo1;

    @Column
    private Integer resultadoEquipo2;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ganador_id")
    private Usuario ganador;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "partido_jugadores",
        joinColumns = @JoinColumn(name = "partido_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> jugadores = new ArrayList<>();

    @Transient
    private IEstadoPartido estado;

    @Transient
    private GestorObservadores gestorObservadores = new GestorObservadores();

    public Partido() {}

    @PostLoad
    public void initEstado() {
        this.estado = EstadoPartidoFactory.crear(this.estadoNombre);
    }

    public IEstadoPartido getEstado() {
        if (estado == null) {
            estado = EstadoPartidoFactory.crear(this.estadoNombre);
        }
        return estado;
    }

    public void setEstado(IEstadoPartido estado) {
        this.estado = estado;
        this.estadoNombre = estado.getNombre();
    }

    public boolean inscribirJugador(Usuario jugador) {
        if (jugadores.size() < cantidadJugadores && !jugadores.contains(jugador)) {
            jugadores.add(jugador);
            return true;
        }
        return false;
    }

    public boolean cupoCompleto() {
        return jugadores.size() >= cantidadJugadores;
    }

    public void avanzarEstado() {
        getEstado().avanzar(this);
        notificarObservadores();
    }

    public void cancelar(Usuario solicitante) {
        if (solicitante.getId().equals(organizador.getId())) {
            setEstado(new EstadoCancelado());
            notificarObservadores();
        }
    }

    // ISujeto implementation
    @Override
    public void agregarObservador(IObserver observer) {
        gestorObservadores.agregar(observer);
    }

    @Override
    public void eliminarObservador(IObserver observer) {
        gestorObservadores.eliminar(observer);
    }

    @Override
    public void notificarObservadores() {
        gestorObservadores.notificar(this);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Deporte getDeporte() { return deporte; }
    public void setDeporte(Deporte deporte) { this.deporte = deporte; }
    public Usuario getOrganizador() { return organizador; }
    public void setOrganizador(Usuario organizador) { this.organizador = organizador; }
    public String getNivelMinimo() { return nivelMinimo; }
    public void setNivelMinimo(String nivelMinimo) { this.nivelMinimo = nivelMinimo; }
    public String getNivelMaximo() { return nivelMaximo; }
    public void setNivelMaximo(String nivelMaximo) { this.nivelMaximo = nivelMaximo; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
    public int getCantidadJugadores() { return cantidadJugadores; }
    public void setCantidadJugadores(int cantidadJugadores) { this.cantidadJugadores = cantidadJugadores; }
    public String getEstadoNombre() { return estadoNombre; }
    public void setEstadoNombre(String estadoNombre) { this.estadoNombre = estadoNombre; }
    public String getEstrategiaEmparejamiento() { return estrategiaEmparejamiento; }
    public void setEstrategiaEmparejamiento(String estrategiaEmparejamiento) { this.estrategiaEmparejamiento = estrategiaEmparejamiento; }
    public List<Usuario> getJugadores() { return jugadores; }
    public void setJugadores(List<Usuario> jugadores) { this.jugadores = jugadores; }
    public GestorObservadores getGestorObservadores() { return gestorObservadores; }
    public Integer getResultadoEquipo1() { return resultadoEquipo1; }
    public void setResultadoEquipo1(Integer resultadoEquipo1) { this.resultadoEquipo1 = resultadoEquipo1; }
    public Integer getResultadoEquipo2() { return resultadoEquipo2; }
    public void setResultadoEquipo2(Integer resultadoEquipo2) { this.resultadoEquipo2 = resultadoEquipo2; }
    public Usuario getGanador() { return ganador; }
    public void setGanador(Usuario ganador) { this.ganador = ganador; }
}
