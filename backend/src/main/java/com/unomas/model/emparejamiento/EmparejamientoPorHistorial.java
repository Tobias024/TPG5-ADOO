package com.unomas.model.emparejamiento;

import com.unomas.model.Partido;
import com.unomas.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class EmparejamientoPorHistorial implements IEstrategiaEmparejamiento {

    private static final int MIN_VICTORIAS = 3;

    @Override
    public boolean esCompatible(Usuario jugador, Partido partido) {
        return jugador.getVictorias() >= MIN_VICTORIAS;
    }

    @Override
    public String getNombre() {
        return "POR_HISTORIAL";
    }
}
