package modelo.observador;

import java.util.ArrayList;
import java.util.List;

// Gestiona la lista de observadores y les avisa cuando algo cambia
public class GestorObservadores {
    private List<IObserver> observadores;

    public GestorObservadores() {
        this.observadores = new ArrayList<>();
    }

    public void agregar(IObserver obs) {
        observadores.add(obs);
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
