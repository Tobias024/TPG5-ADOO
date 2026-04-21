package com.subastar.model;

import com.subastar.model.enums.SiNo;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identificador;

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private SiNo disponible = SiNo.si;

    @Column(length = 500)
    private String descripcionCatalogo;

    @Column(nullable = false, length = 300)
    private String descripcionCompleta;

    @ManyToOne
    @JoinColumn(name = "duenio_id", nullable = false)
    private Persona duenio;

    @Column(length = 30)
    private String seguro;

    public Producto() {}

    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public SiNo getDisponible() { return disponible; }
    public void setDisponible(SiNo disponible) { this.disponible = disponible; }
    public String getDescripcionCatalogo() { return descripcionCatalogo; }
    public void setDescripcionCatalogo(String descripcionCatalogo) { this.descripcionCatalogo = descripcionCatalogo; }
    public String getDescripcionCompleta() { return descripcionCompleta; }
    public void setDescripcionCompleta(String descripcionCompleta) { this.descripcionCompleta = descripcionCompleta; }
    public Persona getDuenio() { return duenio; }
    public void setDuenio(Persona duenio) { this.duenio = duenio; }
    public String getSeguro() { return seguro; }
    public void setSeguro(String seguro) { this.seguro = seguro; }
}
