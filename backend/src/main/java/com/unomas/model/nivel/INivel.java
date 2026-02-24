package com.unomas.model.nivel;

public interface INivel {
    String getNombre();
    int getValor();
    INivel avanzar(NivelBase.Usuario usuario);
}
