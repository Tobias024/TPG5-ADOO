package com.subastar.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public class MetodoPagoDTO {
    private Long identificador;
    @NotNull private Long cliente;
    @NotBlank private String proveedor;
    @NotBlank @Size(max = 4) private String ultimosDigitos;
    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public Long getCliente() { return cliente; }
    public void setCliente(Long cliente) { this.cliente = cliente; }
    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
    public String getUltimosDigitos() { return ultimosDigitos; }
    public void setUltimosDigitos(String ultimosDigitos) { this.ultimosDigitos = ultimosDigitos; }
}
