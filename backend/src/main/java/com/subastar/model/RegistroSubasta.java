package com.subastar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "registro_subasta")
public class RegistroSubasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identificador;

    @ManyToOne
    @JoinColumn(name = "subasta_id")
    private Subasta subasta;

    @ManyToOne
    @JoinColumn(name = "duenio_id")
    private Persona duenio;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private Double importe;

    private Double comision;

    public RegistroSubasta() {}

    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public Subasta getSubasta() { return subasta; }
    public void setSubasta(Subasta subasta) { this.subasta = subasta; }
    public Persona getDuenio() { return duenio; }
    public void setDuenio(Persona duenio) { this.duenio = duenio; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Double getImporte() { return importe; }
    public void setImporte(Double importe) { this.importe = importe; }
    public Double getComision() { return comision; }
    public void setComision(Double comision) { this.comision = comision; }
}
