package patrones.observer;

public interface ISujeto {
    void agregarObservador(Observer obs);

    void eliminarObservador(Observer obs);

    void notificarObservadores();
}
