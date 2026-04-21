package com.subastar.dto;
import com.subastar.model.enums.SiNo;
public class ItemCatalogoDTO {
    private Long identificador;
    private Long catalogo;
    private Long producto;
    private Double precioBase;
    private Double comision;
    private SiNo subastado;
    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public Long getCatalogo() { return catalogo; }
    public void setCatalogo(Long catalogo) { this.catalogo = catalogo; }
    public Long getProducto() { return producto; }
    public void setProducto(Long producto) { this.producto = producto; }
    public Double getPrecioBase() { return precioBase; }
    public void setPrecioBase(Double precioBase) { this.precioBase = precioBase; }
    public Double getComision() { return comision; }
    public void setComision(Double comision) { this.comision = comision; }
    public SiNo getSubastado() { return subastado; }
    public void setSubastado(SiNo subastado) { this.subastado = subastado; }
}
