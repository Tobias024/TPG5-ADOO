package com.subastar.dto;
import jakarta.validation.constraints.NotNull;
public class ClienteRegistroDTO {
    @NotNull private Long identificador;
    private Long numeroPais;
    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public Long getNumeroPais() { return numeroPais; }
    public void setNumeroPais(Long numeroPais) { this.numeroPais = numeroPais; }
}
