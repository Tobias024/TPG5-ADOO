package com.subastar.dto;
public class MetricasClienteDTO {
    private Double totalGastado;
    private Integer subastasParticipadas;
    private Integer subastasGanadas;
    private Double tasaExito;
    private Double mayorPuja;
    public Double getTotalGastado() { return totalGastado; }
    public void setTotalGastado(Double totalGastado) { this.totalGastado = totalGastado; }
    public Integer getSubastasParticipadas() { return subastasParticipadas; }
    public void setSubastasParticipadas(Integer subastasParticipadas) { this.subastasParticipadas = subastasParticipadas; }
    public Integer getSubastasGanadas() { return subastasGanadas; }
    public void setSubastasGanadas(Integer subastasGanadas) { this.subastasGanadas = subastasGanadas; }
    public Double getTasaExito() { return tasaExito; }
    public void setTasaExito(Double tasaExito) { this.tasaExito = tasaExito; }
    public Double getMayorPuja() { return mayorPuja; }
    public void setMayorPuja(Double mayorPuja) { this.mayorPuja = mayorPuja; }
}
