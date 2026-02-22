package modelo.observador;

public interface ISujeto {
    void agregarObservador(IObserver obs);
    void eliminarObservador(IObserver obs);
    void notificarObservadores();
}
