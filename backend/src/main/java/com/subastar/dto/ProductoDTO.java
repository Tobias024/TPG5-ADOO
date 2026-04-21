package com.subastar.dto;
import com.subastar.model.enums.SiNo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
public class ProductoDTO {
    private Long identificador;
    private LocalDate fecha;
    private SiNo disponible;
    private String descripcionCatalogo;
    @NotBlank private String descripcionCompleta;
    @NotNull private Long duenio;
    private String seguro;
    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public SiNo getDisponible() { return disponible; }
    public void setDisponible(SiNo disponible) { this.disponible = disponible; }
    public String getDescripcionCatalogo() { return descripcionCatalogo; }
    public void setDescripcionCatalogo(String descripcionCatalogo) { this.descripcionCatalogo = descripcionCatalogo; }
    public String getDescripcionCompleta() { return descripcionCompleta; }
    public void setDescripcionCompleta(String descripcionCompleta) { this.descripcionCompleta = descripcionCompleta; }
    public Long getDuenio() { return duenio; }
    public void setDuenio(Long duenio) { this.duenio = duenio; }
    public String getSeguro() { return seguro; }
    public void setSeguro(String seguro) { this.seguro = seguro; }
}
