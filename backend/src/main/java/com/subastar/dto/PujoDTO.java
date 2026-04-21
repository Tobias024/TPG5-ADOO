package com.subastar.dto;
import com.subastar.model.enums.SiNo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
public class PujoDTO {
    private Long identificador;
    @NotNull private Long asistente;
    @NotNull private Long item;
    @NotNull @DecimalMin("0.02") private Double importe;
    private SiNo ganador;
    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public Long getAsistente() { return asistente; }
    public void setAsistente(Long asistente) { this.asistente = asistente; }
    public Long getItem() { return item; }
    public void setItem(Long item) { this.item = item; }
    public Double getImporte() { return importe; }
    public void setImporte(Double importe) { this.importe = importe; }
    public SiNo getGanador() { return ganador; }
    public void setGanador(SiNo ganador) { this.ganador = ganador; }
}
