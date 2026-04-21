package com.subastar.dto;
public class CatalogoDTO {
    private Long identificador;
    private String descripcion;
    private Long subasta;
    private Long responsable;
    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Long getSubasta() { return subasta; }
    public void setSubasta(Long subasta) { this.subasta = subasta; }
    public Long getResponsable() { return responsable; }
    public void setResponsable(Long responsable) { this.responsable = responsable; }
}
