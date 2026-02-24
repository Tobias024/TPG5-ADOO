package com.unomas.model.emparejamiento;

import com.unomas.model.Partido;
import com.unomas.model.Usuario;
import com.unomas.model.nivel.NivelBase;
import org.springframework.stereotype.Component;

@Component
public class EmparejamientoPorNivel implements IEstrategiaEmparejamiento {

    @Override
    public boolean esCompatible(Usuario jugador, Partido partido) {
        int valorJugador = NivelBase.getValorPorNombre(jugador.getNivel());
        int valorMinimo = NivelBase.getValorPorNombre(partido.getNivelMinimo());
        int valorMaximo = NivelBase.getValorPorNombre(partido.getNivelMaximo());
        return valorJugador >= valorMinimo && valorJugador <= valorMaximo;
    }

    @Override
    public String getNombre() {
        return "POR_NIVEL";
    }
}
