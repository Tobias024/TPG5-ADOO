package com.subastar.dto;
public class RegistroSubastaDTO {
    private Long identificador;
    private Long subasta;
    private Long duenio;
    private Long producto;
    private Long cliente;
    private Double importe;
    private Double comision;
    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public Long getSubasta() { return subasta; }
    public void setSubasta(Long subasta) { this.subasta = subasta; }
    public Long getDuenio() { return duenio; }
    public void setDuenio(Long duenio) { this.duenio = duenio; }
    public Long getProducto() { return producto; }
    public void setProducto(Long producto) { this.producto = producto; }
    public Long getCliente() { return cliente; }
    public void setCliente(Long cliente) { this.cliente = cliente; }
    public Double getImporte() { return importe; }
    public void setImporte(Double importe) { this.importe = importe; }
    public Double getComision() { return comision; }
    public void setComision(Double comision) { this.comision = comision; }
}
