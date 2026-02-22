package modelo.nivel;

import modelo.Usuario;

// Clase base para los niveles de juego, con logica de avance
// El nivel lleva la cuenta de partidos ganados y avanza cuando llega al umbral
public abstract class NivelBase implements INivel {
    private String nombre;
    private int valor;
    private int umbralPartidos;
    private INivel siguiente;
    private int partidosGanados;  // PENDIENTE: agregar al UML

    public NivelBase(String nombre, int valor, int umbralPartidos, INivel siguiente) {
        this.nombre = nombre;
        this.valor = valor;
        this.umbralPartidos = umbralPartidos;
        this.siguiente = siguiente;
        this.partidosGanados = 0;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public int getValor() {
        return valor;
    }

    // Registra un partido ganado y si llego al umbral, sube al siguiente nivel
    @Override
    public void avanzar(Usuario usuario) {
        partidosGanados++;
        if (siguiente != null && partidosGanados >= umbralPartidos) {
            usuario.setNivel(siguiente);
        }
    }

    public int getUmbralPartidos() {
        return umbralPartidos;
    }

    public INivel getSiguiente() {
        return siguiente;
    }

    public int getPartidosGanados() {
        return partidosGanados;
    }

    @Override
    public String toString() {
        return nombre + " (" + partidosGanados + "/" + umbralPartidos + " partidos)";
    }
}
