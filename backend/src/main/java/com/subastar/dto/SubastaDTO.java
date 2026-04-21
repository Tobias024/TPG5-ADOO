package com.subastar.dto;
import com.subastar.model.enums.CategoriaSubasta;
import com.subastar.model.enums.EstadoSubasta;
import java.time.LocalDate;
public class SubastaDTO {
    private Long identificador;
    private LocalDate fecha;
    private String hora;
    private EstadoSubasta estado;
    private Long subastador;
    private String ubicacion;
    private Integer capacidadAsistentes;
    private CategoriaSubasta categoria;
    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    public EstadoSubasta getEstado() { return estado; }
    public void setEstado(EstadoSubasta estado) { this.estado = estado; }
    public Long getSubastador() { return subastador; }
    public void setSubastador(Long subastador) { this.subastador = subastador; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public Integer getCapacidadAsistentes() { return capacidadAsistentes; }
    public void setCapacidadAsistentes(Integer capacidadAsistentes) { this.capacidadAsistentes = capacidadAsistentes; }
    public CategoriaSubasta getCategoria() { return categoria; }
    public void setCategoria(CategoriaSubasta categoria) { this.categoria = categoria; }
}
