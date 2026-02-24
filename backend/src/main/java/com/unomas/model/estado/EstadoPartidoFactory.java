package com.unomas.model.estado;

public class EstadoPartidoFactory {

    public static IEstadoPartido crear(String nombre) {
        return switch (nombre) {
            case "FALTAN_JUGADORES" -> new EstadoFaltanJugadores();
            case "ARMADO" -> new EstadoArmado();
            case "CONFIRMADO" -> new EstadoConfirmado();
            case "EN_JUEGO" -> new EstadoEnJuego();
            case "FINALIZADO" -> new EstadoFinalizado();
            case "CANCELADO" -> new EstadoCancelado();
            default -> throw new IllegalArgumentException("Estado desconocido: " + nombre);
        };
    }
}
