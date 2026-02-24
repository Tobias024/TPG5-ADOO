package com.unomas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class FinalizarPartidoRequest {
    
    @NotNull(message = "El resultado del equipo 1 es requerido")
    @Min(value = 0, message = "El resultado no puede ser negativo")
    private Integer resultadoEquipo1;
    
    @NotNull(message = "El resultado del equipo 2 es requerido")
    @Min(value = 0, message = "El resultado no puede ser negativo")
    private Integer resultadoEquipo2;
    
    private Long ganadorId;

    public Integer getResultadoEquipo1() { return resultadoEquipo1; }
    public void setResultadoEquipo1(Integer resultadoEquipo1) { this.resultadoEquipo1 = resultadoEquipo1; }
    public Integer getResultadoEquipo2() { return resultadoEquipo2; }
    public void setResultadoEquipo2(Integer resultadoEquipo2) { this.resultadoEquipo2 = resultadoEquipo2; }
    public Long getGanadorId() { return ganadorId; }
    public void setGanadorId(Long ganadorId) { this.ganadorId = ganadorId; }
}
