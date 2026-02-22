package modelo.nivel;

import modelo.Usuario;

public class NivelAvanzado extends NivelBase {

    public NivelAvanzado() {
        // Nivel maximo, no tiene siguiente
        super("Avanzado", 3, Integer.MAX_VALUE, null);
    }

    // avanzar() se hereda de NivelBase, pero como no hay siguiente nunca sube
}
