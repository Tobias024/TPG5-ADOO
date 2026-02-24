package com.unomas.model.observador;

import java.util.ArrayList;
import java.util.List;

public class GestorObservadores {

    private final List<IObserver> observadores = new ArrayList<>();

    public void agregar(IObserver obs) {
        if (!observadores.contains(obs)) {
            observadores.add(obs);
        }
    }

    public void eliminar(IObserver obs) {
        observadores.remove(obs);
    }

    public void notificar(ISujeto sujeto) {
        for (IObserver obs : observadores) {
            obs.notificar(sujeto);
        }
    }
}
