package com.subastar.model;

import com.subastar.model.enums.CategoriaSubasta;
import com.subastar.model.enums.EstadoSubasta;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "subasta")
public class Subasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identificador;

    private LocalDate fecha;

    private String hora;

    @Enumerated(EnumType.STRING)
    private EstadoSubasta estado = EstadoSubasta.abierta;

    @ManyToOne
    @JoinColumn(name = "subastador_id")
    private Persona subastador;

    private String ubicacion;

    private Integer capacidadAsistentes;

    @Enumerated(EnumType.STRING)
    private CategoriaSubasta categoria = CategoriaSubasta.comun;

    public Subasta() {}

    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    public EstadoSubasta getEstado() { return estado; }
    public void setEstado(EstadoSubasta estado) { this.estado = estado; }
    public Persona getSubastador() { return subastador; }
    public void setSubastador(Persona subastador) { this.subastador = subastador; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public Integer getCapacidadAsistentes() { return capacidadAsistentes; }
    public void setCapacidadAsistentes(Integer capacidadAsistentes) { this.capacidadAsistentes = capacidadAsistentes; }
    public CategoriaSubasta getCategoria() { return categoria; }
    public void setCategoria(CategoriaSubasta categoria) { this.categoria = categoria; }
}
