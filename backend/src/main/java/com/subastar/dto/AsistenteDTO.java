package com.subastar.dto;
import jakarta.validation.constraints.NotNull;
public class AsistenteDTO {
    private Long identificador;
    @NotNull private Integer numeroPostor;
    @NotNull private Long cliente;
    @NotNull private Long subasta;
    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public Integer getNumeroPostor() { return numeroPostor; }
    public void setNumeroPostor(Integer numeroPostor) { this.numeroPostor = numeroPostor; }
    public Long getCliente() { return cliente; }
    public void setCliente(Long cliente) { this.cliente = cliente; }
    public Long getSubasta() { return subasta; }
    public void setSubasta(Long subasta) { this.subasta = subasta; }
}
