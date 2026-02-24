package com.unomas.dto;

import com.unomas.model.Partido;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class PartidoResponse {
    private Long id;
    private String deporte;
    private Long deporteId;
    private String organizador;
    private Long organizadorId;
    private String ubicacion;
    private String fechaHora;
    private String duracion;
    private int cantidadJugadores;
    private int jugadoresActuales;
    private String estado;
    private String nivelMinimo;
    private String nivelMaximo;
    private String estrategiaEmparejamiento;
    private List<JugadorResponse> jugadores;
    private Integer resultadoEquipo1;
    private Integer resultadoEquipo2;
    private Long ganadorId;
    private String ganadorNombre;

    public static PartidoResponse from(Partido p) {
        PartidoResponse r = new PartidoResponse();
        r.id = p.getId();
        r.deporte = p.getDeporte().getNombre();
        r.deporteId = p.getDeporte().getId();
        r.organizador = p.getOrganizador().getNombre();
        r.organizadorId = p.getOrganizador().getId();
        r.ubicacion = p.getUbicacion();
        r.fechaHora = p.getFechaHora().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        r.duracion = p.getDuracion();
        r.cantidadJugadores = p.getCantidadJugadores();
        r.jugadoresActuales = p.getJugadores().size();
        r.estado = p.getEstadoNombre();
        r.nivelMinimo = p.getNivelMinimo();
        r.nivelMaximo = p.getNivelMaximo();
        r.estrategiaEmparejamiento = p.getEstrategiaEmparejamiento();
        r.jugadores = p.getJugadores().stream()
            .map(u -> new JugadorResponse(u.getId(), u.getNombre(), u.getNivel()))
            .collect(Collectors.toList());
        r.resultadoEquipo1 = p.getResultadoEquipo1();
        r.resultadoEquipo2 = p.getResultadoEquipo2();
        if (p.getGanador() != null) {
            r.ganadorId = p.getGanador().getId();
            r.ganadorNombre = p.getGanador().getNombre();
        }
        return r;
    }

    public Long getId() { return id; }
    public String getDeporte() { return deporte; }
    public Long getDeporteId() { return deporteId; }
    public String getOrganizador() { return organizador; }
    public Long getOrganizadorId() { return organizadorId; }
    public String getUbicacion() { return ubicacion; }
    public String getFechaHora() { return fechaHora; }
    public String getDuracion() { return duracion; }
    public int getCantidadJugadores() { return cantidadJugadores; }
    public int getJugadoresActuales() { return jugadoresActuales; }
    public String getEstado() { return estado; }
    public String getNivelMinimo() { return nivelMinimo; }
    public String getNivelMaximo() { return nivelMaximo; }
    public String getEstrategiaEmparejamiento() { return estrategiaEmparejamiento; }
    public List<JugadorResponse> getJugadores() { return jugadores; }
    public Integer getResultadoEquipo1() { return resultadoEquipo1; }
    public Integer getResultadoEquipo2() { return resultadoEquipo2; }
    public Long getGanadorId() { return ganadorId; }
    public String getGanadorNombre() { return ganadorNombre; }

    public static class JugadorResponse {
        private Long id;
        private String nombre;
        private String nivel;

        public JugadorResponse(Long id, String nombre, String nivel) {
            this.id = id;
            this.nombre = nombre;
            this.nivel = nivel;
        }

        public Long getId() { return id; }
        public String getNombre() { return nombre; }
        public String getNivel() { return nivel; }
    }
}
