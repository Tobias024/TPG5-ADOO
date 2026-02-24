package com.unomas.service;

import com.unomas.model.Partido;
import com.unomas.model.Usuario;
import com.unomas.model.emparejamiento.EstrategiaEmparejamientoFactory;
import com.unomas.model.emparejamiento.IEstrategiaEmparejamiento;
import org.springframework.stereotype.Component;

@Component
public class ValidadorInscripcion {

    private final EstrategiaEmparejamientoFactory estrategiaFactory;

    public ValidadorInscripcion(EstrategiaEmparejamientoFactory estrategiaFactory) {
        this.estrategiaFactory = estrategiaFactory;
    }

    public boolean esValida(Partido partido, Usuario jugador) {
        if (!"FALTAN_JUGADORES".equals(partido.getEstadoNombre())) {
            return false;
        }
        if (partido.cupoCompleto()) {
            return false;
        }
        if (partido.getJugadores().stream().anyMatch(j -> j.getId().equals(jugador.getId()))) {
            return false;
        }
        IEstrategiaEmparejamiento estrategia = estrategiaFactory.obtener(partido.getEstrategiaEmparejamiento());
        return estrategia.esCompatible(jugador, partido);
    }
}
