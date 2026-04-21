package com.subastar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "catalogo")
public class Catalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identificador;

    private String descripcion;

    @OneToOne
    @JoinColumn(name = "subasta_id")
    private Subasta subasta;

    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private Persona responsable;

    public Catalogo() {}

    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Subasta getSubasta() { return subasta; }
    public void setSubasta(Subasta subasta) { this.subasta = subasta; }
    public Persona getResponsable() { return responsable; }
    public void setResponsable(Persona responsable) { this.responsable = responsable; }
}
