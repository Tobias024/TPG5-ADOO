package com.subastar.model;

import com.subastar.model.enums.SiNo;
import jakarta.persistence.*;

@Entity
@Table(name = "item_catalogo")
public class ItemCatalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identificador;

    @ManyToOne
    @JoinColumn(name = "catalogo_id")
    private Catalogo catalogo;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Double precioBase;

    private Double comision;

    @Enumerated(EnumType.STRING)
    private SiNo subastado = SiNo.no;

    public ItemCatalogo() {}

    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public Catalogo getCatalogo() { return catalogo; }
    public void setCatalogo(Catalogo catalogo) { this.catalogo = catalogo; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public Double getPrecioBase() { return precioBase; }
    public void setPrecioBase(Double precioBase) { this.precioBase = precioBase; }
    public Double getComision() { return comision; }
    public void setComision(Double comision) { this.comision = comision; }
    public SiNo getSubastado() { return subastado; }
    public void setSubastado(SiNo subastado) { this.subastado = subastado; }
}
