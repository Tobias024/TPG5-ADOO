package com.unomas.model.emparejamiento;

import com.unomas.model.Partido;
import com.unomas.model.Usuario;

public interface IEstrategiaEmparejamiento {
    boolean esCompatible(Usuario jugador, Partido partido);
    String getNombre();
}
