package com.subastar.dto;
import jakarta.validation.constraints.NotNull;
public class FotoDTO {
    private Long identificador;
    @NotNull private Long producto;
    @NotNull private String foto;
    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public Long getProducto() { return producto; }
    public void setProducto(Long producto) { this.producto = producto; }
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
}
