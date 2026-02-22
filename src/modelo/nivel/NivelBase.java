package modelo.nivel;

import modelo.Usuario;

// Clase base para los niveles de juego, con logica de avance
public abstract class NivelBase implements INivel {
    private String nombre;
    private int valor;
    private int umbralPartidos;
    private INivel siguiente;

    public NivelBase(String nombre, int valor, int umbralPartidos, INivel siguiente) {
        this.nombre = nombre;
        this.valor = valor;
        this.umbralPartidos = umbralPartidos;
        this.siguiente = siguiente;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public int getValor() {
        return valor;
    }

    // Cada nivel concreto define cuando se avanza al siguiente
    @Override
    public abstract void avanzar(Usuario usuario);

    public int getUmbralPartidos() {
        return umbralPartidos;
    }

    public INivel getSiguiente() {
        return siguiente;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
