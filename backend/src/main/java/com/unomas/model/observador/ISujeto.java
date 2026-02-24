package com.unomas.model.observador;

public interface ISujeto {
    void agregarObservador(IObserver observer);
    void eliminarObservador(IObserver observer);
    void notificarObservadores();
}
