package com.unomas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PartidoRequest {
    @NotNull
    private Long deporteId;
    @NotBlank
    private String ubicacion;
    @NotBlank
    private String fechaHora;
    @NotBlank
    private String duracion;
    @Min(2)
    private int cantidadJugadores;
    private String nivelMinimo = "PRINCIPIANTE";
    private String nivelMaximo = "AVANZADO";
    private String estrategiaEmparejamiento = "LIBRE";

    public Long getDeporteId() { return deporteId; }
    public void setDeporteId(Long deporteId) { this.deporteId = deporteId; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
    public int getCantidadJugadores() { return cantidadJugadores; }
    public void setCantidadJugadores(int cantidadJugadores) { this.cantidadJugadores = cantidadJugadores; }
    public String getNivelMinimo() { return nivelMinimo; }
    public void setNivelMinimo(String nivelMinimo) { this.nivelMinimo = nivelMinimo; }
    public String getNivelMaximo() { return nivelMaximo; }
    public void setNivelMaximo(String nivelMaximo) { this.nivelMaximo = nivelMaximo; }
    public String getEstrategiaEmparejamiento() { return estrategiaEmparejamiento; }
    public void setEstrategiaEmparejamiento(String estrategiaEmparejamiento) { this.estrategiaEmparejamiento = estrategiaEmparejamiento; }
}
