package com.unomas.model.emparejamiento;

import com.unomas.model.Partido;
import com.unomas.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class EmparejamientoLibre implements IEstrategiaEmparejamiento {

    @Override
    public boolean esCompatible(Usuario jugador, Partido partido) {
        return true;
    }

    @Override
    public String getNombre() {
        return "LIBRE";
    }
}
