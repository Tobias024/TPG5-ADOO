package modelo.nivel;

import modelo.Usuario;

public class NivelAvanzado extends NivelBase {

    public NivelAvanzado() {
        // Nivel maximo, no tiene siguiente
        super("Avanzado", 3, Integer.MAX_VALUE, null);
    }

    @Override
    public void avanzar(Usuario usuario) {
        // Ya esta en el nivel mas alto, no avanza
    }
}
